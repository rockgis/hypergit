package com.goodmit.hypergit.logmng.controller;

import com.goodmit.hypergit.logmng.dto.Gitte0001Dto;
import com.goodmit.hypergit.logmng.service.Gitte0001Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class LogmngController {
    private Gitte0001Service gitte0001Service;

    /* Main Page */
    @GetMapping("/admin/logmng")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

        List<Gitte0001Dto> gitta0001List = gitte0001Service.getGitte0001list(pageNum);
        Integer[] pageList = gitte0001Service.getPageList(pageNum);

        double  count = Double.valueOf(gitte0001Service.getGitte001Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gitta0001List", gitta0001List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "logmng/main.html";
    }

}
