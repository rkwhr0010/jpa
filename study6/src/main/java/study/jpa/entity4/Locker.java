package study.jpa.entity4;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Locker {
	@Id
	@GeneratedValue
	@Column(name = "locker_id")
	private Long id;
	private String name;
	@OneToOne(mappedBy = "locker")
	private Member member;
}
