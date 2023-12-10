package study.jpa.entity10;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

//@Entity
@Data
public class Board {
	@Id
	@GeneratedValue
	@Column(name = "board_id")
	private Long id;
	
	private String title;
	
	@OneToOne(mappedBy = "board")
	private BoardDetail boardDetail; 
}
