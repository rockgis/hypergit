package com.goodmit.hypergit.user.controller;

import com.goodmit.hypergit.user.dto.Gitta0001Dto;
import com.goodmit.hypergit.user.service.Gitta0001Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private Gitta0001Service gitta0001Service;

    /* 게시글 목록 */

    @GetMapping("/admin/userlist")
    public String index(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<Gitta0001Dto> gitta0001List = gitta0001Service.getGitta0001list(pageNum);
        Integer[] pageList = gitta0001Service.getPageList(pageNum);

        model.addAttribute("gitta0001List", gitta0001List);
        model.addAttribute("pageList", pageList);
        return "admin/userlist";
    }


    @GetMapping("/admin/usersearch")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        List<Gitta0001Dto> gitta0001DtoList = gitta0001Service.searchPosts(keyword);

        model.addAttribute("gitta0001List", gitta0001DtoList);

        return "admin/userlist";
    }
}
