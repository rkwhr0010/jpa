package study.jpa.entity8;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@Entity
@Data
public class B {
	@MapsId("AId") //private String AId; 
	@ManyToOne
	@JoinColumn(name = "a_id")
	private A a;
	
	@EmbeddedId
	private B.BId id;
	private String name;;
	
	@NoArgsConstructor
	@EqualsAndHashCode
	@Data
	@Embeddable
	public static class BId implements Serializable {
		private String AId; //@MapsId("AId")
		@Column(name = "b_id")
		private String id;
	}
}
