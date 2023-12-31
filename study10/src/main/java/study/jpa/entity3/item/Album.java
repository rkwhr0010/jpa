package study.jpa.entity3.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

//@Entity
@DiscriminatorValue("A")
@Data
public class Album extends Item{
	private String artist;
	private String etc;
}
