package com.goodmit.hypergit.policymng.controller;

import com.goodmit.hypergit.policymng.dto.Gittf0003Dto;
import com.goodmit.hypergit.policymng.service.Gittf0003Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class PolicymngController {

    private Gittf0003Service gittf0003Service;

    /* Main Page */
    @GetMapping("/admin/policymng")
    public String list(Model model) {
        // 총 게시글 갯수
        double  count = Double.valueOf(gittf0003Service.getgittf0003Count());


        Gittf0003Dto gittf0003Dto = gittf0003Service.getPost(Long.valueOf((long) count));

        model.addAttribute("gittf0003Dto", gittf0003Dto);


        return "policymng/main.html";
    }

    @PutMapping("/admin/policymng/edit/{no}")
    public String policymngupdate(@PathVariable("no") Long no, Gittf0003Dto gittf0003Dto) {

        gittf0003Dto.setId(no);
        gittf0003Dto.setUrCk(5);
        gittf0003Dto.setUrDay("15");
        gittf0003Dto.setSamlUrl("https://wso2.uiscloud.com:9445/");
        gittf0003Dto.setSamlPass("Basic YWRtaW46YWRtaW4=");


        gittf0003Service.savePost(gittf0003Dto);

        return "redirect:/admin/policymng";
    }

}
