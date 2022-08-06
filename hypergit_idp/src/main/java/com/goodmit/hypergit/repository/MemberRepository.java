package com.goodmit.hypergit.repository;

import com.goodmit.hypergit.global.config.AuthDBConfig;
import com.goodmit.hypergit.repository.entity.Member;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnBean(value = AuthDBConfig.class)
public interface MemberRepository extends CrudRepository<Member,Long> ,MemberQRepo{
}
