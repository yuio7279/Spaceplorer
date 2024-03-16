package com.spaceplorer.spaceplorerweb.service;

import com.spaceplorer.spaceplorerweb.domain.Board;
import com.spaceplorer.spaceplorerweb.domain.City;
import com.spaceplorer.spaceplorerweb.dto.request.BoardRequestDto;
import com.spaceplorer.spaceplorerweb.dto.response.BoardResponseDto;
import com.spaceplorer.spaceplorerweb.repository.BoardRepository;
import com.spaceplorer.spaceplorerweb.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.spaceplorer.spaceplorerweb.common.Messages.FAIL_TO_CREATE_BOARD;
import static com.spaceplorer.spaceplorerweb.common.Messages.NOT_FOUND_BOARD;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CityRepository cityRepository;


    //게시글 리스트 조회
    public List<BoardResponseDto> getBoardList() {
        List<Board> boardEntityList = boardRepository.findAll();
        if(boardEntityList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_BOARD);
        }
        List<BoardResponseDto> responseDto = boardEntityList.stream().map(BoardResponseDto::new).toList();
        log.debug("[Found BoardResponseDto:{}]",responseDto);

        return responseDto;
    }

    //게시글 1개 조회, 게시글 클릭 시
    public BoardResponseDto getBoardById(Long id) {
        Optional<Board> entity_ = boardRepository.findById(id);
        if(entity_.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_BOARD);
        }
        Board entity = entity_.get();
        //조회 수 증가
        incrementViewsAsync(entity);
        BoardResponseDto boardResponseDto = new BoardResponseDto(entity);
        log.debug("[Found BoardResponseDto:{}]",boardResponseDto);

        return boardResponseDto;
    }

    //게시글 생성
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {
        log.debug("[Board Request title:{} category:{} city:{}]"
                ,boardRequestDto.getTitle(),
                boardRequestDto.getCategoryName(),
                boardRequestDto.getCityName());


        Optional<City> city = cityRepository.findByPlanetAliasAndCityName(boardRequestDto.getCategoryName(), boardRequestDto.getCityName());
        if(city.isEmpty()){
                throw new ResponseStatusException(BAD_REQUEST, FAIL_TO_CREATE_BOARD);
        }

        //게시글 생성
        Board board = new Board(boardRequestDto.getTitle(), boardRequestDto.getContent(), city.get());
        Board entity = boardRepository.save(board);

        //dto
        return new BoardResponseDto(entity);

    }

    //게시글 삭제
    public void deleteBoardById(Long id) {
        Optional<Board> entity_ = boardRepository.findById(id);
        if(entity_.isEmpty()){
            throw new ResponseStatusException(NOT_FOUND, NOT_FOUND_BOARD);
        }
        boardRepository.delete(entity_.get());
        log.info("[Delete board complete:{}]",id);
    }


    //조회수 증가
    @Async
    //동시성 제어 기법을 공부하기
    public void incrementViewsAsync(Board board) {
        board.incrementViews();
        boardRepository.save(board);
    }


}
