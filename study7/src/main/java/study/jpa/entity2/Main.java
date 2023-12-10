package study.jpa.entity2;

import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		save();
		findAndUpdate();
		delete();
		emf.close();
	}

	private static void delete() {
		logic(em -> {
			Book book = em.find(Book.class, 1L);
			em.remove(book);
		});
	}

	private static void findAndUpdate() {
		logic(em -> {
			Book book = em.find(Book.class, 1L);
			book.setIsbn("2222222");
			book.setAuthor("현진건");
			book.setName("운수좋은날");
		});
	}

	private static void save() {
		logic(em -> {
			Book book = new Book();
			book.setAuthor("윤흥길");
			book.setIsbn("111111");
			book.setPrice(5000);
			book.setName("아홉켤레의구두로남은사내");
			
			em.persist(book);
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
