package study.jpa.entity3;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

//@Entity
@Data
public class Album extends Item {

	private String artist;
}
