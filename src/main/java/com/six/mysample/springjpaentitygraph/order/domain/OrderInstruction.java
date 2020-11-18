package com.six.mysample.springjpaentitygraph.order.domain;

import com.google.common.collect.Sets;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportChannelDto;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportFormatDto;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportFrequencyDto;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportTimeDto;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Contains a parameters used in execution of the report.
 */
@Entity
@Table(name = "ORDER_INSTRUCTION", indexes = {
		@Index(name = "deepak_2", columnList = "ORDER_STEP_ID")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class OrderInstruction {

	@Id
	@Column(name = "ID")
	private UUID id;

	@Column(name = "REPORT_TYPE_ID")
	private String reportTypeId;

	@Column(name = "REPORT_KEY", length = 64)
	private String reportKey;

	@Column(name = "FREQUENCY")
	@Enumerated(EnumType.STRING)
	private ReportFrequencyDto frequency;

	@Column(name = "TIME")
	@Enumerated(EnumType.STRING)
	private ReportTimeDto time;

	@Column(name = "CHANNEL_CODE")
	@Enumerated(EnumType.STRING)
	private ReportChannelDto channel;

	@Column(name = "FORMAT_CODE")
	@Enumerated(EnumType.STRING)
	private ReportFormatDto format;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "LOCATION")
	private String location;

	@Builder.Default
	@OneToMany(
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	@JoinColumn(
			name = "ORDER_INSTRUCTION_ID",
			nullable = false,
			foreignKey = @ForeignKey(name = "FK_ORDER_PARAM_VAL_ORDER_INST_1")
	)
	@Setter(AccessLevel.NONE)
	private Set<OrderParameterValue> orderParameterValues = Sets.newLinkedHashSet();

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

	public void addOrderParameterValue(Set<OrderParameterValue> orderParameterValues) {
		if (orderParameterValues != null)
			this.getOrderParameterValues()
					.addAll(orderParameterValues);
	}

	public void addOrderParameterValue(OrderParameterValue orderParameterValue) {
		if (orderParameterValue != null)
			this.getOrderParameterValues()
					.add(orderParameterValue);
	}
}
