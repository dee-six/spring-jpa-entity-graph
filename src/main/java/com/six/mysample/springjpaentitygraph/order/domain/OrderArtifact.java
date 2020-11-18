package com.six.mysample.springjpaentitygraph.order.domain;

import com.six.mysample.springjpaentitygraph.subscription.domain.ReportChannelDto;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportFormatDto;
import java.time.LocalDateTime;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author tkr0d
 * @version 1.0
 * @since 19-Sep-2017 17:14:07
 */
@Entity
@Table(name = "ORDER_ARTIFACT", indexes = {
		@Index(name = "deepak_4", columnList = "ORDER_STEP_ID")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderArtifact {

	@Id
	@Column(name = "ID")
	private UUID id;

	@Column(name = "FORMAT", nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	private ReportFormatDto format;

	@Column(name = "CHANNEL", nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	private ReportChannelDto channel;

	@Column(name = "ARTIFACT_FILE_NAME", nullable = false, length = 255)
	private String artifactFileName;

	@Column(name = "LOCATION", nullable = false, length = 255)
	private String location;

	@Setter(AccessLevel.PACKAGE)
	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL
	)
	@JoinColumn(
			name = "ORDER_STEP_ID",
			nullable = false,
			foreignKey = @ForeignKey(name = "FK_ORDER_ARTIFACT_ORDER_STEP_1")
	)
	private OrderStep orderStep;

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
		else if (!(obj instanceof OrderArtifact)) {
			return false;
		}
		else {
			OrderArtifact other = (OrderArtifact) obj;
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
		String ids = "";
		ids = ids + "Id: " + this.getId() + "; ";
		return this.getClass()
				.getName() + "( " + ids + ")";
	}
}