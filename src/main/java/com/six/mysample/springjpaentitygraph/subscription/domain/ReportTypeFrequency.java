package com.six.mysample.springjpaentitygraph.subscription.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Check;

/**
 * @author tkr0d
 * @version 1.0
 * @created 15-Sep-2017 15:28:31
 */
@Entity
@Table(name = "REPORT_TYPE_FREQUENCY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Check(constraints = "FREQUENCY_TYPE IN ('DAILY','MONTHLY','QUARTERLY','YEARLY')")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReportTypeFrequency {

	@Id
	@Column(name = "ID")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "REPORT_TYPE_ID", foreignKey = @ForeignKey(name = "FK_REPORT_TYPE_FREQUENCY_1"))
	private ReportType reportType;

	@Column(name = "FREQUENCY_TYPE", nullable = false, length = 50)
	private String reportFrequency;

	@PrePersist
	public void ensureId() {
		if (id == null) {
			id = UUID.randomUUID();
		}
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append(
				" ReportTypeFrequeuncy: Id [")
				.append(this.id)
				.append("], ReportType [")
				.append(reportType.getShortName())
				.append("], ReportFrequency [")
				.append(reportFrequency)
				.append("]")
				.toString();
	}
}
