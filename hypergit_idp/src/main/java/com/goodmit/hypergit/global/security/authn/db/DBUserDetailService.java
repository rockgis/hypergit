package com.goodmit.hypergit.global.security.authn.db;

import com.goodmit.hypergit.repository.MemberRepository;
import com.goodmit.hypergit.repository.entity.Member;
import com.goodmit.hypergit.repository.entity.Role;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

public class DBUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Builder
    protected DBUserDetailService(@NonNull MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(username)
                .orElseThrow(()->new UsernameNotFoundException("could not find user"));

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(Role.ROLE_ADMIN.getShortName())
                .build();
    }
}
