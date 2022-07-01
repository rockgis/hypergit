package com.goodmit.hypergit.sample.repository;

import com.goodmit.hypergit.repository.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.goodmit.hypergit.repository.entity.QMember;

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
