
package com.goodmit.hypergit.policymng.service;

import com.goodmit.hypergit.policymng.domain.entity.Gittf0003Entity;
import com.goodmit.hypergit.policymng.domain.repository.Gittf0003Repository;
import com.goodmit.hypergit.policymng.dto.Gittf0003Dto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class Gittf0003Service {

    private Gittf0003Repository gittf0003Repository;


    @Transactional
    public Gittf0003Dto getPost(Long id) {
        Optional<Gittf0003Entity> gittf0003EntityWrapper = gittf0003Repository.findById(id);
        Gittf0003Entity gittf0003Entity = gittf0003EntityWrapper.get();

        return this.convertEntityToDto(gittf0003Entity);
    }

    @Transactional
    public Long getgittf0003Count() {
        return gittf0003Repository.count();
    }

    @Transactional
    public Long savePost(Gittf0003Dto gittf0003Dto) {
        return gittf0003Repository.save(gittf0003Dto.toEntity()).getId();
    }

    private Gittf0003Dto convertEntityToDto(Gittf0003Entity gittf0003Entity) {
        return Gittf0003Dto.builder()
                .id(gittf0003Entity.getId())
                .urCk(gittf0003Entity.getUrCk())
                .urDay(gittf0003Entity.getUrDay())
                .samlUrl(gittf0003Entity.getSamlUrl())
                .samlPass(gittf0003Entity.getSamlPass())
                .rangerUrl(gittf0003Entity.getRangerUrl())
                .rangerAuth(gittf0003Entity.getRangerAuth())
                .build();

    }
}
