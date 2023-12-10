package study.jpa.entity7;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@Entity
@IdClass(B.BId.class)
@Data
public class B {
	
	@Id
	@ManyToOne
	@JoinColumn(name = "a_id") // 외래 키 식별
	private A a;
	
	@Id
	@Column(name = "b_id")
	private String id;
	private String name;;
	
	@NoArgsConstructor
	@EqualsAndHashCode
	@Data
	public static class BId implements Serializable {
		private String a;  //public A a;
		private String id; //private String id;
	}
}
