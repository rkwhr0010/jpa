package study.jpa.entity4.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("B")
@Data
public class Book extends Item{
	private String author;
	private String isbn;
}
