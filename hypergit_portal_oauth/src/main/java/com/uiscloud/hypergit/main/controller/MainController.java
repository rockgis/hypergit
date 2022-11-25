package com.uiscloud.hypergit.main.controller;

import com.uiscloud.hypergit.appmng.dto.Gittb0001Dto;
import com.uiscloud.hypergit.appmng.service.Gittb0001Service;
import com.uiscloud.hypergit.inspectionmng.service.Gittd0004Service;
import com.uiscloud.hypergit.inspectionmng.dto.Gittd0004Dto;
import com.uiscloud.hypergit.board.dto.BoardDto;
import com.uiscloud.hypergit.board.service.BoardService;
import com.uiscloud.hypergit.common.config.auth.LoginUser;
import com.uiscloud.hypergit.common.config.auth.dto.SessionUser;
import com.uiscloud.hypergit.permissionmng.dto.Gittc0001Dto;
import com.uiscloud.hypergit.permissionmng.service.Gittc0001Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  //  private final static Logger log = LoggerFactory.getLogger(MainController.class);


    /* Main Page */
    @GetMapping("/")
    public String main(Authentication authentication, @LoginUser SessionUser user) {

        if (authentication != null) {

            log.info("Main Start");
            log.info("타입정보 : " + authentication.getClass());
            log.info("권한 정보 : " + authentication.getAuthorities());

            // 세션 정보 객체 반환
            WebAuthenticationDetails web = (WebAuthenticationDetails)authentication.getDetails();
            log.info("세션ID : " + web.getSessionId());
            log.info("접속IP : " + web.getRemoteAddress());

            if(authentication.getAuthorities().toString().equals("[ROLE_ADMIN]")){
                return "redirect:/admin";
            }else {
                return "redirect:/user";
            }

        }else {

            return "redirect:/login";

        }


    }

    @GetMapping("/user")
    public String mainuser( Authentication authentication,  Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

        String username = authentication.getName();

        log.info("user Start");
        log.info("타입정보 : " + authentication.getClass());
        log.info("권한 정보 : " + authentication.getAuthorities());

        // 세션 정보 객체 반환
        WebAuthenticationDetails web = (WebAuthenticationDetails)authentication.getDetails();
        log.info("세션ID : " + web.getSessionId());
        log.info("접속IP : " + web.getRemoteAddress());

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

            List<Gittc0001Dto> gittc0001List = gittc0001Service.getGittc0001listUser(username);

            model.addAttribute("gittc0001List", gittc0001List);


            return "main/user_main.html";
        }


    }


    @GetMapping("/admin")
    public String admin(Authentication authentication, Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

        log.info("Admin Start");
        log.info("타입정보 : " + authentication.getClass());
        log.info("권한 정보 : " + authentication.getAuthorities());

        // 세션 정보 객체 반환
        WebAuthenticationDetails web = (WebAuthenticationDetails)authentication.getDetails();
        log.info("세션ID : " + web.getSessionId());
        log.info("접속IP : " + web.getRemoteAddress());

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
    public String webloging(Authentication authentication, @PathVariable("no") Long no, Model model) {

        log.info("Webloging Start");
        log.info("타입정보 : " + authentication.getClass());
        log.info("권한 정보 : " + authentication.getAuthorities());

        // 세션 정보 객체 반환
        WebAuthenticationDetails web = (WebAuthenticationDetails)authentication.getDetails();
        log.info("세션ID : " + web.getSessionId());
        log.info("접속IP : " + web.getRemoteAddress());


        String username = authentication.getName();

        Gittc0001Dto gittc0001Dto = gittc0001Service.getPost(no);

        Gittb0001Dto gittb0001Dto = gittb0001Service.getPost((long) gittc0001Dto.getGsnId());



        String url=gittb0001Dto.getPgeUrlAr();

        log.info("url : " + url);
        log.info("username : " + username);

        //사용권한 채크
        Gittd0004Dto gittd0004Dto = new Gittd0004Dto();
        gittd0004Dto.setAppNm(gittb0001Dto.getAppNm());
        gittd0004Dto.setPgeUrlAr(gittb0001Dto.getPgeUrlAr());
        gittd0004Dto.setUsrEn(username);
        gittd0004Dto.setDcd(gittc0001Dto.getUsrDcd());
        gittd0004Dto.setUsrNm(gittc0001Dto.getUsrNm());
        gittd0004Dto.setUgCt(1);

        gittd0004Service.savePost(gittd0004Dto);

        model.addAttribute("url", url);

        return "main/webloging";
    }

    @GetMapping("/login")
    public String getLoginPage() {

        return "redirect:"+authorizationRequestBaseUri+"/wso2";
    }


    @GetMapping("/help")
    public String gethelp(Model model) {

        return "redirect:/swagger-ui.html";
    }

}
