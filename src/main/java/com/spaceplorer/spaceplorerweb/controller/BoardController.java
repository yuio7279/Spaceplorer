package com.spaceplorer.spaceplorerweb.controller;

import com.spaceplorer.spaceplorerweb.dto.request.BoardRequestDto;
import com.spaceplorer.spaceplorerweb.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 게시판은 인터렉티브할 필요가 없기때문에, ThymeLeaf를 사용하여 SSR로 개발해보기
 * R : 모든 방문자 가능
 * CUD : 로그인 한 유저만 사능
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    //카테고리별 게시판 리스트를 출력
    @GetMapping()
    public String getBoardListByCategoryName(
            @RequestParam("category_name") String categoryName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value= "pageSize", defaultValue = "20") int pageSize,
                               Model model){
        model.addAttribute("boardPage", boardService.getBoardListByCategoryName(categoryName, page, pageSize));
        return "board_list";
    }

    //게시글 1개 조회, 게시글 클릭 시 상세 게시글
    @GetMapping("/{board_id}")
    public String getBoardById(
            @PathVariable("board_id") Long id,
            Model model){

        model.addAttribute("board",boardService.getBoardById(id));
        return "board_detail";
    }

    //게시글 등록
    @PostMapping
    public String createBoard(@RequestBody BoardRequestDto boardRequestDto,
                              @AuthenticationPrincipal String username,
                              RedirectAttributes redirectAttributes){
        log.info("[username:{}]",username);
        Long boardId= boardService.createBoard(boardRequestDto, Long.valueOf(username));


        return "redirect:/boards/"+boardId;
    }

    //게시글 삭제
    @DeleteMapping("/{board_id}")
    public String deleteBoardById(
            @PathVariable("board_id") Long id,
            @AuthenticationPrincipal String username){
        String categoryName = boardService.deleteBoardById(id, Long.valueOf(username));
        String encode = URLEncoder.encode(categoryName, StandardCharsets.UTF_8);
        return "redirect:/boards?category_name="+encode;
    }

    //추천하기
    @PostMapping("/{board_id}/like")
    public String likeBoard(
            @PathVariable("board_id") Long id,
            @AuthenticationPrincipal String username){
        boardService.likeBoard(id, Long.valueOf(username));
        return "redirect:/boards/"+id;
    }
    //추천취소
    @DeleteMapping("/{board_id}/like")
    public String likeCancelBoard(
            @PathVariable("board_id") Long id,
            @AuthenticationPrincipal String username){
        boardService.likeCancelBoard(id, Long.valueOf(username));
        return "redirect:/boards/"+id;
    }

}
