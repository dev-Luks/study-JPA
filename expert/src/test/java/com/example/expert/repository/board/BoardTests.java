package com.example.expert.repository.board;

import com.example.expert.entity.board.Board;
import com.example.expert.entity.board.Like;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class BoardTests {
    @Autowired
    private BoardDAO boardDAO;

    @Autowired
    private LikeDAO likeDAO;

    @Test
    public void saveTest(){
//        Board board = new Board();
        Board board = Board.builder()
                .boardTitle("테스트 제목1")
                .boardContent("테스트 내용1")
                .build();
        Like like1 = new Like();
        Like like2 = new Like();

//        board.setBoardTitle("테스트 제목1");
//        board.setBoardContent("테스트 내용1");
        board.addLike(like1);
        board.addLike(like2);

        boardDAO.save(board);
    }

    @Test
    public void findByIdTest(){
        boardDAO.findById(69L).ifPresent(board -> assertThat(board.getLikes()).hasSize(2));
    }

    @Test
    public void findAllTest(){
//        N:1관계에서 "1"쪽을 조회 시, join을 하면, "N"쪽의 개수만큼 결과 행이 중복되어 조회된다.
//        이 때 중복된 행을 없애기 위해서 distinct를 SELECT절에 사용한다.
        assertThat(boardDAO.findAll()).hasSize(1);
        log.info(boardDAO.findAll().toString());
    }

    @Test
    public void updateTest(){
        likeDAO.findById(70L).ifPresent(like -> like.getBoard().setBoardTitle("수정된 제목"));
    }

    @Test
    public void deleteTest(){
//        boardDAO.findById(69L).ifPresent(boardDAO::delete);
        boardDAO.findById(88L).ifPresent(board -> board.getLikes().remove(0));
    }
}













