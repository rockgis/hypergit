package com.goodmit.hypergit.sample.repository;

import com.goodmit.hypergit.sample.repository.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles="local")
@Slf4j
class MemberRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    MemberDSLRepository memberDSLRepository;

    @Test
    public void testMember()  {
        Iterable<Member> tt = memberRepository.findAll();
        Member member = new Member("admin","");
        Member findMember = memberRepository.findByEmail(member);
//        findMember.forEach(member -> log.info("{}",member.getId()));

        log.info("{}",findMember.getEmail());
    }

    @Test
    public void testQDSLMember() {
        Member member = memberDSLRepository.findByEmail("admin");
        log.info("{}\t{}",member.getEmail(),member.getPassword());
    }

    @Test
    public void testJPACustomRepo() {
        Member member = memberRepository.findByEmailOne("admin");
        log.info("{}\t{}",member.getId(),member.getEmail());
    }

    @Test
    public void testJPAQDSLCustomRepo() {
        Member member = memberRepository.searchMemberByEmail("admin");
        log.info("{}\t{}",member.getId(),member.getEmail());
    }


}