package study.jpa.entity.item;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Album.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Album_ extends study.jpa.entity.item.Item_ {

	
	/**
	 * @see study.jpa.entity.item.Album#artist
	 **/
	public static volatile SingularAttribute<Album, String> artist;
	
	/**
	 * @see study.jpa.entity.item.Album#etc
	 **/
	public static volatile SingularAttribute<Album, String> etc;
	
	/**
	 * @see study.jpa.entity.item.Album
	 **/
	public static volatile EntityType<Album> class_;

	public static final String ARTIST = "artist";
	public static final String ETC = "etc";

}

