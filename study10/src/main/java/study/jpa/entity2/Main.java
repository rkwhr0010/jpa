package study.jpa.entity2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		save();
//		findTypeQuery();
//		findType();
//		findNamedParameters();
//		findPositionalParameters();
//		findEmbedded();
//		findScala();
//		findDto();
//		findPaging();
//		findGroupBy();
//		findOrderBy();
//		findInnerJoin();
//		findOuterJoin();
//		findCollectionJoin();
//		findThetaJoin();
//		findJoinOn();
//		findFetchJoin();
//		findCollectionFetchJoin();
		findFetchJoinDistinct();
		
		emf.close();
	}
	
	private static void findFetchJoinDistinct() {
		logic(em -> {
			String sql = "select distinct t from Team t join fetch t.members";
			TypedQuery<Team> typedQuery = em.createQuery(sql, Team.class);
			typedQuery.getResultList().forEach(t -> {
				System.out.println(System.identityHashCode(t));
				t.getMembers().forEach(m -> {
					System.out.println(System.identityHashCode(m.getTeam()));
				});
				System.out.println();
			});
		});
	}
	
	private static void findCollectionFetchJoin() {
		logic(em -> {
			String sql = "select t from Team t join fetch t.members";
			TypedQuery<Team> typedQuery = em.createQuery(sql, Team.class);
			typedQuery.getResultList().forEach(t -> {
				System.out.println(System.identityHashCode(t));
				t.getMembers().forEach(m -> {
					System.out.println(System.identityHashCode(m.getTeam()));
				});
				System.out.println();
			});
		});
	}
	
	private static void findFetchJoin() {
		logic(em -> {
			String sql = "select m from Member m left join fetch m.team t";
			TypedQuery<Member> typedQuery = em.createQuery(sql, Member.class);
			typedQuery.getResultList().forEach(System.out::println);
		});
	}
	
	private static void findJoinOn() {
		logic(em -> {
			String sql = "select m from Member m left join m.team t on m.username = '임꺽정'";
			TypedQuery<Member> typedQuery = em.createQuery(sql, Member.class);
			System.out.println(typedQuery.getResultList());
		});
	}
	
	private static void findThetaJoin() {
		logic(em -> {
			String sql = "select m from Member m, Team t where m.id = t.id";
			TypedQuery<Member> typedQuery = em.createQuery(sql, Member.class);
			System.out.println(typedQuery.getResultList());
		});
	}
	
	private static void findCollectionJoin() {
		logic(em -> {
			String sql = "select t from Team t left join t.members m";
			TypedQuery<Team> typedQuery = em.createQuery(sql, Team.class);
			System.out.println(typedQuery.getResultList());
		});
	}
	
	private static void findOuterJoin() {
		logic(em -> {
			String sql = "select m from Member m left join m.team t where t.name = :teamname";
			TypedQuery<Member> typedQuery = em.createQuery(sql, Member.class);
			typedQuery.setParameter("teamname", "블루팀");
			System.out.println(typedQuery.getResultList());
		});
	}
	
	private static void findInnerJoin() {
		logic(em -> {
			String sql = "select m from Member m inner join m.team t where t.name = :teamname";
			TypedQuery<Member> typedQuery = em.createQuery(sql, Member.class);
			typedQuery.setParameter("teamname", "블루팀");
			System.out.println(typedQuery.getResultList());
		});
	}
	
	private static void findOrderBy() {
		logic(em -> {
			String sql = "select m from Member m order by m.id";
			TypedQuery<Member> typedQuery = em.createQuery(sql, Member.class);
			typedQuery.getResultList();
		});
	}
	
	private static void findGroupBy() {
		logic(em -> {
			String sql = "select t.name, count(m.age), avg(m.age), max(m.age), min(m.age)"
					+ " from Member m left join m.team t"
					+ " group by t.name"
					+ " having avg(m.age) >= 10";
			Query query = em.createQuery(sql);
			List<Object[]> resultList = query.getResultList();
			for (Iterator<Object[]> it = resultList.iterator(); it.hasNext();) {
				System.out.println(Arrays.toString(it.next()));
			}
		});
	}

	private static void findPaging() {
		logic(em -> {
			TypedQuery<Order> typedQuery = em.createQuery("select o from Order o", Order.class);
			
			int totalCount = typedQuery.getResultList().size();
			Double rowsPerPage = 2.0;
			Double pages = Math.ceil(totalCount / rowsPerPage);
			
			for (int page = 0; page < pages; page++) {
				typedQuery.setFirstResult(0 + page * rowsPerPage.intValue()); // 인덱스
				typedQuery.setMaxResults(rowsPerPage.intValue());  // 총 개수
				typedQuery.getResultList().forEach(System.out::println);
			}
		});
	}

	private static void findDto() {
		logic(em -> {
			String sql = "select m.username, m.age from Member m";
			Query query = em.createQuery(sql);
			@SuppressWarnings("unchecked")
			List<Object[]> resultList = query.getResultList();
			for (Object[] row : resultList) {
				Dto dto = new Dto(row[0].toString(), Integer.parseInt(row[1].toString()));
				System.out.println(dto);
			}
			
			String sqlWithDto = "select new study.jpa.entity2.Dto(m.username, m.age) from Member m";
			TypedQuery<Dto> typedQuery = em.createQuery(sqlWithDto, Dto.class);
			typedQuery.getResultList().forEach(System.out::println);
		});
	}

	private static void findScala() {
		logic(em -> {
			String sql = "select o.address from Order o";
			TypedQuery<Address> typedQuery = em.createQuery(sql, Address.class);
			typedQuery.getResultList().forEach(System.out::println);
		});
		
	}

	private static void findEmbedded() {
		
		logic(em -> {
			String sql = "select o.address from Order o";
			TypedQuery<Address> typedQuery = em.createQuery(sql, Address.class);
			typedQuery.getResultList().forEach(System.out::println);
		});
	}

	private static void findPositionalParameters() {
		logic(em -> {
			String sql = "select m from Member m where m.username = ?1";
			TypedQuery<Member> typedQuery = em.createQuery(sql, Member.class);
			typedQuery.setParameter(1, "임꺽정")
			.getResultList();
		});
	}

	private static void findNamedParameters() {
		logic(em -> {
			String sql = "select m from Member m where m.username = :name";
			TypedQuery<Member> typedQuery = em.createQuery(sql, Member.class);
			typedQuery.setParameter("name", "임꺽정")
				.getResultList();
		});
	}

	private static void findTypeQuery() {
		logic(em -> {
			String sql =  "select m from Member m";
			String sql2 = "select m.team from Member m";
			TypedQuery<Member> typedQuery = em.createQuery(sql, Member.class);
			
			typedQuery.getResultStream().forEach(System.out::println);
		});
	}
	
	private static void findType() {
		logic(em -> {
			String sql = "select m.username, m.age from Member m";
			Query query = em.createQuery(sql);
			@SuppressWarnings("unchecked")
			List<Object[]> resultList = query.getResultList();
			for (Object[] row : resultList) {
				System.out.println(Arrays.toString(row));
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
