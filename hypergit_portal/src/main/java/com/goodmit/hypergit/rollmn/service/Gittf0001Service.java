package com.goodmit.hypergit.rollmn.service;

import com.goodmit.hypergit.rollmn.domain.entity.Gittf0001Entity;
import com.goodmit.hypergit.rollmn.domain.repository.Gittf0001Repository;
import com.goodmit.hypergit.rollmn.dto.Gittf0001Dto;
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
public class Gittf0001Service {
    private Gittf0001Repository gittf0001Repository;

    private static final int BLOCK_PAGE_NUM_COUNT = 10;  // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 10;       // 한 페이지에 존재하는 게시글 수

    @Transactional
    public List<Gittf0001Dto> getGittf0001list(Integer pageNum) {
        Page<Gittf0001Entity> page = gittf0001Repository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate")));

        List<Gittf0001Entity> gittf0001Entities = page.getContent();
        List<Gittf0001Dto> gittf0001DtoList = new ArrayList<>();

        for (Gittf0001Entity gittf0001Entity : gittf0001Entities) {
            gittf0001DtoList.add(this.convertEntityToDto(gittf0001Entity));
        }

        return gittf0001DtoList;
    }

    @Transactional
    public Long getGitta001Count() {
        return gittf0001Repository.count();
    }

    @Transactional
    public Gittf0001Dto getPost(Long id) {
        Optional<Gittf0001Entity> gittf0001EntityWrapper = gittf0001Repository.findById(id);
        Gittf0001Entity gittf0001Entity = gittf0001EntityWrapper.get();

        return this.convertEntityToDto(gittf0001Entity);
    }

    @Transactional
    public Long savePost(Gittf0001Dto gittf0001Dto) {
        return gittf0001Repository.save(gittf0001Dto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id) {
        gittf0001Repository.deleteById(id);
    }

    @Transactional
    public List<Gittf0001Dto> searchPosts(String keyword) {
        List<Gittf0001Entity> gittf0001Entities = gittf0001Repository.findByRoleNmContaining(keyword);
        List<Gittf0001Dto> gittf0001DtoList = new ArrayList<>();

        if (gittf0001Entities.isEmpty()) return gittf0001DtoList;

        for (Gittf0001Entity gittf0001Entity : gittf0001Entities) {
            gittf0001DtoList.add(this.convertEntityToDto(gittf0001Entity));
        }

        return gittf0001DtoList;
    }

    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getGitta001Count());

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        // 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }

    private Gittf0001Dto convertEntityToDto(Gittf0001Entity gittf0001Entity) {
        return Gittf0001Dto.builder()
                .id(gittf0001Entity.getId())
                .roleId(gittf0001Entity.getRoleId())
                .roleNm(gittf0001Entity.getRoleNm())
                .descTt(gittf0001Entity.getDescTt())
                .rgEn(gittf0001Entity.getRgEn())
                .altEn(gittf0001Entity.getAltEn())
                .createdDate(gittf0001Entity.getCreatedDate())
                .build();

        // this.rg_dt = rg_dt;
        // this.alt_dt = alt_dt;
    }
}
