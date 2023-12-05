package study.jpa.entity7;

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
		find();
		emf.close();
	}
	

	private static void find() {
		logic(em -> {
			MemberProductId memberProductId = new MemberProductId();
			memberProductId.setMember(1L);
			memberProductId.setProduct(1L);
			
			MemberProduct memberProduct = em.find(MemberProduct.class, memberProductId);
			Member member = memberProduct.getMember();
			Product product = memberProduct.getProduct();
			
			System.out.println(member.getUsername());
			System.out.println(product.getName());
		});
	}

	private static void save() {
		logic(em -> {
			Member m1 = new Member();
			m1.setUsername("멤버1");
			em.persist(m1);
			
			Product p1 = new Product();
			p1.setName("상품1");
			em.persist(p1);
			
			MemberProduct memberProduct = new MemberProduct();
			memberProduct.setMember(m1);
			memberProduct.setProduct(p1);
			memberProduct.setOrderAmount(10);
			memberProduct.setOrderDate(LocalDateTime.now());
			em.persist(memberProduct);
			
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
