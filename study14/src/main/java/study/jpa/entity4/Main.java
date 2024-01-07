package study.jpa.entity4;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Subgraph;
import study.jpa.entity.Product;
import study.jpa.entity.Team;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
//		entityGraph();
//		entityGraph2();
//		entityGraph3();
//		entityGraph4();
//		entityGraph5();
		entityGraph6();
		emf.close();
	}

	
	private static void entityGraph6() {
		logic(em->{
			EntityGraph<Order> graph = em.createEntityGraph(Order.class);
			graph.addAttributeNodes("member");
			Subgraph<Object> subgraph = graph.addSubgraph("orderItems");
			subgraph.addAttributeNodes("item");
			
			Map<String, Object> hints = new HashMap<>();
			hints.put("jakarta.persistence.fetchgraph", graph);
			
			Order order = em.find(Order.class, 1L, hints);
		});
	}
	
	private static void entityGraph5() {
		logic(em->{
			EntityGraph<Order> graph = em.createEntityGraph(Order.class);
			graph.addAttributeNodes("member");
			graph.addAttributeNodes("orderItems");
			
			Map<String, Object> hints = new HashMap<>();
			hints.put("jakarta.persistence.fetchgraph", graph);
			
			Order order = em.find(Order.class, 1L, hints);
		});
	}
	
	private static void entityGraph4() {
		logic(em->{
			em.createQuery("select o from Order o join fetch o.member where o.id = :id", Order.class)
			.setParameter("id", 1L)
			.setHint("jakarta.persistence.fetchgraph", em.getEntityGraph("Order.withAll"))
			.getResultList();
		});
	}
	
	private static void entityGraph3() {
		logic(em->{
			em.createQuery("select o from Order o where o.id = :id", Order.class)
				.setParameter("id", 1L)
				.setHint("jakarta.persistence.fetchgraph", em.getEntityGraph("Order.withMember"))
				.getResultList();
		});
	}
	
	private static void entityGraph2() {
		logic(em->{
			EntityGraph<?> graph = em.getEntityGraph("Order.withAll");
			HashMap<String, Object> hints = new HashMap<>();
			hints.put("jakarta.persistence.fetchgraph", graph);
			em.find(Order.class, Long.valueOf(1), hints);
			
		});
	}

	private static void entityGraph() {
		logic(em->{
			EntityGraph<?> graph = em.getEntityGraph("Order.withMember");
			HashMap<String, Object> hints = new HashMap<>();
			hints.put("jakarta.persistence.fetchgraph", graph);
			em.find(Order.class, Long.valueOf(1), hints);
			
		});
	}


	private static void save() {
		logic(em -> {
			
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
