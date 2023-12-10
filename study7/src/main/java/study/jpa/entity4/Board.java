package study.jpa.entity4;

import jakarta.persistence.Entity;
import lombok.Data;

//@Entity
@Data
public class Board extends BaseEntity {
	private String name;
	private String contents;
}
