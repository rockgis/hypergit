package com.goodmit.hypergit.user.controller;

import com.goodmit.hypergit.user.dto.Gitta0001Dto;
import com.goodmit.hypergit.user.service.Gitta0001Service;

import com.goodmit.hypergit.user.dto.Gitta0002Dto;
import com.goodmit.hypergit.user.service.Gitta0002Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private Gitta0001Service gitta0001Service;

    private Gitta0002Service gitta0002Service;

    /* 게시글 목록 */

    @GetMapping("/admin/userlist")
    public String index(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<Gitta0001Dto> gitta0001List = gitta0001Service.getGitta0001list(pageNum);
        Integer[] pageList = gitta0001Service.getPageList(pageNum);

        double  count = Double.valueOf(gitta0001Service.getGitta001Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gitta0001List", gitta0001List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);



        return "admin/userlist";
    }


    @GetMapping("/admin/usersearch")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        List<Gitta0001Dto> gitta0001DtoList = gitta0001Service.searchPosts(keyword);

        model.addAttribute("gitta0001List", gitta0001DtoList);

        return "admin/userlist";
    }


    @GetMapping("/admin/publiclist")
    public String publiclist(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<Gitta0002Dto> gitta0002List = gitta0002Service.getGitta0002list(pageNum);
        Integer[] pageList = gitta0002Service.getPageList(pageNum);

        double  count = Double.valueOf(gitta0002Service.getGitta002Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gitta0002List", gitta0002List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);



        return "admin/publiclist";
    }
}
