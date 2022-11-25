package com.uiscloud.hypergit.permissionmng.domain.repository;

import com.uiscloud.hypergit.permissionmng.domain.entity.Gittc0001Entity;
import com.uiscloud.hypergit.permissionmng.dto.Gittc0001Dto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Gittc0001Repository extends JpaRepository<Gittc0001Entity, Long> {

    List<Gittc0001Entity> findByAppNmContaining(String appNm);
    List<Gittc0001Entity> findByUsrEn(String usrEn);
}
