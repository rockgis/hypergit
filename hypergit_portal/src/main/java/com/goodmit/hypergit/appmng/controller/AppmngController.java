package com.goodmit.hypergit.appmng.controller;

import com.goodmit.hypergit.appmng.dto.Gittb0001Dto;
import com.goodmit.hypergit.appmng.service.Gittb0001Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class AppmngController {
    private Gittb0001Service gittb0001Service;

    /* Appmng Page */
    @GetMapping("/admin/appmng")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

        List<Gittb0001Dto> gittb0001List = gittb0001Service.getGitta0001list(pageNum);
        Integer[] pageList = gittb0001Service.getPageList(pageNum);

        double  count = Double.valueOf(gittb0001Service.getGitta001Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gittb0001List", gittb0001List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "appmng/main.html";
    }

    @PostMapping("/admin/appmngpost")
    public String appmngpost(Principal principal, Gittb0001Dto gittb0001Dto) {

        LocalDateTime now = LocalDateTime.now();

        gittb0001Dto.setRgEn(principal.getName());
        gittb0001Dto.setAltEn(principal.getName());
        gittb0001Dto.setModifiedDate(now);

        System.out.println(now);

        gittb0001Service.savePost(gittb0001Dto);

        return "redirect:/admin/appmng";
    }


    @GetMapping("/admin/appmngdel")
    public String appmngdelete(@RequestParam(value="idx") String idx) {

        long no = 0;

        int beginIndex = idx.indexOf(",");

        if(beginIndex > 0){

            String[] ArraysStr = idx.split(",");

            for(String s : ArraysStr){
                no = Long.parseLong(s);
                gittb0001Service.deletePost(no);
            }

        }else{

            no = Long.parseLong(idx);
            gittb0001Service.deletePost(no);

        }
        return "redirect:/admin/appmng";
    }

}
