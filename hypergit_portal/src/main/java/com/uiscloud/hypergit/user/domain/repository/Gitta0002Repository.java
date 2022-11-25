package com.uiscloud.hypergit.user.domain.repository;

import com.uiscloud.hypergit.user.domain.entity.Gitta0002Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Gitta0002Repository extends JpaRepository<Gitta0002Entity, Long> {

    List<Gitta0002Entity> findByUsrEnContaining(String keyword);
}
