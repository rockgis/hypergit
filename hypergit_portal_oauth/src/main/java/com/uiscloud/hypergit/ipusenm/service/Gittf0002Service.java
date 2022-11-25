package com.uiscloud.hypergit.ipusenm.service;

import com.uiscloud.hypergit.ipusenm.domain.entity.Gittf0002Entity;
import com.uiscloud.hypergit.ipusenm.domain.repository.Gittf0002Repository;
import com.uiscloud.hypergit.ipusenm.dto.Gittf0002Dto;
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
public class Gittf0002Service {
    private Gittf0002Repository gittf0002Repository;

    private static final int BLOCK_PAGE_NUM_COUNT = 10;  // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 10;       // 한 페이지에 존재하는 게시글 수

    @Transactional
    public List<Gittf0002Dto> getGittf0002list(Integer pageNum) {
        Page<Gittf0002Entity> page = gittf0002Repository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate")));

        List<Gittf0002Entity> gittf0001Entities = page.getContent();
        List<Gittf0002Dto> gittf0001DtoList = new ArrayList<>();

        for (Gittf0002Entity gittf0002Entity : gittf0001Entities) {
            gittf0001DtoList.add(this.convertEntityToDto(gittf0002Entity));
        }

        return gittf0001DtoList;
    }

    @Transactional
    public Long getGitta002Count() {
        return gittf0002Repository.count();
    }

    @Transactional
    public Gittf0002Dto getPost(Long id) {
        Optional<Gittf0002Entity> gittf0001EntityWrapper = gittf0002Repository.findById(id);
        Gittf0002Entity gittf0002Entity = gittf0001EntityWrapper.get();

        return this.convertEntityToDto(gittf0002Entity);
    }

    @Transactional
    public Long savePost(Gittf0002Dto gittf0001Dto) {
        return gittf0002Repository.save(gittf0001Dto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id) {
        gittf0002Repository.deleteById(id);
    }

    @Transactional
    public List<Gittf0002Dto> searchPosts(String keyword) {
        List<Gittf0002Entity> gittf0001Entities = gittf0002Repository.findByUsrNmContaining(keyword);
        List<Gittf0002Dto> gittf0001DtoList = new ArrayList<>();

        if (gittf0001Entities.isEmpty()) return gittf0001DtoList;

        for (Gittf0002Entity gittf0002Entity : gittf0001Entities) {
            gittf0001DtoList.add(this.convertEntityToDto(gittf0002Entity));
        }

        return gittf0001DtoList;
    }

    public Integer[] getPageList(Integer curPageNum) {

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getGitta002Count());

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

    private Gittf0002Dto convertEntityToDto(Gittf0002Entity gittf0002Entity) {
        return Gittf0002Dto.builder()
                .id(gittf0002Entity.getId())
                .dcd(gittf0002Entity.getDcd())
                .usrNm(gittf0002Entity.getUsrNm())
                .usrEn(gittf0002Entity.getUsrEn())
                .nrIpAr(gittf0002Entity.getNrIpAr())
                .seTf(gittf0002Entity.getSeTf())
                .descTt(gittf0002Entity.getDescTt())
                .rgEn(gittf0002Entity.getRgEn())
                .altEn(gittf0002Entity.getAltEn())
                .createdDate(gittf0002Entity.getCreatedDate())
                .modifiedDate(gittf0002Entity.getModifiedDate())
                .build();

    }
}
