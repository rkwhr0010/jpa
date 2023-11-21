package study.jpa;

import java.time.LocalDateTime;

import org.apache.logging.log4j.*;

import jakarta.persistence.*;
import study.jpa.entity.User;

public class Main {
	static Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			logger.info("#################로그 테스트#################");
			User user = new User();
			user.setName("홍길동");
			user.setRegistDate(LocalDateTime.now());
			em.persist(user);
			
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();
	}
}
