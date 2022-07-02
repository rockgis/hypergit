package com.goodmit.hypergit.rollmn.domain.repository;

import com.goodmit.hypergit.rollmn.domain.entity.Gittf0001Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Gittf0001Repository extends JpaRepository<Gittf0001Entity, Long> {

    List<Gittf0001Entity> findByRoleNmContaining(String keyword);
}
