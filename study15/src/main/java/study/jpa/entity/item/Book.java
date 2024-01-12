package study.jpa.entity.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("B")
@Data
public class Book extends Item{
	private String author;
	private String isbn;
	@Override
	public String getDesciptionView() {
		return "책입니다";
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visior(this);
	}
}
