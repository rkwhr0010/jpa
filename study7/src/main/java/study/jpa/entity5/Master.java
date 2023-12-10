package study.jpa.entity5;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@Entity
@IdClass(Master.MasterId.class)
@Data
public class Master {
	@Id
	@GeneratedValue
	@Column(name = "master_id1")
	private Long id1;
	
	@Id
	@GeneratedValue
	@Column(name = "master_id2")
	private Long id2;
	
	private String name;
	
	@EqualsAndHashCode
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MasterId implements Serializable{
		private Long id1;
		private Long id2;
	}

}
