package com.uiscloud.hypergit.inspectionmng.service;

import com.uiscloud.hypergit.inspectionmng.domain.entity.Gittd0003Entity;
import com.uiscloud.hypergit.inspectionmng.domain.repository.Gittd0003Repository;
import com.uiscloud.hypergit.inspectionmng.dto.Gittd0003Dto;
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
public class Gittd0003Service {
    private Gittd0003Repository gittd0003Repository;

    private static final int BLOCK_PAGE_NUM_COUNT = 10;  // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 10;       // 한 페이지에 존재하는 게시글 수

    @Transactional
    public List<Gittd0003Dto> getGittd0003list(Integer pageNum) {
        Page<Gittd0003Entity> page = gittd0003Repository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate")));

        List<Gittd0003Entity> gitta0003Entities = page.getContent();
        List<Gittd0003Dto> gittd0003DtoList = new ArrayList<>();

        for (Gittd0003Entity gittd0003Entity : gitta0003Entities) {
            gittd0003DtoList.add(this.convertEntityToDto(gittd0003Entity));
        }

        return gittd0003DtoList;
    }

    @Transactional
    public Long getGittd003Count() {
        return gittd0003Repository.count();
    }

    @Transactional
    public Gittd0003Dto getPost(Long id) {
        Optional<Gittd0003Entity> gitta0003EntityWrapper = gittd0003Repository.findById(id);
        Gittd0003Entity gittd0003Entity = gitta0003EntityWrapper.get();

        return this.convertEntityToDto(gittd0003Entity);
    }

    @Transactional
    public Long savePost(Gittd0003Dto gittd0001Dto) {
        return gittd0003Repository.save(gittd0001Dto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id) {
        gittd0003Repository.deleteById(id);
    }

    @Transactional
    public List<Gittd0003Dto> searchPosts(String keyword) {

        List<Gittd0003Entity> gitta0003Entities = gittd0003Repository.findByUsrEnContaining(keyword);
        List<Gittd0003Dto> gittd0003DtoList = new ArrayList<>();

        if (gitta0003Entities.isEmpty()) return gittd0003DtoList;

        for (Gittd0003Entity gittd0003Entity : gitta0003Entities) {
            gittd0003DtoList.add(this.convertEntityToDto(gittd0003Entity));
        }

        return gittd0003DtoList;
    }

    public Integer[] getPageList(Integer curPageNum) {
        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getGittd003Count());

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

    private Gittd0003Dto convertEntityToDto(Gittd0003Entity gittd0003Entity) {
        return Gittd0003Dto.builder()
                .id(gittd0003Entity.getId())
                .wrTy(gittd0003Entity.getWrTy())
                .dcd(gittd0003Entity.getDcd())
                .usrNm(gittd0003Entity.getUsrNm())
                .usrEn(gittd0003Entity.getUsrEn())
                .ugId(gittd0003Entity.getUgId())
                .rgEn(gittd0003Entity.getRgEn())
                .altEn(gittd0003Entity.getAltEn())
                .createdDate(gittd0003Entity.getCreatedDate())
                .modifiedDate(gittd0003Entity.getModifiedDate())
                .build();
    }
}
