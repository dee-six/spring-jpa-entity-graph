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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tkr0d
 * @version 1.0
 * @created 04-Sep-2017 18:25:02
 */
@Entity
@Table(name = "SUBSCRIBED_FORMAT_CHANNEL_LINK")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscribedFormatChannelLink {

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
			fetch = FetchType.LAZY
	)
	@JoinColumn(
			name = "FORMAT_CHANNEL_LINK_ID",
			nullable = false,
			foreignKey = @ForeignKey(name = "FK_SUBS_FMT_CHA_LINK_FOR_CHA_1")
	)
	private FormatChannelLink sFormatChannelLink;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SubscribedFormatChannelLink)
			return this.id.equals(((SubscribedFormatChannelLink) obj).id);

		return false;
	}
}