package study.jpa.entity3;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DuckListener {
	@PrePersist
	public void prePersist(Object obj) {
		log.debug("############### prePersist " + obj);
	}
	@PostPersist
	public void postPersist(Object obj) {
		log.debug("############### postPersist " + obj);
	}
	@PostLoad
	public void postLoad(Object obj) {
		log.debug("############### postLoad " + obj);
	}
	@PreUpdate
	public void preUpdate(Object obj) {
		log.debug("############### preUpdate " + obj);
	}
	@PostUpdate
	public void postUpdate(Object obj) {
		log.debug("############### postUpdate " + obj);
	}
	@PreRemove
	public void preRemove(Object obj) {
		log.debug("############### preRemove " + obj);
	}
	@PostRemove
	public void postRemove(Object obj) {
		log.debug("############### postRemove " + obj);
	}
}
