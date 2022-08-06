package com.goodmit.hypergit.permissionmng.controller;

import com.goodmit.hypergit.appmng.dto.Gittb0001Dto;
import com.goodmit.hypergit.appmng.service.Gittb0001Service;
import com.goodmit.hypergit.permissionmng.dto.Gittc0001Dto;
import com.goodmit.hypergit.permissionmng.service.Gittc0001Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class PermissionmngController {

    private Gittc0001Service gittd0001Service;

    private Gittb0001Service gittb0001Service;

    /* Main Page */
    @GetMapping("/admin/permissionmng")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<Gittc0001Dto> gittc0001List = gittd0001Service.getGittc0001list(pageNum);
        Integer[] pageList = gittd0001Service.getPageList(pageNum);

        double  count = Double.valueOf(gittd0001Service.getGittc001Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gittc0001List", gittc0001List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        List<Gittb0001Dto> gittb0001List = gittb0001Service.getGitta0001list(pageNum);

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

        gittd0001Service.savePost(gittc0001Dto);

        return "redirect:/admin/permissionmng";
    }


    @GetMapping("/admin/permissiondel")
    public String permissiondelete(@RequestParam(value="idx") String idx) {

        long no = 0;

        int beginIndex = idx.indexOf(",");

        if(beginIndex > 0){

            String[] ArraysStr = idx.split(",");

            for(String s : ArraysStr){
                no = Long.parseLong(s);
                gittd0001Service.deletePost(no);
            }

        }else{

            no = Long.parseLong(idx);
            gittd0001Service.deletePost(no);

        }
        return "redirect:/admin/permissionmng";
    }

}
