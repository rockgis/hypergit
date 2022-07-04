package com.goodmit.hypergit.ipusenm.controller;

import com.goodmit.hypergit.ipusenm.dto.Gittf0002Dto;
import com.goodmit.hypergit.ipusenm.service.Gittf0002Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

}
