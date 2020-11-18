package com.six.mysample.springjpaentitygraph.order.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "ORDER_STEP_STATUS", indexes = {
		@Index(name = "deepak_3", columnList = "ORDER_STEP_ID")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Check(constraints = "EXECUTION_STATUS IN ('START','RESTART','PROCESSING','CANCELLED','COMPLETED','FAILED')")
public class OrderStepStatus {

	@Id
	@Column(name = "ID")
	private UUID id;

	@Column(name = "EXECUTION_STATUS", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private OrderStepExecutionStatusDto lastExecutionStatus;

	@Column(name = "EXECUTION_MESSAGE")
	private String executionMessage;

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