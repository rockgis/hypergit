package com.uiscloud.hypergit.modelmng.controller;

import com.uiscloud.hypergit.modelmng.dto.Gittc0001Dto;
import com.uiscloud.hypergit.modelmng.service.Gittc0001Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class ModelmngController {

    private Gittc0001Service gittc0001Service;

    /* Main Page */
    @GetMapping("/admin/modelmng")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<Gittc0001Dto> gittc0001List = gittc0001Service.getGittc0001list(pageNum);
        Integer[] pageList = gittc0001Service.getPageList(pageNum);

        double  count = Double.valueOf(gittc0001Service.getGittc001Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gittc0001List", gittc0001List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "modelmng/main.html";
    }

    @GetMapping("/admin/modelmng/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        //List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        return "modelmng/main.html";
    }

    @PostMapping("/admin/modelpost")
    public String modelpost(Principal principal, Gittc0001Dto gittc0001Dto) {

        LocalDateTime now = LocalDateTime.now();
        gittc0001Dto.setAltEn(principal.getName());

        gittc0001Dto.setModifiedDate(now);

        // System.out.println(now);

        gittc0001Service.savePost(gittc0001Dto);

        return "redirect:/admin/modelmng";
    }


    @GetMapping("/admin/modeldel")
    public String modeldelete(@RequestParam(value="idx") String idx) {

        long no = 0;

        int beginIndex = idx.indexOf(",");

        if(beginIndex > 0){

            String[] ArraysStr = idx.split(",");

            for(String s : ArraysStr){
                no = Long.parseLong(s);
                gittc0001Service.deletePost(no);
            }

        }else{

            no = Long.parseLong(idx);
            gittc0001Service.deletePost(no);

        }
        return "redirect:/admin/modelmng";
    }

}
