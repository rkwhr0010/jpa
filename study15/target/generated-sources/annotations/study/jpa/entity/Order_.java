package study.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Order.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Order_ {

	
	/**
	 * @see study.jpa.entity.Order#member
	 **/
	public static volatile SingularAttribute<Order, Member> member;
	
	/**
	 * @see study.jpa.entity.Order#id
	 **/
	public static volatile SingularAttribute<Order, Long> id;
	
	/**
	 * @see study.jpa.entity.Order
	 **/
	public static volatile EntityType<Order> class_;

	public static final String MEMBER = "member";
	public static final String ID = "id";

}

