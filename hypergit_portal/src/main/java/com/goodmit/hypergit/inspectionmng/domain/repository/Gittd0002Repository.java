package com.goodmit.hypergit.inspectionmng.domain.repository;

import com.goodmit.hypergit.inspectionmng.domain.entity.Gittd0002Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Gittd0002Repository extends JpaRepository<Gittd0002Entity, Long> {

    List<Gittd0002Entity> findByUsrEnContaining(String appNm);
}
