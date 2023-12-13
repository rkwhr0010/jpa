package study.jpa.practice;

import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		logic(em -> {
Delivery delivery = new Delivery();
OrderItem orderItem1 = new OrderItem();
OrderItem orderItem2 = new OrderItem();

Order order = new Order();
order.setDelivery(delivery);;
order.addOrderItem(orderItem1);
order.addOrderItem(orderItem2);

em.persist(order);
		});
		emf.close();
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
