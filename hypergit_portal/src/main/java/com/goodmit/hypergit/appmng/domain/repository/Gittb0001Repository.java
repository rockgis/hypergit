package com.goodmit.hypergit.appmng.domain.repository;

import com.goodmit.hypergit.appmng.domain.entity.Gittb0001Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Gittb0001Repository extends JpaRepository<Gittb0001Entity, Long> {

    List<Gittb0001Entity> findByAppNmContaining(String appNm);
}
