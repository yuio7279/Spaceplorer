package com.spaceplorer.spaceplorerweb.controller;

import com.spaceplorer.spaceplorerweb.dto.request.BoardRequestDto;
import com.spaceplorer.spaceplorerweb.dto.response.BoardResponseDto;
import com.spaceplorer.spaceplorerweb.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 게시판은 인터렉티브할 필요가 없기때문에, ThymeLeaf를 사용하여 SSR로 개발해보기
 * R : 모든 방문자 가능
 * CUD : 로그인 한 유저만 사능
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    //전체 게시판 리스트를 출력
    @GetMapping()
    public String getBoardList(Model model){
        model.addAttribute("boardList",boardService.getBoardList());
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
    public String createBoard(@RequestBody BoardRequestDto boardRequestDto){

        BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto);
        return "redirect:/boards/"+boardResponseDto.getId();
    }

    //게시글 삭제
    @DeleteMapping("/{board_id}")
    public String deleteBoardById(@PathVariable("board_id") Long id){
        boardService.deleteBoardById(id);
        return "redirect:/boards";
    }



}
