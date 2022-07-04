package com.goodmit.hypergit.logmng.controller;

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
public class LogmngController {
    private Gitta0001Service gitta0001Service;

    /* Main Page */
    @GetMapping("/admin/logmng")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

        List<Gitta0001Dto> gitta0001List = gitta0001Service.getGitta0001list(pageNum);
        Integer[] pageList = gitta0001Service.getPageList(pageNum);

        double  count = Double.valueOf(gitta0001Service.getGitta001Count());
        Integer postsTotalCount = (int) count;

        model.addAttribute("gitta0001List", gitta0001List);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "logmng/main.html";
    }

}
