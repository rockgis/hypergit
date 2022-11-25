package com.uiscloud.hypergit.rollmn.controller;

import com.uiscloud.hypergit.appmng.dto.Gittb0001Dto;
import com.uiscloud.hypergit.rollmn.dto.Gittf0001Dto;
import com.uiscloud.hypergit.rollmn.service.Gittf0001Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class RollmnController {
    private Gittf0001Service gittf0001Service;

    /* Main Page */
    @GetMapping("/admin/rollmn")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<Gittf0001Dto> gittf0001List = gittf0001Service.getGittf0001list(pageNum);
        Integer[] pageList = gittf0001Service.getPageList(pageNum);


        // 총 게시글 갯수
        double  count = Double.valueOf(gittf0001Service.getGitta001Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gittf0001List", gittf0001List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "rollmn/main.html";
    }

    @PostMapping("/admin/rollmnpost")
    public String rollmnpost(Principal principal, Gittf0001Dto gittf0001Dto) {

        LocalDateTime now = LocalDateTime.now();

        gittf0001Dto.setRgEn(principal.getName());
        gittf0001Dto.setAltEn(principal.getName());

        // System.out.println(now);

        gittf0001Service.savePost(gittf0001Dto);

        return "redirect:/admin/rollmn";
    }

    @GetMapping("/admin/rollmndel")
    public String rollmndelete(@RequestParam(value="idx") String idx) {

        long no = 0;

        int beginIndex = idx.indexOf(",");

        if(beginIndex > 0){

            String[] ArraysStr = idx.split(",");

            for(String s : ArraysStr){
                no = Long.parseLong(s);
                gittf0001Service.deletePost(no);
            }

        }else{

            no = Long.parseLong(idx);
            gittf0001Service.deletePost(no);

        }
        return "redirect:/admin/rollmn";
    }


}
