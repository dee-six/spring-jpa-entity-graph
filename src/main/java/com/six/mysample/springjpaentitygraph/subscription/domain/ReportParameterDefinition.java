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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "REPORT_PARAMETER_DEFINITION")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Check(constraints = "PARAMETER_TYPE IN ('STRING','DATE','TIME','NUMBER')")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReportParameterDefinition {

	@Id
	@Column(name = "ID")
	private UUID id;

	@Column(name = "NAME", nullable = false, length = 60)
	private String name;

	@Column(name = "DESCRIPTION", nullable = false, length = 255)
	private String description;

	@Column(name = "PARAMETER_TYPE", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private ReportParameterTypeDto parameterType;

	@Column(name = "PARAMETER_KEY", length = 64)
	private String parameterKey;

	@Column(name = "PARAMETER_SCOPE", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private ReportParameterScopeDto parameterScope;

	@Column(name = "SORT_ORDER", nullable = false, length = 10)
	private Integer sortOrder;

	@Column(name = "PARAMETER_MANDATORY", nullable = false, length = 1)
	private Integer parameterMandatory;

	@Column(name = "IS_VISIBLE", nullable = false, length = 1)
	private Integer isVisible;

	@Builder.Default
	@OneToMany(
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	@JoinColumn(
			name = "REPORT_PAREMETER_ID",
			nullable = false,
			foreignKey = @ForeignKey(name = "FK_REPORT_PARAM_CONS_PARAM_1")
	)
	@Setter(AccessLevel.NONE)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ReportParameterConstraint> reportParameterConstraints = Sets.newLinkedHashSet();

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
		}
		else if (!(obj instanceof ReportParameterDefinition)) {
			return false;
		}
		else {
			ReportParameterDefinition other = (ReportParameterDefinition) obj;
			if (this.getId() != null && other.getId() != null) {
				return this.getId()
						.equals(other.getId());
			}
			else {
				return false;
			}
		}
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append(this.getClass()
				.getName())
				.append("(")
				.append("Id: ")
				.append(this.getId())
				.append("; ")
				.append("name: ")
				.append(this.name)
				.append("; ")
				.append("description: ")
				.append(this.description)
				.append("; ")
				.append("parameterKey: ")
				.append(this.parameterKey)
				.append("; ")
				.append("parameter scope: ")
				.append(this.parameterScope)
				.append("; ")
				.append("parameter type: ")
				.append(this.parameterType)
				.append(")")
				.toString();
	}

}
