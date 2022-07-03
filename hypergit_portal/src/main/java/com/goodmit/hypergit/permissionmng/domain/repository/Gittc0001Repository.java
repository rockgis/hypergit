package com.goodmit.hypergit.permissionmng.domain.repository;

import com.goodmit.hypergit.permissionmng.domain.entity.Gittc0001Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Gittc0001Repository extends JpaRepository<Gittc0001Entity, Long> {

    List<Gittc0001Entity> findByAppNmContaining(String appNm);
}
