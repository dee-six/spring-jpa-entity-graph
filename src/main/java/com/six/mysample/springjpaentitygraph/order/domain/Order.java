package com.six.mysample.springjpaentitygraph.order.domain;


import com.google.common.collect.Sets;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportSubscription;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportType;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ORDER_OBJECT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "order.detail",
                attributeNodes = {
                        @NamedAttributeNode(value = "orderSteps", subgraph = "orderSteps")//,
                        //@NamedAttributeNode(value = "reportType", subgraph = "children"),
                        //@NamedAttributeNode(value = "reportSubscription")
                },
                subgraphs = @NamedSubgraph(
                        name = "orderSteps",
                        attributeNodes = {
                                @NamedAttributeNode(value = "orderInstructions"),
                                @NamedAttributeNode(value = "orderStepStatuses"),
                                @NamedAttributeNode(value = "orderArtifacts")
                        }))
)
public class Order {

    public static final int SUMMARY_LENGTH = 255;

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "BUSINESS_ID", nullable = false, unique = true)
    private Integer businessId;

    @Column(name = "SHORT_NAME", nullable = false, length = 80)
    @Pattern(message = "Only characters and numbers are allowed.", regexp = "^[A-Za-z0-9_]+$")
    private String shortName;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Column(name = "SUMMARY", length = SUMMARY_LENGTH)
    private String summary;

    @Column(name = "BUSINESS_PARTNER_REFERENCE", nullable = false, length = 50)
    private String businessPartnerReference;

    @Column(name = "LAST_STATUS", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatusDto orderStatusDto;

    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinColumn(
            name = "REPORT_TYPE_ID", foreignKey = @ForeignKey(name = "FK_ORDER_OBJECT_REPORT_TYPE_1")
    )
    private ReportType reportType;

    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinColumn(
            name = "SUBSCRIPTION_ID", foreignKey = @ForeignKey(name = "FK_ORDER_OBJECT_REPORT_SUBS_1"), nullable = true
    )
    private ReportSubscription reportSubscription;

    @Builder.Default
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(
            name = "ORDER_OBJECT_ID",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_ORDER_TASK_ORDER_OBJ_1")
    )
    @Setter(AccessLevel.NONE)
    private Set<OrderStep> orderSteps = Sets.newLinkedHashSet();

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATION_TIME")
    private LocalDateTime creationTime;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name = "MODIFICATION_TIME")
    private LocalDateTime modificationTime;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Order)) {
            return false;
        } else {
            Order other = (Order) obj;
            if (this.getId() != null && other.getId() != null) {
                return this.getId()
                        .equals(other.getId());
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessId);
    }

    @Override
    public String toString() {
        String ids = "";
        ids = ids + "Id: " + this.getId() + "; ";
        return this.getClass()
                .getName() + "( " + ids + ")";
    }

    @PrePersist
    public void performPreInsert() {
        if (id == null) {
            id = UUID.randomUUID();
        }

        this.creationTime = LocalDateTime.now();
        this.modificationTime = LocalDateTime.now();
    }

    @PreUpdate
    public void setModificationTimeStamp() {
        this.setModificationTime(LocalDateTime.now());
    }

    public void addOrderSteps(Set<OrderStep> orderSteps) {
        for (OrderStep orderStep : orderSteps) {
            addOrderStep(orderStep);
        }
    }

    public void addOrderStep(OrderStep orderStep) {
        this.getOrderSteps()
                .add(orderStep);
    }

    /**
     * The incomplete task is returned
     */
    public OrderStepTaskTypeDto getCurrentOrderStepTaskType() {

        if (isExecutionCompleted()) {
            return OrderStepTaskTypeDto.DELIVER;
        }

        Optional<OrderStep>
                orderStep =
                this.getOrderSteps()
                        .stream()
                        .filter(OrderStep::isStepNotCompleted)
                        .findFirst();

        if (orderStep.isPresent()) {
            return orderStep.get()
                    .getTaskType();
        }

        // There are no icomplete Steps. So send back last complete in sequence - D, C, G
        if (isExecutionCompleted(OrderStepTaskTypeDto.CONVERT)) {
            return OrderStepTaskTypeDto.CONVERT;
        }

        return OrderStepTaskTypeDto.GENERATE;
    }

    /**
     * The incomplete task is returned
     */
    public boolean isExecutionCompleted() {
        return isExecutionCompleted(OrderStepTaskTypeDto.GENERATE) &&
                isExecutionCompleted(OrderStepTaskTypeDto.CONVERT) &&
                isExecutionCompleted(OrderStepTaskTypeDto.DELIVER);
    }

    public boolean isExecutionCompleted(OrderStepTaskTypeDto taskType) {
        Optional<OrderStep>
                orderStep =
                this.getOrderSteps()
                        .stream()
                        .filter(os -> os.getTaskType()
                                .equals(taskType))
                        .findFirst();

        if (orderStep.isPresent()) {
            return orderStep.get()
                    .isCompleted();
        }

        return false;
    }

    public OrderStepExecutionStatusDto getCurrentStepExecutionStatus() {
        if (isExecutionCompleted()) {
            return OrderStepExecutionStatusDto.COMPLETED;
        }

        // get first incomplete state
        OrderStepTaskTypeDto taskType = getCurrentOrderStepTaskType();
        Optional<OrderStep>
                orderStep =
                this.getOrderSteps()
                        .stream()
                        .filter(orderStep1 -> orderStep1.getTaskType()
                                .equals(taskType))
                        .findFirst();

        if (orderStep.isPresent()) {
            return orderStep.get()
                    .getLastExecutionStatus();
        }

        return OrderStepExecutionStatusDto.START;
    }
}
