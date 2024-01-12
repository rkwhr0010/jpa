package study.jpa.entity.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("M")
@Data
public class Movie extends Item{
	private String director;
	private String actor;
	@Override
	public String getDesciptionView() {
		return "영화입니다";
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visior(this);
	}
}
