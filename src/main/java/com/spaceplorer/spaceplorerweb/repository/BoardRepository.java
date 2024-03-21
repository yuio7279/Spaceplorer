package com.spaceplorer.spaceplorerweb.repository;

import com.spaceplorer.spaceplorerweb.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    //카테고리가 LAZY LOADING 이므로 N+1 문제를 막기 위해 즉시 로딩한다.
    //이를 위한 FETCH JOIN을 사용했다.

    //Pageable과 FETCH JOIN을 동시에 사용할 경우, hibernate에서 제한때문에 사용을 막는다. 이런 경우, countQuery 옵션을 통해 따로 생성해주어야 한다.
    @Query(value = "SELECT b FROM Board b " +
            "LEFT JOIN FETCH b.category c " +
            "WHERE c.categoryName = :categoryName",
            countQuery = "SELECT count(b) FROM Board b LEFT JOIN b.category c WHERE c.categoryName = :categoryName")
    Page<Board> findAllByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);


    @Query(value = "SELECT b FROM Board b WHERE b.id = :id AND b.user.socialId = :socialId")
    Optional<Board> findByIdAndSocialId(@Param("id") Long id, @Param("socialId") Long socialId);
}
