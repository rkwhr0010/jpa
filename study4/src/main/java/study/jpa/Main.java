package study.jpa;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import study.jpa.entity1.Board;
import study.jpa.entity1.Member;

public class Main {
	static Logger logger = LogManager.getLogger();
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");

	public static void main(String[] args) {
		save();
//		find();
//		update();
//		delete();
	}

	private static void find() {
		createPersistenceContext(em -> {
			
		});
	}

	private static void createPersistenceContext(Consumer<EntityManager> consumer) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			consumer.accept(em);
			logger.info("\n\n\n커밋\n\n\n");
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
	}

	private static void save() {
		createPersistenceContext(em -> {

		});
	}
}
