package com.six.mysample.springjpaentitygraph.order.domain;

import com.six.mysample.springjpaentitygraph.subscription.domain.ReportParameterTypeDto;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
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
@Table(name = "ORDER_PARAMETER_VALUE", indexes = {
		@Index(name = "deepak_5", columnList = "ORDER_INSTRUCTION_ID")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Check(constraints = "PARAMETER_TYPE IN ('STRING','DATE','TIME','NUMBER')")
public class OrderParameterValue {

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
