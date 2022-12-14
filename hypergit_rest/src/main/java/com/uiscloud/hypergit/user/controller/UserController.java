package com.uiscloud.hypergit.user.controller;

import com.uiscloud.hypergit.appmng.dto.Gittb0001Dto;
import com.uiscloud.hypergit.user.dto.Gitta0001Dto;
import com.uiscloud.hypergit.user.service.Gitta0001Service;

import com.uiscloud.hypergit.user.dto.Gitta0002Dto;
import com.uiscloud.hypergit.user.service.Gitta0002Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
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

    @PostMapping("/admin/userpost")
    public String userpost(Principal principal, Gitta0001Dto gitta0001Dto) {

        LocalDateTime now = LocalDateTime.now();

        gitta0001Dto.setRgEn(principal.getName());

        // System.out.println(now);

        gitta0001Service.savePost(gitta0001Dto);

        return "redirect:/admin/userlist";
    }


    @GetMapping("/admin/userdel")
    public String userdelete(@RequestParam(value="idx") String idx) {

        long no = 0;

        int beginIndex = idx.indexOf(",");

        if(beginIndex > 0){

            String[] ArraysStr = idx.split(",");

            for(String s : ArraysStr){
                no = Long.parseLong(s);
                gitta0001Service.deletePost(no);
            }

        }else{

            no = Long.parseLong(idx);
            gitta0001Service.deletePost(no);

        }
        return "redirect:/admin/userlist";
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

    @PostMapping("/admin/publicpost")
    public String publicpost(Principal principal, Gitta0002Dto gitta0002Dto) {

        LocalDateTime now = LocalDateTime.now();

        gitta0002Dto.setAltEn(principal.getName());
        gitta0002Dto.setModifiedDate(now);
        // System.out.println(now);

        gitta0002Service.savePost(gitta0002Dto);

        return "redirect:/admin/publiclist";
    }


    @GetMapping("/admin/publicdel")
    public String publicdelete(@RequestParam(value="idx") String idx) {

        long no = 0;

        int beginIndex = idx.indexOf(",");

        if(beginIndex > 0){

            String[] ArraysStr = idx.split(",");

            for(String s : ArraysStr){
                no = Long.parseLong(s);
                gitta0002Service.deletePost(no);
            }

        }else{

            no = Long.parseLong(idx);
            gitta0002Service.deletePost(no);

        }
        return "redirect:/admin/publiclist";
    }
}
