package study.jpa.entity5;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		save();
//		findBasic();
//		find();
//		findDistinct();
//		findDto();
//		findTuple();
//		findGroupBy();
//		findJoin();
//		findSubquery();
//		findSubqueryCorrelate();
//		findIn();
//		findCase();
//		findParam();
//		findNativeFunc();
//		findDynamic();
		findMeta();
		
		emf.close();
	}
	

	
	private static void findMeta() {
		logic(em -> {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Member> cq = cb.createQuery(Member.class);
			
			Root<Member> m = cq.from(Member.class);
			cq.select(m)
//				.where(cb.gt(m.get("age"), 20));
				.where(cb.gt(m.get(Member5_.age), 20));
			
			em.createQuery(cq).getResultList();
		});
	}



	private static void findDynamic() {
		logic(em -> {
			//입력 파라미터라고 가정
			Integer age = 10;
			String username = "";
			String teamName = "레드팀";
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Member> cq = cb.createQuery(Member.class);
			
			Root<Member> m = cq.from(Member.class);
			Join<Member, Team> t = m.join("team");
			
			List<Predicate> predicates = new ArrayList<>();
			
			if(age != null && age != 0) 
				predicates.add(
						cb.equal(m.get("age"), 
						cb.parameter(Integer.class, "age")));
			if(username != null && !"".equals(username)) 
				predicates.add(
						cb.equal(m.get("username"), 
						cb.parameter(String.class, "username")));
			if(teamName != null && !"".equals(teamName)) 
				predicates.add(
						cb.equal(t.get("name"), 
						cb.parameter(String.class, "teamName")));
			
			cq.where(cb.and(predicates.toArray(new Predicate[0])));
			
			TypedQuery<Member> typedQuery = em.createQuery(cq);
			if(age != null && age != 0) 
				typedQuery.setParameter("age", age);
			if(username != null && !"".equals(username)) 
				typedQuery.setParameter("username", username);
			if(teamName != null && !"".equals(teamName)) 
				typedQuery.setParameter("teamName", teamName);
			
			typedQuery.getResultList();
		});
	}



	private static void findNativeFunc() {
		logic(em->{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			Root<Member> m = cq.from(Member.class);
			Expression<Long> function = cb.function("SUM", Long.class, m.get("age"));
			cq.select(function);
			em.createQuery(cq).getResultList();
		});
	}
	private static void findParam() {
		logic(em->{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			
			CriteriaQuery<Member> cq = cb.createQuery(Member.class);
			Root<Member> m = cq.from(Member.class);
			
			cq.select(m)
				.where(cb.equal(m.get("username"), cb.parameter(String.class, "usernameParam")));
			em.createQuery(cq)
				.setParameter("usernameParam", "홍길동")
				.getResultList();
			// 하이버네이트에서 위와 아래는 똑같다.
			cq = cb.createQuery(Member.class);
			m = cq.from(Member.class);
			
			cq.select(m)
				.where(cb.equal(m.get("username"), "홍길동"));
			em.createQuery(cq).getResultList();
		});
	}

	private static void findCase() {
		logic(em->{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			
			CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
			Root<Member> m = cq.from(Member.class);
			
			cq.multiselect(
					m.get("username"),
					cb.selectCase()
						.when(cb.ge(m.<Integer>get("age"), 60), 600)
						.when(cb.le(m.<Integer>get("age"), 15), 500)
						.otherwise(1000)
					);
			em.createQuery(cq).getResultList();
		});
	}



	private static void findIn() {
		logic(em->{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Member> cq = cb.createQuery(Member.class);
			Root<Member> m = cq.from(Member.class);
			
			cq.select(m)
				.where(cb.in(m.get("username")).value("홍길동").value("임꺽정"));
			
			em.createQuery(cq).getResultList();
		});
	}



	private static void findSubqueryCorrelate() {
		logic(em->{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			
			CriteriaQuery<Member> mainQ = cb.createQuery(Member.class);
			Root<Member> m = mainQ.from(Member.class);
			
			Subquery<Team> subquery = mainQ.subquery(Team.class);
			//핵심 메인 쿼리의 별칭을 가져온다.
			Root<Member> subM = subquery.correlate(m);
			
			Join<Member, Team> t = subM.join("team");
			subquery.select(t)
				.where(cb.equal(t.get("name"), "블루팀"));
			
			mainQ.select(m)
				.where(cb.exists(subquery));
			
			em.createQuery(mainQ).getResultList();
		});
	}
	
	private static void findSubquery() {
		logic(em->{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			
			CriteriaQuery<Member> mainQ = cb.createQuery(Member.class);
			Subquery<Double> subquery = mainQ.subquery(Double.class);
			
			Root<Member> m2 = subquery.from(Member.class);
			subquery.select(cb.avg(m2.<Integer>get("age")));
			
			Root<Member> m = mainQ.from(Member.class);
			mainQ.select(m)
				.where(cb.ge(m.<Integer>get("age"), subquery));
			
			em.createQuery(mainQ).getResultList();
		});
	}

	private static void findJoin() {
		logic(em->{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			
			CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
			
			Root<Member> m = cq.from(Member.class);
			Join<Member, Team> t = m.join("team", JoinType.INNER);
//			Fetch<Object, Object> t = m.fetch("team");
			cq.multiselect(m, t)
				.where(cb.equal(t.get("name"), "레드팀"));
			
			List<Object[]> resultList = em.createQuery(cq).getResultList();
			resultList.forEach(row -> {
				System.out.println(row[0]);
				System.out.println(row[1]);
			});
		});
	}
	
	private static void findGroupBy() {
		logic(em->{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			
			CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
			
			Root<Member> m = cq.from(Member.class);
			
			Expression<Integer> max = cb.max(m.<Integer>get("age"));
			Expression<Integer> min = cb.min(m.<Integer>get("age"));
			
			Path<Object> groupBy = m.get("team").get("name");
			cq.multiselect(groupBy,
					max,
					min);
			cq.groupBy(groupBy)
				.having(cb.greaterThan(min, 10));
			
			em.createQuery(cq).getResultList();
		});
	}

	private static void findTuple() {
		logic(em->{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			
			CriteriaQuery<Tuple> cq = cb.createTupleQuery();
//			CriteriaQuery<Tuple> createQuery = cb.createQuery(Tuple.class);
			
			Root<Member> m = cq.from(Member.class);
			cq.multiselect(m.get("username").alias("username"),
					m.get("age").alias("age")
					);
//			cq.select(cb.tuple(m.get("username").alias("username"),
//					m.get("age").alias("age"))
//					);
			
			TypedQuery<Tuple> typedQuery = em.createQuery(cq);
			List<Tuple> resultList = typedQuery.getResultList();
			for (Tuple t : resultList) {
				String username = t.get("username", String.class);
				Integer age = t.get("age", Integer.class);
				System.out.println(username + "  " + age);
			}
		});
	}
	
	private static void findDto() {
		logic(em->{
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<Dto> cq = cb.createQuery(Dto.class);
			Root<Member> m = cq.from(Member.class);
			
			cq.select(cb.construct(Dto.class, m.get("username"), m.get("age")));
			em.createQuery(cq).getResultList();
		});
	}
	
	private static void findDistinct() {
		logic(em->{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			
			CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
			Root<Member> m = cq.from(Member.class);
			cq.multiselect(m.get("username"), m.get("age")).distinct(true);
			em.createQuery(cq).getResultList();
		});
	}

	private static void find() {
		logic(em->{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			
			//반환 타입 엔티티
			CriteriaQuery<Member> cq = cb.createQuery(Member.class);
			Root<Member> m = cq.from(Member.class);
			cq.select(m);
			TypedQuery<Member> typedQuery = em.createQuery(cq);
			typedQuery.getResultList();
			
			//반환 타입 알수 없거나 스칼라 타입 둘 이상
			CriteriaQuery<Object> cq2 = cb.createQuery();
			m = cq2.from(Member.class);
			cq2.multiselect(m.get("username"), m.get("age"));
			em.createQuery(cq2).getResultList();
			//반환 타입 스칼라 타입 둘 이상
			CriteriaQuery<Object[]> cq3 = cb.createQuery(Object[].class);
			m = cq3.from(Member.class);
			cq3.multiselect(m.get("username"), m.get("age"));
			em.createQuery(cq3).getResultList();
			
			CriteriaQuery<Object[]> cq4 = cb.createQuery(Object[].class);
			m = cq4.from(Member.class);
			cq4.select(cb.array(m.get("username"), m.get("age")));
			em.createQuery(cq4).getResultList();
		});
	}

	private static void findBasic() {
		logic(em->{
			//쿼리 빌더 생성
			CriteriaBuilder cb = em.getCriteriaBuilder();
			//반환 타입 지정, CriteriaQuery 생성
			CriteriaQuery<Member> cq = cb.createQuery(Member.class);
			//from 절
			Root<Member> m = cq.from(Member.class);
			
			//select 절
			cq.select(m)
			//where 절
				.where(cb.equal(m.get("username"), "홍길동"),
						cb.greaterThan(m.<Integer>get("age"), 10))
			//order by 절
				.orderBy(cb.desc(m.get("age")));
			
			TypedQuery<Member> typedQuery = em.createQuery(cq);
			typedQuery.getResultList();
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
