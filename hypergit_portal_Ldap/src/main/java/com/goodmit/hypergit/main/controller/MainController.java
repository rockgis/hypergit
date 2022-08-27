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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
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
    public String main(Authentication authentication, Model model) {

        if (authentication != null) {
            System.out.println("타입정보 : " + authentication.getClass());
            System.out.println("권한 정보 : " + authentication.getAuthorities().toString().equals("[ROLE_ADMIN]"));

            // 세션 정보 객체 반환
            WebAuthenticationDetails web = (WebAuthenticationDetails)authentication.getDetails();
            System.out.println("세션ID : " + web.getSessionId());
            System.out.println("접속IP : " + web.getRemoteAddress());

            if(authentication.getAuthorities().toString().equals("[ROLE_ADMIN]")){
                return "redirect:/admin";
            }else {
                return "redirect:/user";
            }

        }else {

            return "redirect:/admin/login";

        }


    }

    @GetMapping("/user")
    public String mainuser( Authentication authentication,  Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

        String username = authentication.getName();

        System.out.println("권한 정보 : " + authentication.getAuthorities().toString().equals("[ROLE_ADMIN]"));

        if(authentication.getAuthorities().toString().equals("[ROLE_ADMIN]")){

            return "redirect:/admin";

        }else {

            List<BoardDto> boardList = boardService.getBoardlist(pageNum);
            Integer[] pageList = boardService.getPageList(pageNum);

            double  count = Double.valueOf(boardService.getBoardCount());
            Integer postsTotalCount = (int) count;

            model.addAttribute("boardList", boardList);
            model.addAttribute("pageList", pageList);
            model.addAttribute("postsTotalCount", postsTotalCount);

            //String username = principal.getName();  권한에 따라 URL 가지고 와야 됨

            username = "450192";

            List<Gittc0001Dto> gittc0001List = gittc0001Service.getGittc0001listUser(username);

            model.addAttribute("gittc0001List", gittc0001List);


            return "main/user_main.html";
        }


    }


    @GetMapping("/admin")
    public String admin(Principal principal, Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

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

    @GetMapping("/help")
    public String gethelp(Model model) {

        return "redirect:/swagger-ui.html";
    }

}
