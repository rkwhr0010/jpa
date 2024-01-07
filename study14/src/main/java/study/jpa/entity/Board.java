package study.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
public class Board {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String content;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "comment_id")
	@OrderColumn(name = "position")
	private List<Comment> comments = new ArrayList<>();
	
	public Board(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
