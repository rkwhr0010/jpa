package study.jpa.entity4;

import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseEntity {
	@Id
	@GeneratedValue
	private Long id;
	private LocalDateTime reg_date; // 등록일자
	private String reg_id; //등록자 ID
	private LocalDateTime mod_date; // 수정일자
	private String mod_id; // 수정자 ID
}
