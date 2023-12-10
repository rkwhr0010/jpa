package study.jpa.entity4;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

//@Entity
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "member_id"))
})
@Data
public class Member extends BaseEntity{
	private String name;
}
