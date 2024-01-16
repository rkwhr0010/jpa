package study.jpa.entity.item;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Movie.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Movie_ extends study.jpa.entity.item.Item_ {

	
	/**
	 * @see study.jpa.entity.item.Movie#actor
	 **/
	public static volatile SingularAttribute<Movie, String> actor;
	
	/**
	 * @see study.jpa.entity.item.Movie#director
	 **/
	public static volatile SingularAttribute<Movie, String> director;
	
	/**
	 * @see study.jpa.entity.item.Movie
	 **/
	public static volatile EntityType<Movie> class_;

	public static final String ACTOR = "actor";
	public static final String DIRECTOR = "director";

}

