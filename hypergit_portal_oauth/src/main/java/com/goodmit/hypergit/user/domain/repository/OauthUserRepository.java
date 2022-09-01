package com.goodmit.hypergit.user.domain.repository;

import com.goodmit.hypergit.user.domain.entity.OauthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OauthUserRepository extends JpaRepository<OauthUser, Long> {

    Optional<OauthUser> findByEmail(String email);
}
