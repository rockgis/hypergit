package com.goodmit.hypergit.repository;

import com.goodmit.hypergit.repository.entity.LoginAudit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginAuditRepo extends CrudRepository<LoginAudit,Long> {
}
