package study.jpa.entity1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.text.TabableView;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;

import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		save();
//		findJpql();
//		findCriteria();
//		findDsl();
//		findSql();
		findJdbc();
		emf.close();
	}

	private static void findJpql() {
		logic(em -> {
			String jpql = "select m from Member m where m.name = '홍길동2'";
			Query query = em.createQuery(jpql);
			query.getResultList();
		});
	}
	
	private static void findCriteria() {
		logic(em -> {
			// 빌더 생성
			CriteriaBuilder cb = em.getCriteriaBuilder();
			// 쿼리 생성
			CriteriaQuery<Member> query = cb.createQuery(Member.class);
			// from 절 _ 드라이빙 테이블
			Root<Member> root = query.from(Member.class);
			
			query.select(root)
				.where(cb.equal(root.get("name"), "홍길동1"));
			
			List<Member> resultList = em.createQuery(query).getResultList();
		});
	}
	
	private static void findDsl() {
		logic(em -> {
//			QMember member = QMember.member;
//			JPAQuery<Member> query = new JPAQuery<>(em);
//			Member m = query.from(member)
//				.where(member.name.eq("홍길동1"))
//				.fetchOne();
//			System.out.println(m);
		});
	}
	
	private static void findSql() {
		logic(em -> {
			String sql = "select a.* from Member a where a.username = '홍길동1'";
			
			Query query = em.createNativeQuery(sql);
			System.out.println(query.getResultList());
			
		});
	}
	
	private static void findJdbc() {
		logic(em -> {
			String sql = "select a.* from Member a where a.username = '홍길동1'";
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:55555/jpa?characterEncoding=utf8", 
						"root", 
						"root");
				Statement statement = connection.createStatement();
				em.flush();
				ResultSet resultSet = statement.executeQuery(sql);
				
				while (resultSet.next()) {
					System.out.println(resultSet.getString("username"));
				}
			} catch (Exception e) {}
			

			Session session = em.unwrap(Session.class);
			
			session.doWork(con -> {
				Statement statement = con.createStatement();
				em.flush();
				ResultSet resultSet = statement.executeQuery(sql);
				
				while (resultSet.next()) {
					System.out.println(resultSet.getString("username"));
				}
			});
		});
	}

	private static void save() {
		logic(em -> {
			for (int i = 0; i < 10; i++) {
				Member member = new Member();
				member.setName("홍길동" + i);
				em.persist(member);
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
