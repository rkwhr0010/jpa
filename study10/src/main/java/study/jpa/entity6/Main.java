package study.jpa.entity6;

import static study.jpa.entity6.QMember.*;
import static study.jpa.entity6.QTeam.*;
import static study.jpa.entity6.QOrder.*;
import static study.jpa.entity6.QProduct.*;

import java.util.List;
import java.util.function.Consumer;

import org.hibernate.jpamodelgen.util.StringUtil;
import org.hibernate.query.criteria.JpaSubQuery;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		save();
//		findBasic();
//		findWhere();
//		findPaging();
//		findGroupBy();
//		findJoin();
//		findThetaJoin();
//		findSubquery();
//		findDto();
//		findUDBatch();
//		findDynamic();
		findDelegateMethod();
		
		emf.close();
	}
	
	private static void findDelegateMethod() {
		logic(em -> {
			JPAQuery<Object> query = new JPAQuery<>(em);
			//화면에서 넘어온 조회 조건이라 가능
			String paramAge = "29";
			String paramName = "홍길동";
			
			BooleanBuilder builder = new BooleanBuilder();
			if (!"".equals(paramAge) && Integer.valueOf(paramAge).compareTo(30) <= 0) {
				builder.and(member.isYoung());
			}
			if (!"".equals(paramName)) {
				builder.and(member.username.eq(paramName));
			}
			
			query.from(member)
			.where(builder)
			.fetch();
		});
	}
	private static void findDynamic() {
		logic(em -> {
			JPAQuery<Object> query = new JPAQuery<>(em);
			//화면에서 넘어온 조회 조건이라 가능
			String paramAge = "";
			String paramName = "홍길동";
			
			BooleanBuilder builder = new BooleanBuilder();
			// 간략화된 검사 로직
			if (!"".equals(paramAge)) {
				builder.and(member.age.goe(Integer.valueOf(paramAge)));
			}
			if (!"".equals(paramName)) {
				builder.and(member.username.eq(paramName));
			}
			
			query.from(member)
				.where(builder)
				.fetch();
		});
	}

	private static void findUDBatch() {
		logic(em->{
			JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, member);
			jpaUpdateClause.where(member.age.goe(10))
				.set(member.age, member.age.add(1))
				.execute();
			
			JPADeleteClause jpaDeleteClause = new JPADeleteClause(em, member);
			jpaDeleteClause.where(member.age.goe(10))
				.execute();
		});
	}

	private static void findDto() {
		logic(em->{
			JPAQuery<Object> query = new JPAQuery<>(em);
			query.select(
					// 값 설정 시 생성자 사용
					Projections.constructor(Dto.class, member.username, member.age)
					// 값 설정 시 Setter 메서드 사용
//					Projections.fields(Dto.class, member.username.as("name"), member.age)
					// 값 설정 시 필드 직접 사용
//					Projections.bean(Dto.class, member.username.as("name"), member.age)
					)
				.from(member)
				.fetch();
		});
	}
	
	private static void findSubquery() {
		logic(em->{
			JPAQuery<Product> query = new JPAQuery<>(em);
			query.from(product)
				.where(product.price.eq(
						JPAExpressions.select(product.price.max()).from(product))
						)
				.fetch();
		});
	}
	
	private static void findJoin() {
		logic(em->{
			JPAQuery<Tuple> query = new JPAQuery<>(em);
			
			query.from(order)
				.join(order.member, member)
//				.leftJoin(order.member, member)
//				.rightJoin(order.member, member)
				.on(order.product.price.goe(10000))
				.fetch();
		});
	}

	private static void findGroupBy() {
		logic(em->{
			JPAQuery<Tuple> query = new JPAQuery<>(em);
			
			List<Tuple> list = query.from(member)
					.select(member.team, member.count())
					.groupBy(member.team)
					.fetch();
			System.out.println(list);
		});
	}
	
	
	private static void findPaging() {
		logic(em->{
			JPAQuery<Member> query = new JPAQuery<>(em);
			
			List<Member> fetch = query.from(member)
			.where(member.age.goe(10).or(member.team.name.eq("블루팀")))
			.orderBy(member.username.desc())
			.offset(1).limit(5)
			.fetch();
		});
	}
	
	private static void findWhere() {
		logic(em->{
			JPAQuery<Member> query = new JPAQuery<>(em);
			
			query.from(member)
				.where(member.age.goe(10).or(member.team.name.eq("블루팀")))
				.orderBy(member.username.desc())
				.fetch();
		});
	}

	private static void findBasic() {
		logic(em->{
			JPAQuery<Member> query = new JPAQuery<>(em);
			QMember qMember = new QMember("m");
			
			query.from(qMember)
				.where(qMember.username.eq("홍길동"))
				.orderBy(qMember.username.desc())
				.fetch();
		});
	}



	private static void save() {
		logic(em -> {
			Member m1 = new Member();
			m1.setAge(20);
			m1.setUsername("홍길동");
			
			Member m2 = new Member();
			m2.setAge(30);
			m2.setUsername("임꺽정");
			
			Member m3 = new Member();
			m3.setAge(40);
			m3.setUsername("유관순");
			
			Team t1 = new Team();
			t1.setName("레드팀");
			t1.addMember(m1);
			t1.addMember(m2);
			em.persist(t1);
			
			Team t2 = new Team();
			t2.setName("블루팀");
			t2.addMember(m2);
			t2.addMember(m3);
			em.persist(t2);
			
			Product p1 = new Product();
			p1.setName("삼겹살");
			p1.setPrice(10000);
			p1.setStockAmount(10);
			em.persist(p1);
			
			Product p2 = new Product();
			p2.setName("오징어");
			p2.setPrice(15000);
			p2.setStockAmount(20);
			em.persist(p2);
			
			Product p3 = new Product();
			p3.setName("돈까스");
			p3.setPrice(12000);
			p3.setStockAmount(30);
			em.persist(p3);
			
			Order o1 = new Order();
			o1.setAddress(new Address("서울시", "을지로", "12345"));
			o1.setMember(m1);
			o1.setOrderAmount(1);
			o1.setProduct(p1);
			em.persist(o1);
			
			Order o2 = new Order();
			o2.setAddress(new Address("서울시", "종로", "54321"));
			o2.setMember(m1);
			o2.setOrderAmount(1);
			o2.setProduct(p2);
			em.persist(o2);
			
			Order o3 = new Order();
			o3.setAddress(new Address("서울시", "종로", "54321"));
			o3.setMember(m2);
			o3.setOrderAmount(1);
			o3.setProduct(p2);
			em.persist(o3);
			
			Order o4 = new Order();
			o4.setAddress(new Address("서울시", "종로", "54321"));
			o4.setMember(m3);
			o4.setOrderAmount(1);
			o4.setProduct(p2);
			em.persist(o4);
			
			Order o5 = new Order();
			o5.setAddress(new Address("서울시", "통일로", "55555"));
			o5.setMember(m3);
			o5.setOrderAmount(1);
			o5.setProduct(p3);
			em.persist(o5);
		});
	}

	static void logic(Consumer<EntityManager> logic) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			logic.accept(em);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
}
