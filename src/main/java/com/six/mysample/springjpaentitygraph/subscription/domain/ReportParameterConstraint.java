package com.six.mysample.springjpaentitygraph.subscription.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "REPORT_PARAMETER_CONSTRAINT")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Check(constraints = "CONSTRAINT_TYPE IN ('MINIMUM', 'MAXIMUM', 'GREATER_THAN_FIELD', 'REGEX')")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReportParameterConstraint {

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "CONSTRAINT_TYPE", nullable = false, length = 60)
    @Enumerated(EnumType.STRING)
    private ReportParameterConstraintTypeDto constraintType;

    @Column(name = "VALUE", length = 255)
    private String value;

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

        this.creationTime = LocalDateTime.now();
        this.modificationTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof ReportParameterConstraint)) {
            return false;
        } else {
            ReportParameterConstraint other = (ReportParameterConstraint) obj;
            if (this.getId() != null && other.getId() != null) {
                return this.getId()
                        .equals(other.getId());
            } else {
                return false;
            }
        }
    }

    @Override
    public String toString() {
        return (this.getClass().getName()) + ("(") + ("Id: ") + (this.getId()) + ("; ") + ("constraintType: ")
                + (this.constraintType) + ("; ") + ("value: ") + (this.value) + (")");
    }

}
