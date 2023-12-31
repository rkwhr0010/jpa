package study.jpa.entity6;

import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");

	public static void main(String[] args) {
		save();
		update();
		emf.close();
	}

	private static void save() {
		logic(em -> {
			
			Member member = new Member();
			member.getFavoriteFoods().add("탕수육");
			member.getFavoriteFoods().add("치킨");
			member.getFavoriteFoods().add("탕수육");
			member.getFavoriteFoods().add("치킨");
			member.getFavoriteFoods().add("탕수육");
			member.getFavoriteFoods().add("치킨");
			
			member.getAddressHistory().add(new AddressEntity("서울시", "종로", "12345"));
			member.getAddressHistory().add(new AddressEntity("서울시", "을지로", "12345"));
			member.getAddressHistory().add(new AddressEntity("서울시", "종로", "12345"));
			member.getAddressHistory().add(new AddressEntity("서울시", "을지로", "12345"));
			member.getAddressHistory().add(new AddressEntity("서울시", "종로", "12345"));
			member.getAddressHistory().add(new AddressEntity("서울시", "을지로", "12345"));
			
			em.persist(member);
		});
	}
	private static void update() {
		logic(em -> {
			
			Member member = em.find(Member.class, 1L);
			
			// String은 불변 객체로 수정이 불가, 제거 후 새로 저장
			member.getFavoriteFoods().remove("치킨");
			member.getFavoriteFoods().add("양념치킨");
			
			// Address도 불변 객체로 설계함, setter 불가 생성자로만 값 설정 가능
			member.getAddressHistory().remove(new AddressEntity(1L, "서울시", "종로", "12345"));
			member.getAddressHistory().add(new AddressEntity("서울시", "종로3가", "12345"));

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
