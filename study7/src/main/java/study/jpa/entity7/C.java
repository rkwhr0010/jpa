package study.jpa.entity7;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@Entity
@IdClass(C.CId.class)
@Data
public class C {
	
	@Id
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "a_id"),
		@JoinColumn(name = "b_id")
	})
	private B b;
	
	@Id
	@Column(name = "c_id")
	private String id;
	
	private String name;
	
	@NoArgsConstructor
	@EqualsAndHashCode
	@Data
	public static class CId implements Serializable{
		private B.BId b;  //private B b;
		private String id; // private String id;
	}
}
