package com.spaceplorer.spaceplorerweb.repository;

import com.spaceplorer.spaceplorerweb.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT l FROM Like l WHERE l.board.id = :boardId AND l.user.socialId = :socialId")
    Optional<Like> findByBoardIdAndSocialId(@Param("boardId") Long boardId, @Param("socialId") Long socialId);

    
    Integer countByBoardId(Long boardId);
}
