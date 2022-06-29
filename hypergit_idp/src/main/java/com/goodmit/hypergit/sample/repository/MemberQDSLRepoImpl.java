package com.goodmit.hypergit.sample.repository;

import com.goodmit.hypergit.sample.repository.entity.Member;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import static com.goodmit.hypergit.sample.repository.entity.QMember.member;

public class MemberQDSLRepoImpl extends QuerydslRepositorySupport implements MemberQDSLRepo {
    protected MemberQDSLRepoImpl() {
        super(Member.class);
    }

    @Override
    public Member searchMemberByEmail(String email) {
        JPQLQuery<Member> query = from(member);
        return query
                .select(member)
                .where(member.email.eq(email))
                .fetchOne();
    }
}
