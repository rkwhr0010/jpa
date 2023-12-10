package study.jpa.entity2;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;

//@Entity
@DiscriminatorValue("B")
@PrimaryKeyJoinColumn(name = "book_id")
@Data
public class Book extends Item{
	private String author;
	private String isbn;
}
