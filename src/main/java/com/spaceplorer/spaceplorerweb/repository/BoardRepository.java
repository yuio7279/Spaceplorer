package com.spaceplorer.spaceplorerweb.repository;

import com.spaceplorer.spaceplorerweb.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
