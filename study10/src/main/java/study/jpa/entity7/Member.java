package study.jpa.entity7;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.StoredProcedureParameter;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"orders"})
@SqlResultSetMapping(name = "memberJoinOrders",
		entities = {@EntityResult(entityClass = Member.class)},
		columns = {@ColumnResult(name = "ORDER_COUNT")})
@NamedNativeQueries({
	@NamedNativeQuery(name = "Member.memberSQL",
			query = "SELECT ID, AGE, NAME, TEAM_ID "
					+ "FROM Member WHERE AGE > ?",
			resultClass = Member.class),
	@NamedNativeQuery(name = "Member.memberJoinOrders",
	query = "SELECT M.ID, M.AGE, M.NAME, M.TEAM_ID, I.ORDER_COUNT "
			+ "FROM Member M "
			+ "LEFT JOIN "
		  +"(SELECT M2.ID, COUNT(*) AS ORDER_COUNT "
			+" FROM Member M2 "
			+ "JOIN Orders O "
			+ "  ON M2.ID = O.MEMBER_ID "
			+"GROUP BY M2.ID) I"
			+ "  ON M.ID = I.ID",
			resultSetMapping = "memberJoinOrders")
})
@NamedStoredProcedureQuery(
	name = "multiply",
	procedureName = "proc_multiply",
	parameters = {
		@StoredProcedureParameter(name = "inParam", mode = ParameterMode.IN, type = Integer.class),
		@StoredProcedureParameter(name = "outParam", mode = ParameterMode.OUT, type = Integer.class),
	}
)
public class Member {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name")
	private String username;
	private int age;
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;
	
	public void setTeam(Team team) {
		if (this.team != null) {
			this.team.getMembers().remove(this);
		}
		this.team = team;
		team.getMembers().add(this);
	}
	
	public void addOrder(Order order) {
		orders.add(order);
		order.setMember(this);
	}
}
