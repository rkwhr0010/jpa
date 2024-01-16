package study.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import study.jpa.entity.item.Item;

@StaticMetamodel(OrderItem.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class OrderItem_ {

	
	/**
	 * @see study.jpa.entity.OrderItem#item
	 **/
	public static volatile SingularAttribute<OrderItem, Item> item;
	
	/**
	 * @see study.jpa.entity.OrderItem#count
	 **/
	public static volatile SingularAttribute<OrderItem, Integer> count;
	
	/**
	 * @see study.jpa.entity.OrderItem#orderPrice
	 **/
	public static volatile SingularAttribute<OrderItem, Integer> orderPrice;
	
	/**
	 * @see study.jpa.entity.OrderItem#id
	 **/
	public static volatile SingularAttribute<OrderItem, Long> id;
	
	/**
	 * @see study.jpa.entity.OrderItem
	 **/
	public static volatile EntityType<OrderItem> class_;

	public static final String ITEM = "item";
	public static final String COUNT = "count";
	public static final String ORDER_PRICE = "orderPrice";
	public static final String ID = "id";

}

