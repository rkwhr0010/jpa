package study.jpa.entity7;

import java.io.Serializable;

import lombok.Data;

@Data
public class MemberProductId implements Serializable{
	//IDE 자동 생성 기능
	private static final long serialVersionUID = 938018879231292890L;
	private Long member;
	private Long product;
}
