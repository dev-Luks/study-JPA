package com.example.expert.repository.board;

import com.example.expert.entity.board.Board;
import com.example.expert.entity.board.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
//    게시글 전체 조회 및 좋아요 개수
    @Query(value = "select new com.example.expert.entity.board.BoardDTO(b.id, b.boardTitle, b.boardContent, size(b.likes)) from Board b left join b.likes l"
            , countQuery = "select count(b) from Board b")
    public Page<BoardDTO> findBoardAndLikeCountAllWithPaging(Pageable pageable);

    @Query("select b from Board b left join b.likes l")
    public List<Board> findAll();
}
