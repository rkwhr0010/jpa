package study.jpa.entity3;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;

//@Entity
@PrimaryKeyJoinColumn(name = "book_id") // 의미 없는 설정
@Data
public class Book extends Item{
	private String author;
	private String isbn;
}
