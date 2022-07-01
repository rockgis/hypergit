package com.goodmit.hypergit.repository;

import com.goodmit.hypergit.repository.entity.Member;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

import static com.goodmit.hypergit.repository.entity.QMember.member;

public class MemberQRepoImpl extends QuerydslRepositorySupport implements MemberQRepo{
    public MemberQRepoImpl() {
        super(Member.class);
    }

    @Override
    public Optional<Member> findByName(String name ) {
        return Optional.ofNullable(
                from(member)
                        .select(member)
                        .where(member.email.eq(name))
                        .fetchOne()
        );
    }
}
