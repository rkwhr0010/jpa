package study.jpa.entity8;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@Entity
@Data
public class C {
	
	@MapsId("BId") //private B.BId BId;
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "a_id"),
		@JoinColumn(name = "b_id")
	})
	private B b;
	@EmbeddedId
	private C.CId id;
	private String name;
	
	@NoArgsConstructor
	@EqualsAndHashCode
	@Data
	@Embeddable
	public static class CId implements Serializable{
		private B.BId BId; //@MapsId("BId")
		@Column(name = "c_id")
		private String id;
	}

}
