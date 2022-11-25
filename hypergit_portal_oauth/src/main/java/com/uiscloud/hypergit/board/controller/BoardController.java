package com.uiscloud.hypergit.board.controller;

import com.uiscloud.hypergit.board.dto.BoardDto;
import com.uiscloud.hypergit.board.service.BoardService;
import com.uiscloud.hypergit.permissionmng.dto.Gittc0001Dto;
import com.uiscloud.hypergit.permissionmng.service.Gittc0001Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;

    private Gittc0001Service gittc0001Service;

    /* 게시글 목록 */
    @GetMapping("/user/list")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        // 총 게시글 갯수
        double  count = Double.valueOf(boardService.getBoardCount());
        Integer postsTotalCount = (int) count;

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("postsTotalCount", postsTotalCount);

        return "board/list.html";
    }


    /* 게시글 상세 */
    @GetMapping("/user/post/{no}")
    public String detail(Principal principal,  @PathVariable("no") Long no, Model model) {

        String username = principal.getName();  //권한에 따라 URL 가지고 와야 됨

        List<Gittc0001Dto> gittc0001List = gittc0001Service.getGittc0001listUser(username);

        model.addAttribute("gittc0001List", gittc0001List);


        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "board/detail.html";
    }

    /* 게시글 쓰기 */
    @GetMapping("/user/post")
    public String write() {
        return "board/write.html";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);

        return "redirect:/user/list";
    }

    /* 게시글 수정 */
    @GetMapping("/user/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "board/update.html";
    }

    @PutMapping("/user/post/edit/{no}")
    public String update(BoardDto boardDTO) {
        boardService.savePost(boardDTO);

        return "redirect:/user/list";
    }

    /* 게시글 삭제 */
    @DeleteMapping("/user/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);

        return "redirect:/user/list";
    }

    @GetMapping("/user/board/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        model.addAttribute("boardList", boardDtoList);

        return "board/list.html";
    }
}
