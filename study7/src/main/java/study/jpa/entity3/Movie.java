package study.jpa.entity3;

import jakarta.persistence.Entity;
import lombok.Data;

//@Entity
@Data
public class Movie extends Item {
	private String director;
	private String actor;
}