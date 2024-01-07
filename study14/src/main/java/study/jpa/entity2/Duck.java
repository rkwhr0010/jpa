package study.jpa.entity2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

//@Entity
@Data
@Log4j2
public class Duck {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	
	@PrePersist
	public void prePersist() {
		log.debug("############### prePersist " + id + name);
	}
	@PostPersist
	public void postPersist() {
		log.debug("############### postPersist " + id + name);
	}
	@PostLoad
	public void postLoad() {
		log.debug("############### postLoad " + id + name);
	}
	@PreUpdate
	public void preUpdate() {
		log.debug("############### preUpdate " + id + name);
	}
	@PostUpdate
	public void postUpdate() {
		log.debug("############### postUpdate " + id + name);
	}
	@PreRemove
	public void preRemove() {
		log.debug("############### preRemove " + id + name);
	}
	@PostRemove
	public void postRemove() {
		log.debug("############### postRemove " + id + name);
	}
}
