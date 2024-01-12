package study.jpa.entity.item;

public interface Visitor {
	void visior(Book book);
	void visior(Movie movie);
	void visior(Album album);
}
