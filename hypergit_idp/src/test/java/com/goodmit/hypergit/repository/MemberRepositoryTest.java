package com.goodmit.hypergit.repository;

import com.goodmit.hypergit.repository.entity.Member;
import com.goodmit.hypergit.repository.entity.Role;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles="dev")
@Slf4j
class MemberRepositoryTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    public void testQuerDSLMember()  {
        Iterable<Member> tt = memberRepository.findAll();
        Member findMember = memberRepository.findByName("admin").get();
        Assert.assertNotNull(findMember);
        Assert.assertTrue(passwordEncoder.matches("admin",findMember.getPassword()));
        log.info("{}",findMember.getEmail());
    }

    @Test
    public void testMember() {
        Iterable<Member> member = memberRepository.findAll();
        member.forEach(m -> log.info("{}\t{}",m.getId(),m.getEmail()) );

    }

    @Test
    public void testRoleName() {
        String shortName = Role.ROLE_ADMIN.getShortName();;
        MatcherAssert.assertThat(shortName, Matchers.not(Matchers.startsWith("_")));
    }


}