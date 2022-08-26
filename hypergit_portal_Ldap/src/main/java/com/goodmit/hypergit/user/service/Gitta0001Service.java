package com.goodmit.hypergit.user.service;

import com.goodmit.hypergit.user.domain.entity.Gitta0001Entity;
import com.goodmit.hypergit.user.domain.repository.Gitta0001Repository;
import com.goodmit.hypergit.user.dto.Gitta0001Dto;
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
public class Gitta0001Service {

    private Gitta0001Repository gitta0001Repository;

    private static final int BLOCK_PAGE_NUM_COUNT = 10;  // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 10;       // 한 페이지에 존재하는 게시글 수

    @Transactional
    public List<Gitta0001Dto> getGitta0001list(Integer pageNum) {
        Page<Gitta0001Entity> page = gitta0001Repository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate")));

        List<Gitta0001Entity> gitta0001Entities = page.getContent();
        List<Gitta0001Dto> gitta0001DtoList = new ArrayList<>();

        for (Gitta0001Entity gitta0001Entity : gitta0001Entities) {
            gitta0001DtoList.add(this.convertEntityToDto(gitta0001Entity));
        }

        return gitta0001DtoList;
    }

    @Transactional
    public Long getGitta001Count() {
        return gitta0001Repository.count();
    }

    @Transactional
    public Gitta0001Dto getPost(Long id) {
        Optional<Gitta0001Entity> gitta0001EntityWrapper = gitta0001Repository.findById(id);
        Gitta0001Entity gitta0001Entity = gitta0001EntityWrapper.get();

        return this.convertEntityToDto(gitta0001Entity);
    }

    @Transactional
    public Long savePost(Gitta0001Dto gitta0001Dto) {
        return gitta0001Repository.save(gitta0001Dto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id) {
        gitta0001Repository.deleteById(id);
    }

    @Transactional
    public List<Gitta0001Dto> searchPosts(Gitta0001Dto gitta0001Dto) {

        String dcd = gitta0001Dto.getDcd();
        String usrNm =  gitta0001Dto.getUsrNm();
        String usrEn = gitta0001Dto.getUsrEn();
        String emNm = gitta0001Dto.getEmNm();
        String nrIpAr = gitta0001Dto.getNrIpAr();
        String earEhf = gitta0001Dto.getEarEhf();

        System.out.println(dcd);
        System.out.println(usrNm);
        System.out.println(usrEn);
        System.out.println(emNm);
        System.out.println(nrIpAr);
        System.out.println(earEhf);

        List<Gitta0001Entity> gitta0001Entities = null;

        if(!dcd.isEmpty()){
            gitta0001Entities = gitta0001Repository.findByDcdContaining(dcd);
        } else if (!usrNm.isEmpty()) {
            gitta0001Entities = gitta0001Repository.findByUsrNmContaining(usrNm);
        }else if (!usrEn.isEmpty()) {
            gitta0001Entities = gitta0001Repository.findByUsrEnContaining(usrEn);
        }else if (!emNm.isEmpty()) {
            gitta0001Entities = gitta0001Repository.findByEmNmContaining(emNm);
        }else if (!nrIpAr.isEmpty()) {
            gitta0001Entities = gitta0001Repository.findByNrIpArContaining(nrIpAr);
        }else if (!earEhf.equals("A")) {
            gitta0001Entities = gitta0001Repository.findByEarEhfContaining(earEhf);
        }else{

            Page<Gitta0001Entity> page = gitta0001Repository.findAll(PageRequest.of(0, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate")));

            gitta0001Entities = page.getContent();
        }

        List<Gitta0001Dto> gitta0001DtoList = new ArrayList<>();

        if (gitta0001Entities.isEmpty()) return gitta0001DtoList;

        for (Gitta0001Entity gitta0001Entity : gitta0001Entities) {
            gitta0001DtoList.add(this.convertEntityToDto(gitta0001Entity));
        }

        return gitta0001DtoList;
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

    private Gitta0001Dto convertEntityToDto(Gitta0001Entity gitta0001Entity) {
        return Gitta0001Dto.builder()
                .id(gitta0001Entity.getId())
                .dcd(gitta0001Entity.getDcd())
                .usrNm(gitta0001Entity.getUsrNm())
                .usrEn(gitta0001Entity.getUsrEn())
                .emNm(gitta0001Entity.getEmNm())
                .earEhf(gitta0001Entity.getEarEhf())
                .adTf(gitta0001Entity.getAdTf())
                .nrIpAr(gitta0001Entity.getNrIpAr())
                .rgEn(gitta0001Entity.getRgEn())
                .createdDate(gitta0001Entity.getCreatedDate())
                .build();

        // this.rg_dt = rg_dt;
        // this.alt_dt = alt_dt;
    }
}
