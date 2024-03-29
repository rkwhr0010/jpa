package study.jpa.entity7;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMember extends BeanPath<Member> {

    private static final long serialVersionUID = 1060220908L;

    public static final QMember member = new QMember("member1");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

    public BooleanExpression isYoung() {
        return MemberExpression.isYoung(this);
    }

}

