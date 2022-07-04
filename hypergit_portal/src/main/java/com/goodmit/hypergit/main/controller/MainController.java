package com.goodmit.hypergit.main.controller;

import com.goodmit.hypergit.board.dto.BoardDto;
import com.goodmit.hypergit.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {
    private BoardService boardService;

    /* Main Page */
    @GetMapping("/")
    public String main(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        double  count = Double.valueOf(boardService.getBoardCount());
        Integer postsTotalCount = (int) count;

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "main/user_main.html";
    }


    @GetMapping("/admin")
    public String adminmain(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        double  count = Double.valueOf(boardService.getBoardCount());
        Integer postsTotalCount = (int) count;

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "main/index.html";
    }

}
