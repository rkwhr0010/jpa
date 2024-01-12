package study.jpa.entity;

import java.util.List;
import java.util.function.Consumer;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.proxy.HibernateProxy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import study.jpa.entity.item.Album;
import study.jpa.entity.item.Book;
import study.jpa.entity.item.Item;
import study.jpa.entity.item.Movie;
import study.jpa.entity.item.VisitorImpl;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
//		프록시_비교1();
//		프록시_비교2();
//		프록시_타입_비교();
//		프록시_동등성_비교();
//		부모타입으로_프록시조회();
//		대상타입으로_직접조회();
//		프록시_벗기기();
//		별도_인터페이스_제공();
//		비지터_패턴_사용();
//		NPlus1_문제();
//		NPlus1_해결_페치조인();
//		Plus1_해결_batchSize();
//		읽기전용쿼리();
//		JPA등록배치();
//		JPA페이징배치();
//		JPA하이버네이트커서();
//		JPA하이버네이트무상태세션();
//		하이버네이트힌트사용();
		
//		for (int i = 0; i < 3; i++) {
//			저장시간측정1();
//			저장시간측정2();
//		}
		for (int i = 0; i < 3; i++) {
//			수정시간측정1();
//			수정시간측정2();
		}
		
		
		emf.close();
	}
	

	private static void 수정시간측정2() {
		long start = System.currentTimeMillis();
		
		logic(em -> {
			Session session = em.unwrap(Session.class);
			ScrollableResults<Member> scroll = 
					session.createQuery("select m from Member m", Member.class)
						.setCacheMode(CacheMode.IGNORE)
						.scroll(ScrollMode.FORWARD_ONLY);
			
			int count = 0;
			int end = 0;
			
			while (scroll.next()) {
				Member member = scroll.get();
				member.setUsername("비즈니스 로직 수행 중");
				
				count++;
				if (count % 1_000 == 0) {
					end++;
					session.flush();
					session.clear();
				}
				if (end == 9) {
					scroll.close();
				}
			}
		});
		
		long end = System.currentTimeMillis();
		System.out.println("###################################################");
		System.out.println("JPA 페이징 배치 : " + (end - start));
		System.out.println("###################################################");
	}
	
	private static void 수정시간측정1() {
		long start = System.currentTimeMillis();
		logic(em -> {
			
			int pageSize = 1_000;
			for (int i = 0; i < 10; i ++) {
				
				List<Member> resultList = 
						em.createQuery("select m from Member m", Member.class)
							.setFirstResult(pageSize * i)
							.setMaxResults(pageSize)
							.getResultList();
				
				for (Member m : resultList) {
					m.setUsername("비즈니스 로직 수행");
				}
				em.flush();
				em.clear();
			}
		});
		
		long end = System.currentTimeMillis();
		System.out.println("###################################################");
		System.out.println("JPA 페이징 배치 : " + (end - start));
		System.out.println("###################################################");
	}

	static int key = 0;
	
	private static void 저장시간측정2() {
		long start = System.currentTimeMillis();
		
		SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
		StatelessSession session = sessionFactory.openStatelessSession();
		Transaction tx = session.beginTransaction();
		
		for (int i = 0; i < 10_000; i++) {
			session.insert(new Member(key++, "이름"+i));
			
		}
		long end = System.currentTimeMillis();
		
		tx.commit();
		session.close();
		
		System.out.println("###################################################");
		System.out.println("영속성 컨텍스트 비활성화 : " + (end - start));
		System.out.println("###################################################");
	}
	
	private static void 저장시간측정1() {
		long start = System.currentTimeMillis();
		logic(em->{
			for (int i = 0; i < 10_000; i++) {
				em.persist(new Member(key++, "이름"+i));
				
				if (i % 1_000 == 0) {
					//메모리 부족 방지용 플러시
					em.flush();
					em.clear();
				}
			}
		});
		long end = System.currentTimeMillis();
		System.out.println("###################################################");
		System.out.println("영속성 컨텍스트 사용, 쓰기 지연 : " + (end - start));
		System.out.println("###################################################");
	}

	private static void 하이버네이트힌트사용() {
		logic(em -> {
			Session session = em.unwrap(Session.class);
			session.createQuery("select m from Member m", Member.class)
				.addQueryHint("FULL (Member)")
				.list();
			
		});
	}

	private static void JPA하이버네이트무상태세션() {
		SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
		StatelessSession session = sessionFactory.openStatelessSession();
		Transaction tx = session.beginTransaction();
		org.hibernate.query.Query<Member> query = 
				session.createQuery("select m from Member m", Member.class);
		ScrollableResults<Member> scroll = query.scroll();
		
		int end = 0;
		while (scroll.next()) {
			Member m = scroll.get();
			m.setUsername("비즈니스 로직 수행중");
			
			session.update(m); // 영속성 컨텍스트가 없어 직접 update 호출
			if (++end > 100) {
				//편의상 종료 조건
				break;
			}
		}
		tx.commit();
		scroll.close();
		session.close();
	}

	private static void JPA하이버네이트커서() {
		logic(em -> {
			Session session = em.unwrap(Session.class);
			ScrollableResults<Member> scroll = 
					session.createQuery("select m from Member m", Member.class)
				.setCacheMode(CacheMode.IGNORE) // 2차 캐시 기능 끄기
				.scroll(ScrollMode.FORWARD_ONLY);
			
			int count = 0;
			
			while (scroll.next()) {
				Member m = scroll.get();
				m.setUsername("비지니스로직...");
				count++;
				
				if (count % 100 == 0) {
					session.flush();
					session.clear();
				}
				if (count > 1_000) {
					//편의상 종료 조건...
					scroll.close();
					break;
				}
			}
		});
	}

	private static void JPA페이징배치() {
		logic(em -> {
			int pageSize = 100;
			for (int i = 0; i < 10; i++) {
				List<Member> resultList = em.createQuery("select m from Member m", Member.class)
					.setFirstResult(i * pageSize)
					.setMaxResults(pageSize)
					.getResultList();
				
				for (Member m : resultList) {
					m.setUsername("비즈니스로직수행중..." + i);
				}
				em.flush();
				em.clear();
			}
		});
	}

	private static void JPA등록배치() {
		logic(em -> {
			for (int i = 0; i < 100_000; i++) {
				em.persist(new Member(i, "이름"+i));
				
				if (i % 100 == 0) {
					em.flush();
					em.clear();
				}
			}
		});
	}

	private static void 읽기전용쿼리() {
		logic(em -> {
			for (int i = 0; i < 5; i++) {
				Member m = new Member(i, "회원" + i);
				em.persist(m);
			}
			em.flush();
			em.clear();
			
			Query query = em.createQuery("select m.id, m.username from Member m");
			List<Object[]> resultList = query.getResultList();
			
			for (Object[] columns : resultList) {
				for (Object col : columns) {
					System.out.print(col + " ");
				}
				System.out.println();
			}
			em.clear();
			
			TypedQuery<Member> typedQuery = em.createQuery("select m from Member m", Member.class);
			typedQuery.setHint("org.hibernate.readOnly", true);
			query.getResultList();
			
			//org.hibernate.Session
			Session session = em.unwrap(Session.class);
			session.setHibernateFlushMode(FlushMode.MANUAL);
			
		});
	}

	private static void Plus1_해결_batchSize() {
		logic(em -> {
			for (int i = 0; i < 2; i++) {
				Member m = new Member(i, "회원" + i);
				em.persist(m);
				for (int j = 0; j < 10; j++) {
					Order o = new Order();
					o.setMember(m);
					em.persist(o);
				}
			}
			
			em.flush();
			em.clear();
			
			TypedQuery<Member> query = 
					em.createQuery("select m from Member m", Member.class);
			List<Member> resultList = query.getResultList();
			for (Member m : resultList) {
				System.out.println(m);
				for (Order o : m.getOrders()) {
					System.out.println(o);
				}
			}
		});
	}
	
	
	private static void NPlus1_해결_페치조인() {
		logic(em -> {
			for (int i = 0; i < 5; i++) {
				Member m = new Member(i, "회원" + i);
				em.persist(m);
				Order o = new Order();
				o.setMember(m);
				em.persist(o);
			}
			
			em.flush();
			em.clear();
			
			System.out.println("##### JPQL fetch 조회 #####");
			TypedQuery<Member> query = 
					em.createQuery("select m from Member m join fetch m.orders", Member.class);
			List<Member> resultList = query.getResultList();
			for (Member m : resultList) {
				System.out.println(m);
				for (Order o : m.getOrders()) {
					System.out.println(o);
				}
			}
		});
	}
	
	private static void NPlus1_문제() {
		logic(em -> {
			for (int i = 0; i < 5; i++) {
				Member m = new Member(i, "회원" + i);
				em.persist(m);
				Order o = new Order();
				o.setMember(m);
				em.persist(o);
			}
			
			em.flush();
			em.clear();
			
			Member m = em.find(Member.class, "member0");
			m.getOrders().size();
			
			em.flush();
			em.clear();
			
			System.out.println("##### JPQL 조회 #####");
			TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
			for (Member member : query.getResultList()) {
				member.getOrders().size();// 강제 초기화
			}
		});
	}
	
	private static void 비지터_패턴_사용() {
		logic(em -> {
			Book book = new Book();
			Movie movie = new Movie();
			Album album = new Album();
			
			em.persist(book);
			em.persist(movie);
			em.persist(album);
			em.flush();
			em.clear();
			
			VisitorImpl visitorImpl = new VisitorImpl();
			em.getReference(Item.class, 1L).accept(visitorImpl);
			em.getReference(Item.class, 2L).accept(visitorImpl);
			em.getReference(Item.class, 3L).accept(visitorImpl);
		});
	}
	
	private static void 별도_인터페이스_제공() {
		logic(em -> {
			Book book = new Book();
			Movie movie = new Movie();
			Album album = new Album();
			
			em.persist(book);
			em.persist(movie);
			em.persist(album);
			em.flush();
			em.clear();
			
			System.out.println(em.getReference(Item.class, 1L).getDesciptionView());
			System.out.println(em.getReference(Item.class, 2L).getDesciptionView());
			System.out.println(em.getReference(Item.class, 3L).getDesciptionView());
		});
	}
	
	private static void 프록시_벗기기() {
		logic(em -> {
			// 자식 타입 저장
			Book book = new Book();
			book.setName("jpa책");
			
			em.persist(book);
			em.flush();
			em.clear();
			
			// 부모 타입 프록시 조회
			Item proxy = em.getReference(Item.class, 1L);
			Item item = unProxy(proxy);
			
			System.out.println("Item + " + item.getClass());
			System.out.println("itemProxy instanceof Book = " + (item instanceof Book));
			System.out.println("itemProxy instanceof Item = " + (item instanceof Item));
			
			System.out.println("proxy == item  : " + (proxy == item));
			item.setPrice(10000);
		});
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T unProxy(Object proxy) {
		if (proxy instanceof HibernateProxy) {
			return (T)((HibernateProxy)proxy).getHibernateLazyInitializer()
					.getImplementation();
		}
		throw new IllegalArgumentException();
	}
	
	
	private static void 대상타입으로_직접조회() {
		logic(em -> {
			// 자식 타입 저장
			Book book = new Book();
			book.setName("jpa책");
			
			book = em.createQuery("select b from Book b where b.id = :bookId", Book.class)
				.setParameter("bookId", 1L)
				.getSingleResult();
		});
	}
	
	private static void 부모타입으로_프록시조회() {
		logic(em -> {
			// 자식 타입 저장
			Book book = new Book();
			book.setName("jpa책");
			
			em.persist(book);
			em.flush();
			em.clear();
			
			// 부모 타입 프록시 조회
			Item item = em.getReference(Item.class, 1L);
			// 부모 타입 조회
//			Item item = em.find(Item.class, 1L);
			
			System.out.println("Item + " + item.getClass());
			System.out.println("itemProxy instanceof Book = " + (item instanceof Book));
			System.out.println("itemProxy instanceof Item = " + (item instanceof Item));
		});
	}
	
	private static void 프록시_동등성_비교() {
		logic(em -> {
			Member member = new Member(1, "회원1");
			em.persist(member);
			em.flush();
			em.clear();
			
			Member m1 = new Member(1, "회원1");
			Member m2 = em.getReference(Member.class, 1);
			System.out.println("equals() 비교 : " + m1.equals(m2));
		});
	}

	private static void 프록시_타입_비교() {
		logic(em -> {
			Member member = new Member(1, "회원1");
			em.persist(member);
			em.flush();
			em.clear();
			
			Member m1 = em.getReference(Member.class, 1);
			
			System.out.println("ref = " + m1.getClass());
			System.out.println("== 비교 : " + (Member.class == m1.getClass()));
			System.out.println("instanceof 비교 : " + (m1 instanceof Member));
		});
	}
	
	private static void 프록시_비교2() {
		logic(em -> {
			Member member = new Member(1, "회원1");
			em.persist(member);
			em.flush();
			em.clear();
			
			Member m2 = em.find(Member.class, 1);
			Member m1 = em.getReference(Member.class, 1);
			
			System.out.println("ref = " + m1.getClass());
			System.out.println("find = " + m2.getClass());
			System.out.println(m1 == m2);
		});
	}
	
	private static void 프록시_비교1() {
		logic(em -> {
			Member member = new Member(1, "회원1");
			em.persist(member);
			em.flush();
			em.clear();
			
			Member m2 = em.find(Member.class, 1);
			Member m1 = em.getReference(Member.class, 1);
			
			System.out.println("find = " + m2.getClass());
			System.out.println("ref = " + m1.getClass());
			System.out.println(m1 == m2);
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
