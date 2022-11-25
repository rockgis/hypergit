package com.uiscloud.hypergit.ipusenm.controller;

import com.uiscloud.hypergit.ipusenm.dto.Gittf0002Dto;
import com.uiscloud.hypergit.ipusenm.service.Gittf0002Service;
import com.uiscloud.hypergit.rollmn.dto.Gittf0001Dto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class IpusenmController {
    private Gittf0002Service gittf0002Service;

    /* Main Page */
    @GetMapping("/admin/ipusenm")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

        List<Gittf0002Dto> gittf0002List = gittf0002Service.getGittf0002list(pageNum);
        Integer[] pageList = gittf0002Service.getPageList(pageNum);

        double  count = Double.valueOf(gittf0002Service.getGitta002Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gittf0002List", gittf0002List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);


        return "ipusenm/main.html";
    }

    @PostMapping("/admin/ipusenmpost")
    public String ipusenmpost(Principal principal, Gittf0002Dto gittf0002Dto) {

        LocalDateTime now = LocalDateTime.now();

        gittf0002Dto.setRgEn(principal.getName());
        gittf0002Dto.setAltEn(principal.getName());

        // System.out.println(now);

        gittf0002Service.savePost(gittf0002Dto);

        return "redirect:/admin/ipusenm";
    }

    @GetMapping("/admin/ipusenmdel")
    public String ipusenmdelete(@RequestParam(value="idx") String idx) {

        long no = 0;

        int beginIndex = idx.indexOf(",");

        if(beginIndex > 0){

            String[] ArraysStr = idx.split(",");

            for(String s : ArraysStr){
                no = Long.parseLong(s);
                gittf0002Service.deletePost(no);
            }

        }else{

            no = Long.parseLong(idx);
            gittf0002Service.deletePost(no);

        }
        return "redirect:/admin/ipusenm";
    }

}
