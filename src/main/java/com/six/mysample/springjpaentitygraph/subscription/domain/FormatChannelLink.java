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
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Pre-configured allowed format and channel association.
 * A SWIFT format can be delivered to SWFT channel only. PDF to SWIFT channel is not allowed.
 *
 * @author tkr0d
 * @version 1.0
 * @updated 31-Aug-2017 14:20:20
 */
@Entity
@Table(name = "FORMAT_CHANNEL_LINK",
       uniqueConstraints = {
		       @UniqueConstraint(columnNames = { "FORMAT_ID", "CHANNEL_ID" }, name = "UK_FORMAT_CHANNEL_LINK_1")
       })
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FormatChannelLink {

	@Id
	@Column(name = "ID")
	private UUID id;

	@PrePersist
	public void ensureId() {
		if (id == null) {
			id = UUID.randomUUID();
		}
	}

	/**
	 * Many to many between Format & Channel
	 */
	@ManyToOne(
			optional = false,
			fetch = FetchType.EAGER
	)
	@JoinColumn(
			name = "FORMAT_ID",
			nullable = false,
			foreignKey = @ForeignKey(name = "FK_FOR_CHA_LINK_FORMAT_1")
	)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Format format;

	@ManyToOne(
			optional = false,
			fetch = FetchType.EAGER
	)
	@JoinColumn(
			name = "CHANNEL_ID",
			nullable = false,
			foreignKey = @ForeignKey(name = "FK_FOR_CHA_LINK_CHANNEL_1")
	)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Channel channel;

	@Override
	public boolean equals(Object o) {
		if (o instanceof  FormatChannelLink)
			return this.id.equals(((FormatChannelLink)o).id);

		return false;
	}
}