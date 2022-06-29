package com.goodmit.hypergit.sample.repository;

import com.goodmit.hypergit.sample.repository.entity.Member;
import com.goodmit.hypergit.sample.repository.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


public class MemberDSLRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberDSLRepository(JPAQueryFactory jpaQueryFactory) {
        super(Member.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Member findByEmail(String email) {
        return jpaQueryFactory.selectFrom(QMember.member)
                .where(QMember.member.email.eq(email))
                .fetchOne();
    }

}
