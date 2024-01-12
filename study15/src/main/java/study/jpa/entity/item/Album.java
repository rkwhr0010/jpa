package study.jpa.entity.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("A")
@Data
public class Album extends Item{
	private String artist;
	private String etc;
	@Override
	public String getDesciptionView() {
		return "앨범입니다.";
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visior(this);
	}
}
