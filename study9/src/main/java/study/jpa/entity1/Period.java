package study.jpa.entity1;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Period {
	private LocalDate startDate;
	private LocalDate endDate;
	//메서드 정의해서 사용하기도 함
	public boolean isWork() {
		return true;
	}
}