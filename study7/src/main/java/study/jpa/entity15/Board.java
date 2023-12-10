package study.jpa.entity15;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import lombok.Data;

@Data
//@Entity
@Table(name = "Board")
@SecondaryTable(name = "Board_Detail", 
	pkJoinColumns = @PrimaryKeyJoinColumn(name = "board_detail_id"))
public class Board {
	@Id
	@GeneratedValue
	@Column(name = "board_id")
	private Long id;
	private String title;
	
	@Column(table = "Board_Detail")
	private String content;
}
