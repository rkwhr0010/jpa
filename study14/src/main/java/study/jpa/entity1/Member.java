package study.jpa.entity1;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

//@Entity
@Data
@Convert(converter = BooleanToYNConverter.class, attributeName = "vip")
public class Member {
	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private boolean vip;
}
