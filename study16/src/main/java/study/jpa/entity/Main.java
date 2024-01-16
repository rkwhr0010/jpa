package study.jpa.entity;

import static jakarta.persistence.LockModeType.OPTIMISTIC;
import static jakarta.persistence.LockModeType.OPTIMISTIC_FORCE_INCREMENT;
import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) throws Exception {
		저장();
		
		ExecutorService pool = Executors.newCachedThreadPool();
//		pool.execute(Main::트랜잭션A);
//		pool.execute(Main::트랜잭션B);
//		락();
//		pool.execute(Main::트랜잭션OptimisticA);
//		pool.execute(Main::트랜잭션OptimisticB);
//		OptimisticForceIncrement();
//		pool.execute(Main::트랜잭션PessimisticForceIncrementA);
//		pool.execute(Main::트랜잭션PessimisticForceIncrementB);
//		캐시모드설정();
		쿼리캐시와컬렉션캐시의주의점();
		
		Thread.sleep(3000);
		emf.close();
	}
	
	
	private static void 쿼리캐시와컬렉션캐시의주의점() {
		logic(em -> {
			// 쿼리 캐시가 적용되어 있어, 이 결과 셋이 캐시된다. 단, 식별자만 캐시된다.
			List<Board> resultList = em.createQuery("select b from Board b", Board.class)
					.setHint("org.hibernate.cacheable",true)
					.getResultList();
			
			
			
		});
	}


	private static void 캐시모드설정() {
		logic(em -> {
			// 엔티티 매니저 범위 설정
			em.setProperty("jakarta.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
			em.setProperty("jakarta.persistence.cache.storeMode", CacheStoreMode.BYPASS);
			
			Map<String, Object> properties = new HashMap<>();
			properties.put("jakarta.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
			properties.put("jakarta.persistence.cache.storeMode", CacheStoreMode.BYPASS);
			
			// 질의 단위
			Board board = em.find(Board.class, "0", properties);
			
			// 엔티티 단위
			em.refresh(board, properties);
			
			// JPQL
			em.createQuery("select b from Board b", Board.class)
				.setHint("jakarta.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
				.setHint("jakarta.persistence.cache.storeMode", CacheStoreMode.BYPASS)
				.getResultList();
			
			emf.getCache();
			
			// 쿼리 캐시
			em.createQuery("select b from Board b", Board.class)
				.setHint("org.hibernate.cacheable", true)
				.getResultList();
			
		});
	}


	private static void 트랜잭션PessimisticForceIncrementA() {
		logic(em -> {
			em.setProperty("", "");
			Map<String,Object> map = new HashMap<>();
			map.put("jakarta.persistence.lock.timeout", 1500);
			Board board = em.find(Board.class, "0", PESSIMISTIC_WRITE, map);
			board.setTitle("트랜잭션A");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
		});
	}
	
	private static void 트랜잭션PessimisticForceIncrementB() {
		logic(em -> {
			Map<String,Object> map = new HashMap<>();
			map.put("jakarta.persistence.lock.timeout", 1000);
			Board board = em.find(Board.class, "0", PESSIMISTIC_WRITE, map);
			board.setTitle("트랜잭션B");
		});
	}
	
	private static void OptimisticForceIncrement() {
		logic(em -> {
			Board board = em.find(Board.class, "0",OPTIMISTIC_FORCE_INCREMENT);
			board.getComments().get(0).setComment("바꾸기!");
			board.setTitle("바꾸기@@");
		});
	}


	private static void 트랜잭션OptimisticA() {
		logic(em -> {
			Board board = em.find(Board.class, "0", OPTIMISTIC);
			board.setTitle("트랜잭션A");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		});
	}
	private static void 트랜잭션OptimisticB() {
		logic(em -> {
			Board board = em.find(Board.class, "0", OPTIMISTIC);
			board.setTitle("트랜잭션B");
		});
	}

	private static void 락() {
		logic(em -> {
			//1 조회 즉시, 애플리케이션이 제공하는 낙관적 락 걸기
			em.find(Board.class, "0", OPTIMISTIC);
			
			//2 조회 후 원하는 시점에 애플리케이션이 제공하는 낙관적 락 걸기
			Board board = em.find(Board.class, "0");
			//원하는 시점....
			em.lock(board, OPTIMISTIC);
		});
	}

	private static void 트랜잭션A() {
		logic(em -> {
			Board board = em.find(Board.class, "0");
			board.setTitle("트랜잭션A");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		});
	}
	private static void 트랜잭션B() {
		logic(em -> {
			Board board = em.find(Board.class, "0");
			board.setTitle("트랜잭션B");
		});
	}

	private static void 저장() {
		logic(em -> {
			for (int i = 0; i < 5; i++) {
				Board board = new Board();
				board.setId(i + "");
				board.setTitle("게시물" + i);
				em.persist(board);
				Comment comment = new Comment();
				comment.setComment("댓글" + i);
				board.getComments().add(comment);
				em.persist(comment);
			}
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
