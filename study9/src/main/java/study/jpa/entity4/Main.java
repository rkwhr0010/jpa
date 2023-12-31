package study.jpa.entity4;

import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");

	public static void main(String[] args) {
		save();
		emf.close();
	}

	private static void save() {
		logic(em -> {
			Address homeAddress = new Address();
			homeAddress.setCity("서울시");
			homeAddress.setStreet("종로");
			homeAddress.setZipcode("12345");

			Member m1 = new Member();
			m1.setHomeAddress(homeAddress);
			m1.setName("홍길동");

			Member m2 = new Member();
//복사가 목적이였음!
			Address homeAddress2 = m1.getHomeAddress();
			homeAddress2.setCity("부산");
			m2.setHomeAddress(homeAddress2);
			m2.setName("임꺽정");

			em.persist(m1);
			em.persist(m2);
		});
	}

	private static void save2() {
		logic(em -> {
			Address homeAddress = new Address();
			homeAddress.setCity("서울시");
			homeAddress.setStreet("종로");
			homeAddress.setZipcode("12345");

			Member m1 = new Member();
			m1.setHomeAddress(homeAddress);
			m1.setName("홍길동");

			Member m2 = new Member();

			Address homeAddress2 = m1.getHomeAddress();
			homeAddress2.setCity("부산");
			m2.setHomeAddress(homeAddress2);
			m2.setName("임꺽정");

			em.persist(m1);
			em.persist(m2);
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
