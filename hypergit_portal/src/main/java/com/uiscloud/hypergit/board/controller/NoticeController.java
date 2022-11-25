package com.uiscloud.hypergit.board.controller;

import com.uiscloud.hypergit.board.dto.BoardDto;
import com.uiscloud.hypergit.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class NoticeController {
    private BoardService boardService;

    /* 게시글 목록 */
    /* Main Page */
    @GetMapping("/admin/notice")
    public String notice(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        // 총 게시글 갯수
        double  count = Double.valueOf(boardService.getBoardCount());
        Integer postsTotalCount = (int) count;

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "notice/main.html";
    }


    /* 게시글 상세 */
    @GetMapping("/admin/post/{no}")
    public String admindetail(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "notice/detail.html";
    }

    /* 게시글 쓰기 */
    @GetMapping("/admin/post")
    public String adminwrite() {
        return "board/write.html";
    }

    @PostMapping("/admin/post")
    public String adminwrite(Principal principal, BoardDto boardDto) {

        boardDto.setWriter(principal.getName());

        boardService.savePost(boardDto);

        return "redirect:/admin/notice";
    }

    /* 게시글 수정 */
    @GetMapping("/admin/post/edit/{no}")
    public String adminedit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "notice/update.html";
    }

    @PutMapping("/admin/post/edit/{no}")
    public String adminupdate(BoardDto boardDTO) {
        boardService.savePost(boardDTO);

        return "redirect:/admin/notice";
    }

    /* 게시글 삭제 */
    @DeleteMapping("/admin/post/{no}")
    public String admindelete(@PathVariable("no") Long no) {
        boardService.deletePost(no);

        return "redirect:/admin/notice";
    }

    @GetMapping("/admin/notice/search")
    public String adminsearch(@RequestParam(value="keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        model.addAttribute("boardList", boardDtoList);

        return "notice/main.html";
    }
}
