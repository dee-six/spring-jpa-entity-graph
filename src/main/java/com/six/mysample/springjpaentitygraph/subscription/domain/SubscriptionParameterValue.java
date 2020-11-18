package com.six.mysample.springjpaentitygraph.subscription.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OrderColumn;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

/**
 * Contains a parameters used in execution of the report.
 */
@Entity
@Table(name = "SUBSCRIPTION_PARAMETER_VALUE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Check(constraints = "PARAMETER_TYPE IN ('STRING','DATE','TIME','NUMBER')")
public class SubscriptionParameterValue {

	@Id
	@Column(name = "ID")
	private UUID id;

	@Column(name = "PARAMETER_NAME", nullable = false)
	private String parameterName;

	@Column(name = "PARAMETER_TYPE", nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	private ReportParameterTypeDto parameterType;

	@Column(name = "PARAMETER_VALUE")
	private String parameterValue;

	@OrderColumn
	@Column(name = "SORT_ORDER", nullable = false)
	private Integer sortOrder;

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
}
