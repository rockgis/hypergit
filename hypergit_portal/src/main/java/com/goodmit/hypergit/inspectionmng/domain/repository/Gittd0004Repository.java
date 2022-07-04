package com.goodmit.hypergit.inspectionmng.domain.repository;

import com.goodmit.hypergit.inspectionmng.domain.entity.Gittd0004Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Gittd0004Repository extends JpaRepository<Gittd0004Entity, Long> {

    List<Gittd0004Entity> findByUsrEnContaining(String appNm);
}
