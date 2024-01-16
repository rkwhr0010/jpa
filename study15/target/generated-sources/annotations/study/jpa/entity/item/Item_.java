package study.jpa.entity.item;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Item.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Item_ {

	
	/**
	 * @see study.jpa.entity.item.Item#price
	 **/
	public static volatile SingularAttribute<Item, Integer> price;
	
	/**
	 * @see study.jpa.entity.item.Item#name
	 **/
	public static volatile SingularAttribute<Item, String> name;
	
	/**
	 * @see study.jpa.entity.item.Item#stockQuantity
	 **/
	public static volatile SingularAttribute<Item, Integer> stockQuantity;
	
	/**
	 * @see study.jpa.entity.item.Item#id
	 **/
	public static volatile SingularAttribute<Item, Long> id;
	
	/**
	 * @see study.jpa.entity.item.Item
	 **/
	public static volatile EntityType<Item> class_;

	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String STOCK_QUANTITY = "stockQuantity";
	public static final String ID = "id";

}

