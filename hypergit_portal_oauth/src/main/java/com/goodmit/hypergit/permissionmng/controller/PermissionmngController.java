package com.goodmit.hypergit.permissionmng.controller;

import com.goodmit.hypergit.appmng.dto.Gittb0001Dto;
import com.goodmit.hypergit.appmng.service.Gittb0001Service;
import com.goodmit.hypergit.inspectionmng.service.Gittd0003Service;
import com.goodmit.hypergit.inspectionmng.dto.Gittd0003Dto;
import com.goodmit.hypergit.permissionmng.dto.Gittc0001Dto;
import com.goodmit.hypergit.permissionmng.service.Gittc0001Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
public class PermissionmngController {

    private Gittc0001Service gittc0001Service;

    private Gittd0003Service gittd0003Service;

    private Gittb0001Service gittb0001Service;

    /* Main Page */
    @GetMapping("/admin/permissionmng")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<Gittc0001Dto> gittc0001List = gittc0001Service.getGittc0001list(pageNum);
        Integer[] pageList = gittc0001Service.getPageList(pageNum);

        double  count = Double.valueOf(gittc0001Service.getGittc001Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gittc0001List", gittc0001List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);


        List<Gittb0001Dto> gittb0001List = gittb0001Service.getGitta0001list(1);
        model.addAttribute("gittb0001List", gittb0001List);


        return "permissionmng/main.html";
    }

    @GetMapping("/admin/permissionmng/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        //List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        return "permissionmng/main.html";
    }

    @PostMapping("/admin/permissionpost")
    public String permissionpost(Principal principal, Gittc0001Dto gittc0001Dto) {

        LocalDateTime now = LocalDateTime.now();
        gittc0001Dto.setAltEn(principal.getName());

        gittc0001Dto.setModifiedDate(now);

        // System.out.println(now);

        gittc0001Service.savePost(gittc0001Dto);

        log.info("권한 감사 기능 수행 Start ");
        Gittd0003Dto gittd0003Dto = new Gittd0003Dto();
        gittd0003Dto.setDcd(gittc0001Dto.getUsrDcd());
        gittd0003Dto.setAltEn(principal.getName()); // 작성 한 아이디
        gittd0003Dto.setRgEn(gittc0001Dto.getRgEn());
        gittd0003Dto.setUsrEn(gittc0001Dto.getUsrEn());
        gittd0003Dto.setUsrNm(gittc0001Dto.getUsrNm());
        gittd0003Dto.setWrTy("BDP SAML 연동 시스템 ");
        gittd0003Dto.setUgId(1);

        gittd0003Service.savePost(gittd0003Dto);


        return "redirect:/admin/permissionmng";
    }


    @GetMapping("/admin/permissiondel")
    public String permissiondelete(Principal principal, @RequestParam(value="idx") String idx) {

        long no = 0;

        log.info("권한 감사 삭제 기능 수행 Start ");
        Gittd0003Dto gittd0003Dto = new Gittd0003Dto();
        Gittc0001Dto gittc0001Dto = new Gittc0001Dto();

        int beginIndex = idx.indexOf(",");

        if(beginIndex > 0){

            String[] ArraysStr = idx.split(",");

            for(String s : ArraysStr){
                no = Long.parseLong(s);

                gittc0001Dto = gittc0001Service.getPost(no);

                gittd0003Dto.setDcd(gittc0001Dto.getUsrDcd());
                gittd0003Dto.setAltEn(principal.getName()); // 작성 한 아이디
                gittd0003Dto.setRgEn(gittc0001Dto.getRgEn());
                gittd0003Dto.setUsrEn(gittc0001Dto.getUsrEn());
                gittd0003Dto.setUsrNm(gittc0001Dto.getUsrNm());
                gittd0003Dto.setWrTy("BDP SAML 연동 시스템 ");
                gittd0003Dto.setUgId(0);

                gittd0003Service.savePost(gittd0003Dto);


                gittc0001Service.deletePost(no);



            }

        }else{

            no = Long.parseLong(idx);

            gittc0001Dto = gittc0001Service.getPost(no);

            gittd0003Dto.setDcd(gittc0001Dto.getUsrDcd());
            gittd0003Dto.setAltEn(principal.getName()); // 작성 한 아이디
            gittd0003Dto.setRgEn(gittc0001Dto.getRgEn());
            gittd0003Dto.setUsrEn(gittc0001Dto.getUsrEn());
            gittd0003Dto.setUsrNm(gittc0001Dto.getUsrNm());
            gittd0003Dto.setWrTy("BDP SAML 연동 시스템 ");
            gittd0003Dto.setUgId(0);

            gittd0003Service.savePost(gittd0003Dto);

            gittc0001Service.deletePost(no);

        }
        return "redirect:/admin/permissionmng";
    }

}
