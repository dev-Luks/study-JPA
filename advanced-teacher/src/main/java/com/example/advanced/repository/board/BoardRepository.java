package com.example.advanced.repository.board;

import com.example.advanced.entity.board.Board;
import com.example.advanced.entity.board.Reply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
//    join하고 싶은 연관객체를 작성해주면 한 방쿼리가 실행된다.
    @EntityGraph(attributePaths = {"replies"})
    public List<Board> findAll();
}
