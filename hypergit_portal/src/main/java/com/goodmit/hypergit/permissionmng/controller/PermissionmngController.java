package com.goodmit.hypergit.permissionmng.controller;

import com.goodmit.hypergit.permissionmng.dto.Gittc0001Dto;
import com.goodmit.hypergit.permissionmng.service.Gittc0001Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class PermissionmngController {

    private Gittc0001Service gittd0001Service;

    /* Main Page */
    @GetMapping("/admin/permissionmng")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<Gittc0001Dto> gittd0001List = gittd0001Service.getGittc0001list(pageNum);
        Integer[] pageList = gittd0001Service.getPageList(pageNum);

        double  count = Double.valueOf(gittd0001Service.getGittc001Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gittd0001List", gittd0001List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "permissionmng/main.html";
    }

    @GetMapping("/admin/permissionmng/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        //List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        return "permissionmng/main.html";
    }

    //<!-- Sendmail 기능 -->

}
