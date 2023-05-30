package com.example.expert.repository.board;

import com.example.expert.entity.board.BoardDTO;
import com.example.expert.entity.board.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
