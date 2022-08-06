package com.goodmit.hypergit.inspectionmng.service;

import com.goodmit.hypergit.inspectionmng.domain.entity.Gittd0004Entity;
import com.goodmit.hypergit.inspectionmng.domain.repository.Gittd0004Repository;
import com.goodmit.hypergit.inspectionmng.dto.Gittd0004Dto;
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
public class Gittd0004Service {
    private Gittd0004Repository gittd0004Repository;

    private static final int BLOCK_PAGE_NUM_COUNT = 10;  // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 10;       // 한 페이지에 존재하는 게시글 수

    @Transactional
    public List<Gittd0004Dto> getGittd0004list(Integer pageNum) {
        Page<Gittd0004Entity> page = gittd0004Repository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate")));

        List<Gittd0004Entity> gitta0004Entities = page.getContent();
        List<Gittd0004Dto> gittd0004DtoList = new ArrayList<>();

        for (Gittd0004Entity gittd0004Entity : gitta0004Entities) {
            gittd0004DtoList.add(this.convertEntityToDto(gittd0004Entity));
        }

        return gittd0004DtoList;
    }

    @Transactional
    public Long getGittd004Count() {
        return gittd0004Repository.count();
    }

    @Transactional
    public Gittd0004Dto getPost(Long id) {
        Optional<Gittd0004Entity> gitta0004EntityWrapper = gittd0004Repository.findById(id);
        Gittd0004Entity gittd0004Entity = gitta0004EntityWrapper.get();

        return this.convertEntityToDto(gittd0004Entity);
    }

    @Transactional
    public Long savePost(Gittd0004Dto gittd0001Dto) {
        return gittd0004Repository.save(gittd0001Dto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id) {
        gittd0004Repository.deleteById(id);
    }

    @Transactional
    public List<Gittd0004Dto> searchPosts(String keyword) {

        List<Gittd0004Entity> gitta0004Entities = gittd0004Repository.findByUsrEnContaining(keyword);
        List<Gittd0004Dto> gittd0004DtoList = new ArrayList<>();

        if (gitta0004Entities.isEmpty()) return gittd0004DtoList;

        for (Gittd0004Entity gittd0004Entity : gitta0004Entities) {
            gittd0004DtoList.add(this.convertEntityToDto(gittd0004Entity));
        }

        return gittd0004DtoList;
    }

    public Integer[] getPageList(Integer curPageNum) {
        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getGittd004Count());

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

    private Gittd0004Dto convertEntityToDto(Gittd0004Entity gittd0004Entity) {
        return Gittd0004Dto.builder()
                .id(gittd0004Entity.getId())
                .dcd(gittd0004Entity.getDcd())
                .usrNm(gittd0004Entity.getUsrNm())
                .usrEn(gittd0004Entity.getUsrEn())
                .appNm(gittd0004Entity.getAppNm())
                .pgeUrlAr(gittd0004Entity.getPgeUrlAr())
                .ugCt(gittd0004Entity.getUgCt())
                .createdDate(gittd0004Entity.getCreatedDate())
                .build();
    }
}
