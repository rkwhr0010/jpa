package study.jpa.entity6;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@Entity
@Data
public class Master {
	@EmbeddedId
	private Master.MasterId id;
	
	private String name;
	
	@EqualsAndHashCode
	@AllArgsConstructor
	@NoArgsConstructor
	@Embeddable
	public static class MasterId implements Serializable{
		@Column(name = "master_id1")
		private Long id1;
		@Column(name = "master_id2")
		private Long id2;
	}

}
