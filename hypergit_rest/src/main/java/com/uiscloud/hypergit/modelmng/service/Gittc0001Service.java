package com.goodmit.hypergit.modelmng.service;

import com.goodmit.hypergit.modelmng.domain.entity.Gittc0001Entity;
import com.goodmit.hypergit.modelmng.domain.repository.Gittc0001Repository;
import com.goodmit.hypergit.modelmng.dto.Gittc0001Dto;
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
public class Gittc0001Service {
    private Gittc0001Repository gittc0001Repository;

    private static final int BLOCK_PAGE_NUM_COUNT = 10;  // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 10;       // 한 페이지에 존재하는 게시글 수

    @Transactional
    public List<Gittc0001Dto> getGittc0001list(Integer pageNum) {
        Page<Gittc0001Entity> page = gittc0001Repository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate")));

        List<Gittc0001Entity> gittc0001Entities = page.getContent();
        List<Gittc0001Dto> gittc0001DtoList = new ArrayList<>();

        for (Gittc0001Entity gittc0001Entity : gittc0001Entities) {
            gittc0001DtoList.add(this.convertEntityToDto(gittc0001Entity));
        }

        return gittc0001DtoList;
    }

    public List<Gittc0001Dto> getGittc0001listUser(String usrEn) {

        List<Gittc0001Entity> gittc0001Entities = gittc0001Repository.findByUsrEn(usrEn);
        List<Gittc0001Dto> gittc0001DtoList = new ArrayList<>();

        for (Gittc0001Entity gittc0001Entity : gittc0001Entities) {
            gittc0001DtoList.add(this.convertEntityToDto(gittc0001Entity));
        }

        return gittc0001DtoList;
    }

    @Transactional
    public Long getGittc001Count() {
        return gittc0001Repository.count();
    }

    @Transactional
    public Gittc0001Dto getPost(Long id) {
        Optional<Gittc0001Entity> gitta0001EntityWrapper = gittc0001Repository.findById(id);
        Gittc0001Entity gittc0001Entity = gitta0001EntityWrapper.get();

        return this.convertEntityToDto(gittc0001Entity);
    }


    @Transactional
    public Long savePost(Gittc0001Dto gittc0001Dto) {
        return gittc0001Repository.save(gittc0001Dto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id) {
        gittc0001Repository.deleteById(id);
    }

    @Transactional
    public List<Gittc0001Dto> searchPosts(String keyword) {

        List<Gittc0001Entity> gitta0001Entities = gittc0001Repository.findByAppNmContaining(keyword);
        List<Gittc0001Dto> gittc0001DtoList = new ArrayList<>();

        if (gitta0001Entities.isEmpty()) return gittc0001DtoList;

        for (Gittc0001Entity gittc0001Entity : gitta0001Entities) {
            gittc0001DtoList.add(this.convertEntityToDto(gittc0001Entity));
        }

        return gittc0001DtoList;
    }

    public Integer[] getPageList(Integer curPageNum) {
        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getGittc001Count());

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

    private Gittc0001Dto convertEntityToDto(Gittc0001Entity gittc0001Entity) {
        return Gittc0001Dto.builder()
                .id(gittc0001Entity.getId())
                .gsnId(gittc0001Entity.getGsnId())
                .clNm(gittc0001Entity.getClNm())
                .pgeUrlAr(gittc0001Entity.getPgeUrlAr())
                .appNm(gittc0001Entity.getAppNm())
                .roleId(gittc0001Entity.getRoleId())
                .useYn(gittc0001Entity.getUseYn())
                .usrDcd(gittc0001Entity.getUsrDcd())
                .usrNm(gittc0001Entity.getUsrNm())
                .usrEn(gittc0001Entity.getUsrEn())
                .emNm(gittc0001Entity.getEmNm())
                .rgEn(gittc0001Entity.getRgEn())
                .altEn(gittc0001Entity.getAltEn())
                .startDate(gittc0001Entity.getStartDate())
                .endDate(gittc0001Entity.getEndDate())
                .createdDate(gittc0001Entity.getCreatedDate())
                .modifiedDate(gittc0001Entity.getModifiedDate())
                .build();
    }
}
