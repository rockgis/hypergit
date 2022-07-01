package com.goodmit.hypergit.repository;

import com.goodmit.hypergit.repository.entity.Member;

import java.util.Optional;

public interface MemberQRepo {
    Optional<Member> findByName(String name);
}
