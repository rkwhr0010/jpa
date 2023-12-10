package study.jpa.entity5;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.Data;

//@Entity
@Data
public class Detail {
	
	@Id
	@GeneratedValue
	@Column(name = "detail_id")
	private Long id;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "fk_master_id1", referencedColumnName = "master_id1"),
		@JoinColumn(name = "fk_master_id2", referencedColumnName = "master_id2")
	})
	private Master master;
}
