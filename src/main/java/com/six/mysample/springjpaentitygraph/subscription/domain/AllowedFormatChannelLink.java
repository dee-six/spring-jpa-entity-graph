package com.six.mysample.springjpaentitygraph.subscription.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author tkr0d
 * @version 1.0
 * @created 31-Aug-2017 14:20:14
 */
@Entity
@Table(name = "ALLOWED_FORMAT_CHANNEL_LINK",
       uniqueConstraints = {
		       @UniqueConstraint(columnNames = { "REPORT_TYPE_ID", "FORMAT_CHANNEL_LINK_ID" }, name = "UK_ALLOWED_FMT_CHA_LINK_1")
       })
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllowedFormatChannelLink {

	@Id
	@Column(name = "ID")
	private UUID id;

	@PrePersist
	public void ensureId() {
		if (id == null) {
			id = UUID.randomUUID();
		}
	}

	@ManyToOne(
			optional = false,
			fetch = FetchType.EAGER
	)
	@JoinColumn(
			name = "REPORT_TYPE_ID",
			nullable = false,
			foreignKey = @ForeignKey(name = "FK_ALLOWED_FMT_CHA_RPT_TYPE_1")
	)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private ReportType reportType;

	@ManyToOne(
			optional = false,
			fetch = FetchType.EAGER
	)
	@JoinColumn(
			name = "FORMAT_CHANNEL_LINK_ID",
			nullable = false,
			foreignKey = @ForeignKey(name = "FK_ALLOWED_FMT_CHA_LINK_1")
	)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private FormatChannelLink aFormatChannelLink;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof  AllowedFormatChannelLink)
			return this.id.equals(((AllowedFormatChannelLink)obj).id);

		return false;
	}
}