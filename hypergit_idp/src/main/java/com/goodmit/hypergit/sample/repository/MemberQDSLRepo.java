package com.goodmit.hypergit.sample.repository;

import com.goodmit.hypergit.sample.repository.entity.Member;

public interface MemberQDSLRepo {
    Member searchMemberByEmail(String email);
}
