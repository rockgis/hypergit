package com.goodmit.hypergit.main.controller;

import com.goodmit.hypergit.appmng.dto.Gittb0001Dto;
import com.goodmit.hypergit.appmng.service.Gittb0001Service;
import com.goodmit.hypergit.board.dto.BoardDto;
import com.goodmit.hypergit.board.service.BoardService;
import com.goodmit.hypergit.inspectionmng.dto.Gittd0004Dto;
import com.goodmit.hypergit.inspectionmng.service.Gittd0004Service;
import com.goodmit.hypergit.permissionmng.dto.Gittc0001Dto;
import com.goodmit.hypergit.permissionmng.service.Gittc0001Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
public class MainController {
    private BoardService boardService;

    private Gittb0001Service gittb0001Service;

    private Gittd0004Service gittd0004Service;

    private Gittc0001Service gittc0001Service;


    private static String authorizationRequestBaseUri = OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;


    /* Main Page */
    @GetMapping("/")
    public String main(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        double  count = Double.valueOf(boardService.getBoardCount());
        Integer postsTotalCount = (int) count;

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "main/user_main.html";
    }


    @GetMapping("/admin")
    public String admin(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        double  count = Double.valueOf(boardService.getBoardCount());
        Integer postsTotalCount = (int) count;

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "main/index.html";
    }

    @GetMapping("/admin/webloging/{no}")
    public String webloging(Principal principal, @PathVariable("no") Long no, Model model) {


        String username = principal.getName();

        Gittb0001Dto gittb0001Dto = gittb0001Service.getPost(no);

        String url=gittb0001Dto.getPgeUrlAr();

        Gittd0004Dto gittd0004Dto = new Gittd0004Dto();
        gittd0004Dto.setAppNm(gittb0001Dto.getAppNm());
        gittd0004Dto.setPgeUrlAr(gittb0001Dto.getPgeUrlAr());
        gittd0004Dto.setUsrEn(username);

        //사용권한 채크

        Gittc0001Dto gittc0001Dto = gittc0001Service.getPost(no);
        gittd0004Dto.setDcd(gittc0001Dto.getUsrDcd());
        gittd0004Dto.setUsrNm(gittc0001Dto.getUsrNm());
        gittd0004Dto.setUgCt(1);


        gittd0004Service.savePost(gittd0004Dto);


        model.addAttribute("url", url);

        return "main/webloging";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {

        return "redirect:"+authorizationRequestBaseUri+"/wso2";
    }

}
