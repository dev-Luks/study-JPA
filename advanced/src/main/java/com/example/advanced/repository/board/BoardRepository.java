package com.example.advanced.repository.board;

import com.example.advanced.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board , Long> {

}
