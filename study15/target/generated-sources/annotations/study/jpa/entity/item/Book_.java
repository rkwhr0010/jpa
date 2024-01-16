package study.jpa.entity.item;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Book.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Book_ extends study.jpa.entity.item.Item_ {

	
	/**
	 * @see study.jpa.entity.item.Book#author
	 **/
	public static volatile SingularAttribute<Book, String> author;
	
	/**
	 * @see study.jpa.entity.item.Book#isbn
	 **/
	public static volatile SingularAttribute<Book, String> isbn;
	
	/**
	 * @see study.jpa.entity.item.Book
	 **/
	public static volatile EntityType<Book> class_;

	public static final String AUTHOR = "author";
	public static final String ISBN = "isbn";

}

