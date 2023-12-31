package study.jpa.entity2;

import jakarta.persistence.Embeddable;

@Embeddable
public class Zipcode {
	private String zip;
	private String plusFour;
}