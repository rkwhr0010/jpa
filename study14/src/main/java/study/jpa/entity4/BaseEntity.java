package study.jpa.entity4;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity {
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
}
