package study.jpa.practice;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		save();
	}

	private static void save() {
		logic(em -> {
			for (int i = 0; i < 5; i++) {
				Member member = new Member();
				member.setName("멤버이름" + i);
				member.setCity("서울시" + i);
				member.setStrret("종로" + i);
				member.setZipcode("" + i);
				
				em.persist(member);
				
				Order order = new Order();
				order.setStatus(OrderStatus.ORDER);
				order.setOrderDate(LocalDateTime.now());
				order.setMember(member);
				
				em.persist(order);
				
				Item item = new Item();
				item.setName("아이템" + i);
				item.setPrice(i);
				item.setStockQuantity(i);
				
				em.persist(item);
				
				
				OrderItem orderItem = new OrderItem();
				orderItem.setItem(item);
				orderItem.setOrder(order);
				orderItem.setCount(i);
				orderItem.setOrderPrice(i);
				
				em.persist(orderItem);
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
