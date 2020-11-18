package com.six.mysample.springjpaentitygraph.order.domain;

import com.google.common.collect.Sets;
import java.time.LocalDateTime;
import java.util.Objects;
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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "ORDER_STEP", indexes = {
        @Index(name = "deepak_1", columnList = "ORDER_OBJECT_ID")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Check(constraints = "LAST_EXECUTION_STATUS IN ('START','RESTART','PROCESSING','CANCELLED','COMPLETED','FAILED')")
public class OrderStep {

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "START_TIME", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "END_TIME", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "TASK_TYPE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStepTaskTypeDto taskType;

    @Column(name = "LAST_EXECUTION_STATUS", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStepExecutionStatusDto lastExecutionStatus;

    @Builder.Default
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(
            name = "ORDER_STEP_ID",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_ORDER_INSTRUCT_ORDER_STEP_1")
    )
    private Set<OrderInstruction> orderInstructions = Sets.newLinkedHashSet();

    @Builder.Default
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(
            name = "ORDER_STEP_ID",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_ORDER_STEP_STATUS_1")
    )
    private Set<OrderStepStatus> orderStepStatuses = Sets.newLinkedHashSet();

    @Builder.Default
    @OneToMany(
            mappedBy = "orderStep",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<OrderArtifact> orderArtifacts = Sets.newLinkedHashSet();

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATION_TIME")
    private LocalDateTime creationTime;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name = "MODIFICATION_TIME")
    private LocalDateTime modificationTime;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        return Objects.equals(getId(), ((OrderStep) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public void addOrderInstruction(Set<OrderInstruction> orderInstructions) {
        if (orderInstructions != null) {
            this.getOrderInstructions()
                    .addAll(orderInstructions);
        }
    }

    public void addOrderInstruction(@NotNull OrderInstruction orderInstruction) {
        if (orderInstruction != null) {
            this.getOrderInstructions()
                    .add(orderInstruction);
        }
    }

    public void addOrderStepStatus(Set<OrderStepStatus> orderStepStatuses) {
        if (orderStepStatuses != null) {
            this.getOrderStepStatuses()
                    .addAll(orderStepStatuses);
        }
    }

    public void addOrderStepStatus(OrderStepStatus orderStepStatus) {
        if (orderStepStatus != null) {
            this.getOrderStepStatuses()
                    .add(orderStepStatus);
        }
    }

    public void addOrderArtifact(@NotNull Set<OrderArtifact> orderArtifacts) {
        for (OrderArtifact orderArtifact : orderArtifacts) {
            addOrderArtifact(orderArtifact);
        }
    }

    public void addOrderArtifact(@NotNull OrderArtifact orderArtifact) {
        this.getOrderArtifacts()
                .add(orderArtifact);
        orderArtifact.setOrderStep(this);
    }

    public boolean isStepNotCompleted() {
        return !this.getLastExecutionStatus()
                .equals(OrderStepExecutionStatusDto.COMPLETED);
    }

    public boolean isCompleted() {
        return this.getLastExecutionStatus()
                .equals(OrderStepExecutionStatusDto.COMPLETED);
    }
}