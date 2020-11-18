package com.six.mysample.springjpaentitygraph.subscription.domain;


import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Provide a description of the class
 * <p>
 * Created by tkr0d on 19.07.2017.
 */
@Entity
@Table(name = "FORMAT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Format {

	@Id
	@Column(name = "ID")
	private UUID id;

	@Column(name = "CODE", nullable = false, unique = true, length = 10)
	private String code;

	@Column(name = "NAME", nullable = false, unique = true, length = 60)
	private String name;

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
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Format)
			return this.id.equals(((Format) o).id);

		return false;
	}
}

