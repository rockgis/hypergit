package com.uiscloud.hypergit.inspectionmng.domain.repository;

import com.uiscloud.hypergit.inspectionmng.domain.entity.Gittd0003Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Gittd0003Repository extends JpaRepository<Gittd0003Entity, Long> {

    List<Gittd0003Entity> findByUsrEnContaining(String appNm);
}
