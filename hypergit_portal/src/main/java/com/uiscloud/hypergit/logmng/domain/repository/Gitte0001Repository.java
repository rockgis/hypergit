package com.uiscloud.hypergit.logmng.domain.repository;

import com.uiscloud.hypergit.logmng.domain.entity.Gitte0001Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Gitte0001Repository extends JpaRepository<Gitte0001Entity, Long> {

    List<Gitte0001Entity> findByUsrNmContaining(String keyword);
}
