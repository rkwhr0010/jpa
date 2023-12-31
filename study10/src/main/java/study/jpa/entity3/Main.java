package study.jpa.entity3;

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
//		findStateField();
//		findSingleAssociationField();
//		findCollectionAssociationField();
//		findSubquery();
//		findSubqueryExists();
//		findSubqueryAllAnySome();
//		findSubqueryIn();
//		findCollection();
//		findInheritance();
//		findType();
		findTreat();
		
		emf.close();
	}
	


	private static void findTreat() {
		logic(em->{
			String sql = "select i from Item i"
					+ " where treat(i as Book).author = 'kim'";
			em.createQuery(sql).getResultList();
		});
	}
	private static void findType() {
		logic(em->{
			String sql = "select i from Item i"
					+ " where type(i) in (Book, Movie)";
			em.createQuery(sql).getResultList();
		});
	}
	
	private static void findInheritance() {
		logic(em->{
			String sql = "select i from Item i";
			em.createQuery(sql).getResultList();
		});
	}

	private static void findCollection() {
		logic(em->{
			String sql = "select m from Member m"
					+ " where m.orders is empty";
			String sameSql = "select m from Member m"
					+ " where not  exists (select 1 from Order o where m.id = o.member)";
			em.createQuery(sql, Team.class).getResultList();
			
			sql = "select t from Team t"
					+ " where 1L member of t.members";
			em.createQuery(sql, Team.class).getResultList();
		});
	}
	private static void findSubqueryIn() {
		logic(em->{
			String sql = "select t from Team t "
					+ "where t in (select t2 from Team t2 join t2.members m2 where m2.age >= 20)";
			em.createQuery(sql, Team.class).getResultList();
		});
	}



	private static void findSubqueryAllAnySome() {
		logic(em -> {
			String sql = "select m from Member m "
					+ "where m.team > all (select p.stockAmount from Product p)";
			System.out.println(em.createQuery(sql, Member.class).getResultList());
			sql = "select m from Member m "
					+ "where m.team = any (select p.stockAmount from Product p)";
			System.out.println(em.createQuery(sql, Member.class).getResultList());
			sql = "select m from Member m "
					+ "where m.team = some (select p.stockAmount from Product p)";
			System.out.println(em.createQuery(sql, Member.class).getResultList());
		});
	}
	
	private static void findSubqueryExists() {
		logic(em -> {
			String sql = "select m from Member m "
					+ "where exists (select t from m.team t where t.name = '블루팀')";
			System.out.println(em.createQuery(sql, Member.class).getResultList());
		});
	}
	
	private static void findSubquery() {
		logic(em -> {
			String sql = "select m from Member m "
					+ "where m.age > (select avg(m2.age) from Member m2)";
			
			System.out.println(em.createQuery(sql, Member.class).getResultList());
		});
	}



	private static void findStateField() {
		logic(em -> {
			// 상태 필드 경로 탐색
			String sql = "select m.username from Member m";
			em.createQuery(sql).getResultList();
		});
	}
	private static void findSingleAssociationField() {
		logic(em -> {
			// 단일 값 연관 경로 탐색, 묵시적 조인
			String sql = "select o.member from Order o";
			// 단일 값 연관 경로 탐색, 명시적 조인
			String sql2 = "select o.member from Order o join o.member";
			// 복잡 예시
			String sql3 = "select o.member.team"
					+ " from Order o"
					+ " where o.product.name = '삼겹살'";
			
			em.createQuery(sql).getResultList();
			em.createQuery(sql2).getResultList();
			System.out.println(em.createQuery(sql3).getResultList()); ;
		});
	}
	private static void findCollectionAssociationField() {
		logic(em -> {
			// 컬렉션 값 연관 경로 탐색
			String sql = "select t.members from Team t";
			// 컬렉션 값 연관 경로 탐색 이후 더는 탐색 불가
			String sql2 = "select t.members.username from Team t";
			// 컬렉션 값 연관 경로를 별칭을 주면 더 탐색 가능
			String sql3 = "select m.username from Team t join t.members m";
			
			em.createQuery(sql).getResultList();
//			em.createQuery(sql2).getResultList();
			em.createQuery(sql3).getResultList();
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
