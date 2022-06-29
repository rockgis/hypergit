package com.goodmit.hypergit.sample.repository;

import com.goodmit.hypergit.sample.repository.entity.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member,Long> ,MemberRepoCustom ,MemberQDSLRepo{
    @Query(value = "select m from Member as m where m.email = :#{#member.email}")
    Member findByEmail(@Param("member") Member member);
}
