package com.example.advanced.entity.board;

import com.example.advanced.repository.board.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void saveTest(){
        Board board = new Board();
        Reply reply1 = new Reply();
        Reply reply2 = new Reply();

        reply1.setReplyContent("테스트 댓글1");
        reply2.setReplyContent("테스트 댓글2");

        board.setBoardTitle("테스트 제목1");
        board.setBoardContent("테스트 내용1");

        board.getReplies().add(reply1);
        board.getReplies().add(reply2);

        boardRepository.save(board);
    }

    @Test
    public void findAllTest(){
        List<Board> boards = boardRepository.findAll();
        boards.forEach(board -> board.getReplies().forEach(reply -> log.info(reply.getReplyContent())));
    }
}

























