package study.jpa.entity10;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;

//@Entity
@Data
public class BoardDetail {
	@Id
	private Long id;
	
	@MapsId("id")
	@OneToOne
	@JoinColumn(name = "board_id")
	private Board board;
	
	private String content;
}
