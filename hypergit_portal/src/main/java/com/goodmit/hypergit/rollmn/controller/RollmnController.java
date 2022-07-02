package com.goodmit.hypergit.rollmn.controller;

import com.goodmit.hypergit.rollmn.dto.Gittf0001Dto;
import com.goodmit.hypergit.rollmn.service.Gittf0001Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        model.addAttribute("gitta0001List", gittf0001List);
        model.addAttribute("pageList", pageList);

        return "rollmn/main.html";
    }

}
