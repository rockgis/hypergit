package com.goodmit.hypergit.sample.repository;

import com.goodmit.hypergit.repository.entity.Member;

public interface MemberRepoCustom {
    Member findByEmailOne(String email);
}
