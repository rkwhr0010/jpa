package study.jpa.entity1;

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
		find2();
		find3();
		find4();
		emf.close();
	}


	private static void find() {
		logic(em -> {
			Member member = em.find(Member.class, 1L);
			System.out.println("#########################################");
			System.out.println(member.getClass().getSuperclass());
			System.out.println(member.getClass());
			
			// Member 프록시를 반환
			Member reference = em.getReference(Member.class, 1L);
			// 첫 사용 한 번만 초기화 수행
			reference.getTeam();
			System.out.println("#########################################");
			System.out.println(reference.getClass().getSuperclass());
			System.out.println(reference.getClass());
		});
	}
	private static void find2() {
		logic(em -> {
			// 아무것도 수행 안함
			Member reference = em.getReference(Member.class, 1L);
			// 첫 사용 한 번만 초기화 수행
			reference.getTeam();
			System.out.println("#########################################");
			System.out.println(reference.getClass().getSuperclass());
			System.out.println(reference.getClass());
			
			Member member = em.find(Member.class, 1L);
			System.out.println("#########################################");
			System.out.println(member.getClass().getSuperclass());
			System.out.println(member.getClass());
		});
	}
	
	private static void find3() {
		logic(em -> {
			Member reference = em.getReference(Member.class, 1L);
			//준영속
			em.detach(reference);
			
			// 첫 사용 한 번만 초기화 수행
			reference.getTeam();
			System.out.println("#########################################");
			System.out.println(reference.getClass().getSuperclass());
			System.out.println(reference.getClass());
		});
	}
	
	private static void find4() {
		logic(em -> {
			Member reference = em.getReference(Member.class, 1L);
			System.out.println("#########################################");
			System.out.println(emf.getPersistenceUnitUtil().isLoaded(reference));
			// 첫 사용 한 번만 초기화 수행
			reference.getTeam();
			System.out.println("#########################################");
			System.out.println(emf.getPersistenceUnitUtil().isLoaded(reference));
			
			
		});
	}


	private static void save() {
		logic(em -> {
			
			Team team = new Team();
			team.setName("팀");
			em.persist(team);
			
			Member member = new Member();
			member.setName("멤버");
			member.setTeam(team);
			em.persist(member);
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
