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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Check;

/**
 * Provide a description of the class
 * <p>
 * Created by tkr0d on 19.07.2017.
 */
@Entity
@Table(name = "REPORT_TYPE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Check(constraints = "CATEGORY IN ('SMRA', 'TPA', 'INSURANCE', 'SNB', 'SNB_LIMIT_I', 'INTERNAL')")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedEntityGraph(name = "reportType.allowedFormatChannel",
        attributeNodes = {
                @NamedAttributeNode(value = "allowedFormatChannelLinks"),
                @NamedAttributeNode(value = "reportTypeFrequencies"),
                @NamedAttributeNode(value = "reportParameterDefinitions",
                        subgraph = "constraints")},
                subgraphs = @NamedSubgraph(name = "constraints", attributeNodes = @NamedAttributeNode(value = "reportParameterConstraints")))
public class ReportType {

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "BUSINESS_ID", nullable = false, unique = true)
    private Integer businessId;

    @Column(name = "SHORT_NAME", nullable = false, unique = true, length = 20)
    @Pattern(message = "Only characters and numbers are allowed.", regexp = "^[A-Za-z0-9]+$")
    private String shortName;

    @Column(name = "NAME", nullable = false, unique = true, length = 80)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false, length = 255)
    private String description;

    @Column(name = "REPORT_KEY", nullable = false, unique = true, length = 64)
    private String reportKey;

    @Column(name = "CATEGORY", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ReportTypeCategoryDto category;

    @Builder.Default
    @OneToMany(
            mappedBy = "reportType",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH},
            orphanRemoval = true
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AllowedFormatChannelLink> allowedFormatChannelLinks = Sets.newLinkedHashSet();

    @Builder.Default
    @OneToMany(
            mappedBy = "reportType",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH},
            orphanRemoval = true

    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReportTypeFrequency> reportTypeFrequencies = Sets.newLinkedHashSet();

    @Builder.Default
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(
            name = "REPORT_TYPE_ID",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_REPORT_PARAM_REPORT_TYPE_1")
    )
    @Setter(AccessLevel.NONE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReportParameterDefinition> reportParameterDefinitions = Sets.newLinkedHashSet();

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
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ReportType) {
            return this.id.equals(((ReportType) o).id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        return sb.append(this.getClass()
                .getName())
                .append("( ")
                .append("Id: ")
                .append(this.getId())
                .append("; ")
                .append("BUSINESS_ID: ")
                .append(this.businessId)
                .append("; ")
                .append("SHORT_NAME: ")
                .append(this.shortName)
                .append("; ")
                .append("REPORT_KEY: ")
                .append(this.reportKey)
                .append(")")
                .toString();
    }
}
