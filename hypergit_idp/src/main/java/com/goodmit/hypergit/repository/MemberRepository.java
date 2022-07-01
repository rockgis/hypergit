package com.goodmit.hypergit.repository;

import com.goodmit.hypergit.repository.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member,Long> ,MemberQRepo{
}
