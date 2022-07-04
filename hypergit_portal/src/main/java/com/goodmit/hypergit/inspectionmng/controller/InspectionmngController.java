package com.goodmit.hypergit.inspectionmng.controller;

import com.goodmit.hypergit.board.dto.BoardDto;
import com.goodmit.hypergit.board.service.BoardService;
import com.goodmit.hypergit.user.dto.Gitta0001Dto;
import com.goodmit.hypergit.user.service.Gitta0001Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class InspectionmngController {


    private Gitta0001Service gitta0001Service;

    /* 인증감사 */
    @GetMapping("/admin/inspection")
    public String inspection(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<Gitta0001Dto> gitta0001List = gitta0001Service.getGitta0001list(pageNum);
        Integer[] pageList = gitta0001Service.getPageList(pageNum);

        double  count = Double.valueOf(gitta0001Service.getGitta001Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gitta0001List", gitta0001List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "inspectionmng/main.html";
    }

    /* 인증감사 */
    @GetMapping("/admin/inspectionmng")
    public String inspectionmng(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<Gitta0001Dto> gitta0001List = gitta0001Service.getGitta0001list(pageNum);
        Integer[] pageList = gitta0001Service.getPageList(pageNum);

        double  count = Double.valueOf(gitta0001Service.getGitta001Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gitta0001List", gitta0001List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "inspectionmng/main.html";
    }

    /* 계정감사 */
    @GetMapping("/admin/accountaudit")
    public String accountaudit(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<Gitta0001Dto> gitta0001List = gitta0001Service.getGitta0001list(pageNum);
        Integer[] pageList = gitta0001Service.getPageList(pageNum);

        double  count = Double.valueOf(gitta0001Service.getGitta001Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gitta0001List", gitta0001List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "accountaudit/main.html";
    }

    /* 권한감사 */
    @GetMapping("/admin/authorityaudit")
    public String authorityaudit(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<Gitta0001Dto> gitta0001List = gitta0001Service.getGitta0001list(pageNum);
        Integer[] pageList = gitta0001Service.getPageList(pageNum);

        double  count = Double.valueOf(gitta0001Service.getGitta001Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gitta0001List", gitta0001List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "authorityaudit/main.html";
    }

    /* 앱점속감사 */
    @GetMapping("/admin/appstoreaudit")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<Gitta0001Dto> gitta0001List = gitta0001Service.getGitta0001list(pageNum);
        Integer[] pageList = gitta0001Service.getPageList(pageNum);

        double  count = Double.valueOf(gitta0001Service.getGitta001Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gitta0001List", gitta0001List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "appstoreaudit/main.html";
    }

}
