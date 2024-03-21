package com.spaceplorer.spaceplorerweb.service;

import com.spaceplorer.spaceplorerweb.domain.*;
import com.spaceplorer.spaceplorerweb.dto.request.BoardRequestDto;
import com.spaceplorer.spaceplorerweb.dto.response.BoardDetailResponseDto;
import com.spaceplorer.spaceplorerweb.dto.response.BoardResponseDto;
import com.spaceplorer.spaceplorerweb.repository.*;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    //게시글 리스트 조회 20개씩
    public Page<BoardResponseDto> getBoardListByCategoryName(String categoryName, int page, int pageSize) {
        Pageable pageable = PageRequest.of(
                page, pageSize, Sort.by(Sort.Direction.DESC, "id")); // 페이지 번호, 사이즈, 정렬 기준

        Page<Board> boardPage = boardRepository.findAllByCategoryName(categoryName, pageable);
        if(boardPage.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_BOARD);
        }

        return boardPage.map(BoardResponseDto::new);
    }

    //게시글 1개 조회, 게시글 클릭 시
    //LAZY LOADING시, Transactional로 처리를 해야 안전하게 로딩한다. + incrementViewsAsync
    @Transactional(readOnly = true)
    public BoardDetailResponseDto getBoardById(Long id) {
        Optional<Board> entity_ = boardRepository.findById(id);
        if(entity_.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_BOARD);
        }
        Board entity = entity_.get();
        //조회 수 증가
        incrementViewsAsync(entity);
        BoardDetailResponseDto boardResponseDto = new BoardDetailResponseDto(entity);
        log.debug("[Created BoardDetailResponseDto:{}]",boardResponseDto);

        return boardResponseDto;
    }

    //게시글 생성
    @Transactional
    public Long createBoard(BoardRequestDto boardRequestDto, Long socialId) {
        log.debug("[Creating board... Request title:{} category:{} city:{}]"
                , boardRequestDto.getTitle(),
                boardRequestDto.getCategoryName(),
                boardRequestDto.getCityName());

        City city = cityRepository.findByPlanetAliasAndCityName(boardRequestDto.getCategoryName(), boardRequestDto.getCityName())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, FAIL_TO_CREATE_BOARD + " - city not found"));

        User user = userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, FAIL_TO_CREATE_BOARD + " - user not found"));

        Category category = categoryRepository.findByCategoryName(boardRequestDto.getCategoryName())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, FAIL_TO_CREATE_BOARD + " - Category not found"));

        Board board = new Board(boardRequestDto.getTitle(), boardRequestDto.getContent(), city, user, category);
        Board savedBoard = boardRepository.save(board);


        if (boardRequestDto.getImageUrlList() == null || boardRequestDto.getImageUrlList().isEmpty()) {
            log.debug("[Created board id:{}]",savedBoard.getId());
            return savedBoard.getId();
        }else{
            //이미지가 있을 경우
            for (String imageUrl : boardRequestDto.getImageUrlList()) {
                if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                    Image image = new Image(imageUrl, savedBoard);
                    savedBoard.addImage(image);
                }
            }

        }
        log.debug("[Created board id:{}]",savedBoard.getId());
        return savedBoard.getId();
    }


    //게시글 삭제
    public String deleteBoardById(Long id, Long socialId) {
        Optional<Board> entity = boardRepository.findByIdAndSocialId(id, socialId);
        if(entity.isEmpty()){
            throw new ResponseStatusException(NOT_FOUND, NOT_FOUND_BOARD);
        }
        boardRepository.delete(entity.get());
        log.info("[Delete board complete:{}]",id);
        return entity.get().getCategory().getCategoryName();
    }


    //조회수 증가, 더티체킹
    @Async
    public void incrementViewsAsync(Board board) {
        board.incrementViews();
        boardRepository.save(board);
    }

    //게시글 추천
    @Transactional
    public void likeBoard(Long boardId, Long socialId) {
        Board boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, NOT_FOUND_BOARD));
        User userEntity = userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));

        //중복 추천 방지
        Optional<Like> likeEntity = likeRepository.findByBoardIdAndSocialId(boardId, socialId);
        if(likeEntity.isPresent()){
            return;
        }
        likeRepository.save(new Like(userEntity, boardEntity));

        // like 수를 계산
        int likeCount = likeRepository.countByBoardId(boardId);
        boardEntity.setLikeCount(likeCount);
        boardRepository.save(boardEntity);
    }


    //게시글 추천취소
    @Transactional
    public void likeCancelBoard(Long boardId, Long socialId) {
        Optional<Like> like = likeRepository.findByBoardIdAndSocialId(boardId, socialId);
        if(like.isEmpty()){
            return;
        }

        Board boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, NOT_FOUND_BOARD));

        likeRepository.delete(like.get());

        // Like 삭제 후 변경된 like 수를 업데이트
        int likeCount = likeRepository.countByBoardId(boardId);
        boardEntity.setLikeCount(likeCount);

        boardRepository.save(boardEntity);
    }
}
