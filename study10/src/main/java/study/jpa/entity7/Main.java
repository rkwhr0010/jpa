package study.jpa.entity7;

import java.util.List;
import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.Persistence;
import jakarta.persistence.StoredProcedureQuery;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		save();
//		findEntity();
//		findScalar();
//		sqlResultSetMapping();
//		namedNativeQuery();
//		paging();
//		storedProcedureQuery();
//		storedProcedureQuery2();
//		namedStoredProcedureQuery();
//		bulk();
//		bulk2();
//		bulk3();
//		bulk4();
//		bulk5();
//		example();
		flush();
		
		
		emf.close();
	}
	
	
	private static void flush() {
		logic(em -> {
			// 기본 값이 AUTO
			em.setFlushMode(FlushModeType.COMMIT); 
			Product p1 = em.find(Product.class, 1L);
			p1.setPrice(99999);
			System.out.println(p1);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			String sql = "select p from Product p where p.id = 1L";
			Product p2 = em.createQuery(sql, Product.class)
					.setFlushMode(FlushModeType.AUTO) // 이 쿼리에만 적용
					.getSingleResult();
			System.out.println(p2);
			
			
			
			
			
			String sql2 = "select p.price from Product p where p.id = 1L";
			Object price = em.createQuery(sql2)
					.getSingleResult();
			System.out.println(price);
		});
	}


	private static void example() {
		logic(em->{
			//최초 조회 영속성 컨텍스트에 없으므로 DB 조회 후 영속성 컨텍스트에 저장 후 반환
			Product p1 = em.find(Product.class, 1L);
			//이미 1L 식별자를 가진 엔티티가 영속성 컨텍스트에 존재 조회 안함
			Product p2 = em.find(Product.class, 1L);
			//영속성 컨텍스트로 먼저 조회를 하는게 아님
			//일단 DB로 조회 후 식별자를 기반으로 영속성 컨텍스트에 존재하면, 조회된 결과는
			//버려지고, 영속성 컨텍스트에 있는 엔티티 반환
			Product p3 = em.createQuery("select p from Product p where p.id = 1", Product.class)
				.getSingleResult();
			//DB로 질의함
			Product p4 = em.createQuery("select p from Product p where p.id = 1", Product.class)
					.getSingleResult();
			
			
		});
	}


	private static void bulk5() {
		logic(em -> {
			Product p1 = em.find(Product.class, 1L);
			System.out.println("수정 전 : " + p1);
			
			String sql =
					"UPDATE Product p "
							+ "SET p.price = CAST(p.price * 1.1 AS integer)";
			int resultCount = em.createQuery(sql)
					.executeUpdate();
			// 해결책 벌크 연산 후 영속성 컨텍스트 초기화
			em.clear();
			
			p1 = em.find(Product.class, 1L);
			System.out.println("수정 후 : " + p1);
		});
	}
	
	private static void bulk4() {
		logic(em -> {
			// 해결책 벌크 연산을 가장 먼저 수행
			String sql =
					"UPDATE Product p "
							+ "SET p.price = CAST(p.price * 1.1 AS integer)";
			int resultCount = em.createQuery(sql)
					.executeUpdate();
			
			Product p1 = em.find(Product.class, 1L);
			System.out.println("수정 후 : " + p1);
		});
	}
	
	private static void bulk3() {
		logic(em -> {
			Product p1 = em.find(Product.class, 1L);
			System.out.println("수정 전 : " + p1);
			
			String sql =
					"UPDATE Product p "
							+ "SET p.price = CAST(p.price * 1.1 AS integer)";
			int resultCount = em.createQuery(sql)
					.executeUpdate();
			//해결책 해당 엔티티 갱신
			em.refresh(p1);
			
			System.out.println("수정 후 : " + p1);
		});
	}
	
	private static void bulk2() {
		logic(em -> {
			Product p1 = em.find(Product.class, 1L);
			System.out.println("수정 전 : " + p1);
			
			String sql =
					"UPDATE Product p "
							+ "SET p.price = CAST(p.price * 1.1 AS integer)";
			int resultCount = em.createQuery(sql)
					.executeUpdate();
			
			p1 = em.find(Product.class, 1L);
			System.out.println("수정 후 : " + p1);
		});
	}
	
	private static void bulk() {
		logic(em -> {
			String sql =
					"UPDATE Product p "
					+ "SET p.price = CAST(p.price * 1.1 AS integer)";
			int resultCount = em.createQuery(sql)
				.executeUpdate();
			System.out.println(resultCount);
		});
	}

	private static void namedStoredProcedureQuery() {
		logic(em -> {
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("multiply");
			query.setParameter("inParam", 100);
			query.execute();
			System.out.println(query.getOutputParameterValue("outParam"));
		});
	}

	private static void storedProcedureQuery2() {
		logic(em -> {
			StoredProcedureQuery query = em.createStoredProcedureQuery("proc_multiply");
			query.registerStoredProcedureParameter("inParam", Integer.class, ParameterMode.INOUT);
			query.registerStoredProcedureParameter("outParam", Integer.class, ParameterMode.INOUT);
			
			query.setParameter("inParam", 100);
			query.setParameter("outParam", 100);
			query.execute();
			System.out.println(query.getOutputParameterValue("inParam"));
			System.out.println(query.getOutputParameterValue("outParam"));
		});
	}
	
	private static void storedProcedureQuery() {
		logic(em -> {
			StoredProcedureQuery query = em.createStoredProcedureQuery("proc_multiply");
			query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.INOUT);
			query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);
			
			query.setParameter(1, 100);
			query.execute();
			System.out.println(query.getOutputParameterValue(1));
			System.out.println(query.getOutputParameterValue(2));
		});
	}

	private static void paging() {
		logic(em -> {
			String sql =
					"SELECT ID, AGE, NAME, TEAM_ID "
							+ "FROM Member";
			em.createNativeQuery(sql, Member.class)
				.setFirstResult(1)
				.setMaxResults(5)
				.getResultList();
		});
	}

	private static void namedNativeQuery() {
		logic(em -> {
			em.createNamedQuery("Member.memberSQL", Member.class)
				.setParameter(1, 10)
				.getResultList();
			em.createNamedQuery("Member.memberJoinOrders")
				.getResultList();
		});
	}

	private static void sqlResultSetMapping() {
		logic(em -> {
			String sql =
					"SELECT M.ID, M.AGE, M.NAME, M.TEAM_ID, I.ORDER_COUNT "
					+ "FROM Member M "
					+ "LEFT JOIN "
				  +"(SELECT M2.ID, COUNT(*) AS ORDER_COUNT "
					+" FROM Member M2 "
					+ "JOIN Orders O "
					+ "  ON M2.ID = O.MEMBER_ID "
					+"GROUP BY M2.ID) I"
					+ "  ON M.ID = I.ID";
			
			List<Object[]> resultList = em.createNativeQuery(sql, "memberJoinOrders")
				.getResultList();
			
			for (Object[] entities : resultList) {
				for (Object entity : entities) {
					System.out.println(entity);
				}
			}
		});
	}
	
	private static void findScalar() {
		logic(em -> {
			String sql =
					"SELECT ID, AGE, NAME, TEAM_ID "
							+ "FROM Member WHERE AGE > ?";
			List<Object[]> resultList = em.createNativeQuery(sql)
					.setParameter(1, 10)
					.getResultList();
			
			for (Object[] cols : resultList) {
				for (Object col : cols) {
					System.out.print(col + " ");
				}
				System.out.println();
			}
		});
	}
	private static void findEntity() {
		logic(em -> {
			String sql =
				"SELECT ID, AGE, NAME, TEAM_ID "
				+ "FROM Member WHERE AGE > ?";
			List<Member> resultList = em.createNativeQuery(sql, Member.class)
				.setParameter(1, 10)
				.getResultList();
			
			for (Member member : resultList) {
				System.out.println(member);
			}
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
