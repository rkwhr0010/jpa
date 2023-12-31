package study.jpa.entity6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

//@Entity
@Data
public class Member {
	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String name;
	
	@Embedded
	private Address homeAddress;
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "city", column = @Column(name = "company_city")),
		@AttributeOverride(name = "street", column = @Column(name = "company_street")),
		@AttributeOverride(name = "zipcode", column = @Column(name = "company_zipcode")),
	})
	private Address companyAddress;
	
	@ElementCollection
	@CollectionTable(name = "favorite_foods",
			joinColumns = @JoinColumn(name = "member_id"))
	@Column(name = "food_name")
	private Set<String> favoriteFoods = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	// 외래키는 AddressEntity 테이블에 있음, 그 외래키 정보임
	@JoinColumn(name = "member_id") 
	private List<AddressEntity> addressHistory = new ArrayList<>();
}