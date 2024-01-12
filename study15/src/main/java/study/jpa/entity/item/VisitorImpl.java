package study.jpa.entity.item;

public class VisitorImpl implements Visitor{
	public void visior(Book book) {
		System.out.println("book.getClass()  + " + book.getClass());
	}
	public void visior(Movie movie) {
		System.out.println("movie.getClass()  + " + movie.getClass());
	}
	public void visior(Album album) {
		System.out.println("album.getClass()  + " + album.getClass());
	}
}
