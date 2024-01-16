package study.jpa.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Member.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Member_ {

	
	/**
	 * @see study.jpa.entity.Member#orders
	 **/
	public static volatile ListAttribute<Member, Order> orders;
	
	/**
	 * @see study.jpa.entity.Member#id
	 **/
	public static volatile SingularAttribute<Member, Integer> id;
	
	/**
	 * @see study.jpa.entity.Member
	 **/
	public static volatile EntityType<Member> class_;
	
	/**
	 * @see study.jpa.entity.Member#username
	 **/
	public static volatile SingularAttribute<Member, String> username;

	public static final String ORDERS = "orders";
	public static final String ID = "id";
	public static final String USERNAME = "username";

}

