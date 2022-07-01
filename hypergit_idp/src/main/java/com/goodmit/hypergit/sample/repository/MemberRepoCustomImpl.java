package com.goodmit.hypergit.sample.repository;

import com.goodmit.hypergit.repository.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class MemberRepoCustomImpl implements MemberRepoCustom{

    @Autowired
    private EntityManager entityManager;

    @Override
    public Member findByEmailOne(String email) {
        return entityManager
                .createQuery("select m from Member as m where m.email = :email",Member.class)
                .setParameter("email",email)
                .getSingleResult();
    }
}
