package study.jpa.entity2;

import java.util.List;
import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		save();
		find();
		find2();
		remove();
		remove2();
	}
	
	
	private static void remove2() {
		logic(em -> {
			Member m1 = em.find(Member.class, "member1");
			Member m2 = em.find(Member.class, "member2");
			
			m1.setTeam(null);
			m2.setTeam(null);
			
			// 팀을 제거하려면 기존 연관관계를 모두 끊어야 가능하다.
			em.remove(em.find(Team.class, "team1"));
		});
	}
	private static void remove() {
		logic(em -> {
			Member m1 = em.find(Member.class, "member1");
			m1.setTeam(null); // 연관관계 제거
			// em.persist 가 없어도, 더티체킹으로 감지되어 제거된다.(Update)
		});
	}


	private static void find2() {
		logic(em -> {
			String query = "select m from Member m join fetch m.team t "
					+ "where t.name = :teamName";
			TypedQuery<Member> q = em.createQuery(query, Member.class)
					.setParameter("teamName", "팀1");
			List<Member> resultList = q.getResultList();
			System.out.println(resultList);
			
		});
	}
	private static void find() {
		logic(em -> {
			Member m1 = em.find(Member.class, "member1");
			Team t1 = m1.getTeam(); // 객체 그래프 탐색
			System.out.println(m1);
			System.out.println(t1);
		});
	}


	private static void save() {
		logic(em -> {
			Team t1 = new Team("team1", "팀1");
			em.persist(t1);
			
			Member m1 = new Member("member1", "멤버1");
			m1.setTeam(t1);
			em.persist(m1);
			
			Member m2 = new Member("member2", "멤버2");
			m2.setTeam(t1);
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
