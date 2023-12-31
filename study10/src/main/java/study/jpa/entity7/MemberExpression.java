package study.jpa.entity7;

import com.querydsl.core.annotations.QueryDelegate;
import com.querydsl.core.types.dsl.BooleanExpression;

public class MemberExpression {
	@QueryDelegate(Member.class)
	public static BooleanExpression isYoung(QMember member) {
		return member.age.loe(30);
	}
}
