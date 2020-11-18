package com.six.mysample.springjpaentitygraph.subscription.domain;

import com.google.common.collect.Sets;
import java.time.LocalDateTime;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "REPORT_SUBSCRIPTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "reportSubscription.all",
                attributeNodes = {
                        @NamedAttributeNode(value = "subscribedFormatChannelLinks"),
                        @NamedAttributeNode(value = "subscriptionParameters"),
                        @NamedAttributeNode(value = "reportType", subgraph = "reportType")},
                subgraphs = @NamedSubgraph(name = "reportType",
                        attributeNodes = {
                                @NamedAttributeNode(value = "allowedFormatChannelLinks"),
                                @NamedAttributeNode(value = "reportTypeFrequencies"),
                                @NamedAttributeNode(value = "reportParameterDefinitions")})
        )
)
public class ReportSubscription {

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "BUSINESS_ID", nullable = false, unique = true, length = 10)
    private Integer businessId;

    @Column(name = "SHORT_NAME", nullable = false, length = 80)
    @Pattern(message = "Only characters and numbers are allowed.", regexp = "^[A-Za-z0-9_]+$")
    private String shortName;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "BUSINESS_PARTNER_REFERENCE", nullable = false, length = 50)
    private String businessPartnerReference;

    @Column(name = "SCHEDULER_CRON_EXPRESSION", nullable = false, length = 60)
    private String schedulerCronExpression;

    /**
     * ReportType.Report_key + '_'  + ReportSubscription.businessId
     */
    @Column(name = "SCHEDULED_TASK_REFERENCE", length = 90)
    private String scheduleTaskReference;

    @Column(name = "FREQUENCY", nullable = false, length = 90)
    @Enumerated(EnumType.STRING)
    private ReportFrequencyDto frequency;

    @Column(name = "TIME", nullable = false, length = 90)
    @Enumerated(EnumType.STRING)
    private ReportTimeDto time;

    @Column(name = "STATUS", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ReportStatusDto status;

    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinColumn(
            name = "REPORT_TYPE_ID", foreignKey = @ForeignKey(name = "FK_REPORT_SUBS_REPORT_TYPE_1")
    )
    private ReportType reportType;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    @JoinColumn(
            name = "REPORT_SUBSCRIPTION_ID",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_SUBS_FMT_CHA_LINK_RPT_SUB_1")
    )
    @Builder.Default
    private Set<SubscribedFormatChannelLink> subscribedFormatChannelLinks = Sets.newLinkedHashSet();

    @Builder.Default
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(
            name = "REPORT_SUBSCRIPTION_ID",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_SUBS_PARAM_VAL_RPT_SUBS_1")
    )
    private Set<SubscriptionParameterValue> subscriptionParameters = Sets.newLinkedHashSet();

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATION_TIME")
    private LocalDateTime creationTime;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name = "MODIFICATION_TIME")
    private LocalDateTime modificationTime;

    @PrePersist
    public void ensureId() {
        if (id == null) {
            id = UUID.randomUUID();
        }

        this.setCreationTime(LocalDateTime.now());
    }

    @PreUpdate
    public void setModificationTimeStamp() {
        this.setModificationTime(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ReportSubscription) {
            return this.id.equals(((ReportSubscription) o).id);
        }

        return false;
    }

    public void addSubscribedFormatChannelLink(Set<SubscribedFormatChannelLink> subscribedFormatChannelLinks) {
        this.getSubscribedFormatChannelLinks()
                .addAll(subscribedFormatChannelLinks);
    }

    public void addSubscribedFormatChannelLink(SubscribedFormatChannelLink subscribedFormatChannelLink) {
        this.getSubscribedFormatChannelLinks()
                .add(subscribedFormatChannelLink);
    }

    public void removeAllSubscribedFormatChannelLink() {
        this.getSubscribedFormatChannelLinks()
                .clear();
    }

    public void addSubscriptionParameter(Set<SubscriptionParameterValue> subscriptionParameterValues) {
        this.getSubscriptionParameters()
                .addAll(subscriptionParameterValues);
    }

    public void removeAllSubscriptionParameter() {
        this.getSubscriptionParameters().clear();
    }
}
