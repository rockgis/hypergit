package com.goodmit.hypergit.user.domain.repository;

import com.goodmit.hypergit.user.domain.entity.Gitta0001Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Gitta0001Repository extends JpaRepository<Gitta0001Entity, Long> {

    List<Gitta0001Entity> findByUsrEnContaining(String keyword);
}
