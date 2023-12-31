package study.jpa.entity4;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		save();
//		findEntity();
//		findEntityPK();
//		findEntityFK();
		findNamedQuery();
		
		emf.close();
	}
	
	
	private static void findNamedQuery() {
		logic(em -> {
			em.createNamedQuery("Member.findByUsername", Member.class)
					.setParameter("username", "홍길동")
					.getResultList();
		});
	}
	
	private static void findEntityFK() {
		logic(em -> {
			Team team = em.find(Team.class, 1L);
			
			String sql = "select m from Member m where m.team = :team";
			List<Member> resultList = em.createQuery(sql, Member.class)
					.setParameter("team", team)
					.getResultList();
		});
	}
	
	private static void findEntityPK() {
		logic(em -> {
			String sql = "select m from Member m where m.id = :id";
			List<Member> resultList = em.createQuery(sql, Member.class)
				.setParameter("id", 1L)
				.getResultList();
			
			sql = "select m from Member m where m = :member";
			resultList = em.createQuery(sql, Member.class)
					.setParameter("member", resultList.get(0))
					.getResultList();
			
		});
	}

	private static void findEntity() {
		logic(em -> {
			String sql = "select count(m) from Member m";
			em.createQuery(sql).getResultList();
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
