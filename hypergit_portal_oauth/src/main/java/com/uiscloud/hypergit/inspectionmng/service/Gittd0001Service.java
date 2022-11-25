package com.goodmit.hypergit.inspectionmng.service;

import com.goodmit.hypergit.inspectionmng.domain.entity.Gittd0001Entity;
import com.goodmit.hypergit.inspectionmng.domain.repository.Gittd0001Repository;
import com.goodmit.hypergit.inspectionmng.dto.Gittd0001Dto;
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
public class Gittd0001Service {
    private Gittd0001Repository gittd0001Repository;

    private static final int BLOCK_PAGE_NUM_COUNT = 10;  // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 10;       // 한 페이지에 존재하는 게시글 수

    @Transactional
    public List<Gittd0001Dto> getGittd0001list(Integer pageNum) {
        Page<Gittd0001Entity> page = gittd0001Repository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate")));

        List<Gittd0001Entity> gittd0001Entities = page.getContent();
        List<Gittd0001Dto> gittd0001DtoList = new ArrayList<>();

        for (Gittd0001Entity gittd0001Entity : gittd0001Entities) {
            gittd0001DtoList.add(this.convertEntityToDto(gittd0001Entity));
        }

        return gittd0001DtoList;
    }

    @Transactional
    public Long getGittd001Count() {
        return gittd0001Repository.count();
    }

    @Transactional
    public Gittd0001Dto getPost(Long id) {
        Optional<Gittd0001Entity> gittd0001EntityWrapper = gittd0001Repository.findById(id);
        Gittd0001Entity gittd0001Entity = gittd0001EntityWrapper.get();

        return this.convertEntityToDto(gittd0001Entity);
    }

    @Transactional
    public Long savePost(Gittd0001Dto gittd0001Dto) {
        return gittd0001Repository.save(gittd0001Dto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id) {
        gittd0001Repository.deleteById(id);
    }

    @Transactional
    public List<Gittd0001Dto> searchPosts(String keyword) {

        List<Gittd0001Entity> gitta0001Entities = gittd0001Repository.findByUgNmContaining(keyword);
        List<Gittd0001Dto> gittd0001DtoList = new ArrayList<>();

        if (gitta0001Entities.isEmpty()) return gittd0001DtoList;

        for (Gittd0001Entity gittd0001Entity : gitta0001Entities) {
            gittd0001DtoList.add(this.convertEntityToDto(gittd0001Entity));
        }

        return gittd0001DtoList;
    }

    public Integer[] getPageList(Integer curPageNum) {
        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getGittd001Count());

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

    private Gittd0001Dto convertEntityToDto(Gittd0001Entity gittd0001Entity) {
        return Gittd0001Dto.builder()
                .id(gittd0001Entity.getId())
                .dcd(gittd0001Entity.getDcd())
                .usrNm(gittd0001Entity.getUsrNm())
                .usrEn(gittd0001Entity.getUsrEn())
                .emNm(gittd0001Entity.getEmNm())
                .usrIp(gittd0001Entity.getUsrIp())
                .ugNm(gittd0001Entity.getUgNm())
                .refUrlAr(gittd0001Entity.getRefUrlAr())
                .createdDate(gittd0001Entity.getCreatedDate())
                .build();
    }
}
