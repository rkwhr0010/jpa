package study.jpa.entity;

import java.util.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.QueryHint;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Data
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@NamedQuery(
//		hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")},
//		name = "Board.findByUsername",
//		query = "select b from Board b where b.id = :id")
public class Board {
	@Id
	private String id;
	private String title;
	
	@OneToMany
	@JoinColumn(name = "board_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<Comment> comments = new ArrayList<>();
	
	@Version
	private Integer version;
}
