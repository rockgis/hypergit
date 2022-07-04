package com.goodmit.hypergit.appmng.service;

import com.goodmit.hypergit.appmng.domain.entity.Gittb0001Entity;
import com.goodmit.hypergit.appmng.domain.repository.Gittb0001Repository;
import com.goodmit.hypergit.appmng.dto.Gittb0001Dto;
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
public class Gittb0001Service {
    private Gittb0001Repository gittb0001Repository;

    private static final int BLOCK_PAGE_NUM_COUNT = 10;  // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 10;       // 한 페이지에 존재하는 게시글 수

    @Transactional
    public List<Gittb0001Dto> getGitta0001list(Integer pageNum) {
        Page<Gittb0001Entity> page = gittb0001Repository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate")));

        List<Gittb0001Entity> gitta0001Entities = page.getContent();
        List<Gittb0001Dto> gittb0001DtoList = new ArrayList<>();

        for (Gittb0001Entity gittb0001Entity : gitta0001Entities) {
            gittb0001DtoList.add(this.convertEntityToDto(gittb0001Entity));
        }

        return gittb0001DtoList;
    }

    @Transactional
    public Long getGitta001Count() {
        return gittb0001Repository.count();
    }

    @Transactional
    public Gittb0001Dto getPost(Long id) {
        Optional<Gittb0001Entity> gitta0001EntityWrapper = gittb0001Repository.findById(id);
        Gittb0001Entity gittb0001Entity = gitta0001EntityWrapper.get();

        return this.convertEntityToDto(gittb0001Entity);
    }

    @Transactional
    public Long savePost(Gittb0001Dto gittb0001Dto) {
        return gittb0001Repository.save(gittb0001Dto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id) {
        gittb0001Repository.deleteById(id);
    }

    @Transactional
    public List<Gittb0001Dto> searchPosts(String keyword) {

        List<Gittb0001Entity> gitta0001Entities = gittb0001Repository.findByAppNmContaining(keyword);
        List<Gittb0001Dto> gittb0001DtoList = new ArrayList<>();

        if (gitta0001Entities.isEmpty()) return gittb0001DtoList;

        for (Gittb0001Entity gittb0001Entity : gitta0001Entities) {
            gittb0001DtoList.add(this.convertEntityToDto(gittb0001Entity));
        }

        return gittb0001DtoList;
    }

    public Integer[] getPageList(Integer curPageNum) {
        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getGitta001Count());

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        Integer[] pageList = new Integer[blockLastPageNum];

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        // 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }

    private Gittb0001Dto convertEntityToDto(Gittb0001Entity gittb0001Entity) {
        return Gittb0001Dto.builder()
                .id(gittb0001Entity.getId())
                .appSn(gittb0001Entity.getAppSn())
                .appNm(gittb0001Entity.getAppNm())
                .clNm(gittb0001Entity.getClNm())
                .pgeUrlAr(gittb0001Entity.getPgeUrlAr())
                .descTt(gittb0001Entity.getDescTt())
                .seTf(gittb0001Entity.getSeTf())
                .bsTf(gittb0001Entity.getBsTf())
                .rgEn(gittb0001Entity.getRgEn())
                .altEn(gittb0001Entity.getAltEn())
                .createdDate(gittb0001Entity.getCreatedDate())
                .build();

    }
}
