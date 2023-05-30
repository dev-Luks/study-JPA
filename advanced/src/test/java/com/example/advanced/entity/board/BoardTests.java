package com.example.advanced.entity.board;

import com.example.advanced.repository.board.BoardDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class BoardTests {
    @Autowired
    private BoardDAO boardDAO;

    @Test
    public void saveTest(){
        Board board = new Board();
        Reply reply1 = new Reply();
        Reply reply2 = new Reply();

        reply1.setReplyContent("테스트 댓글19");
        reply2.setReplyContent("테스트 댓글20");

//        참조 엔티티 모두 영속 상태로 전환해야 한다.
//        하지만 cascade에 CascadeType.PERSIST를 설정하면,
//        자동으로 참조 엔티티까지 영속 상태로 전환되기 때문에
//        아래의 코드를 작성할 필요 없다.
//        boardDAO.save(reply1);
//        boardDAO.save(reply2);

        board.setBoardTitle("테스트 제목11");
        board.setBoardContent("테스트 내용11");

        board.getReplies().add(reply1);
        board.getReplies().add(reply2);

//        엔티티를 영속상태로 변경하고 참조 엔티티까지 영속상태로 변경되었다면,
//        현재 1차 캐시에는 엔티티 및 참조 엔티티 모두 등록되어 있는 상태이다.
        boardDAO.save(board);

//        영속 상태인 엔티티를 조회하고, 참조 엔티티까지 조회해도 SELECT 쿼리는 실행되지 않는다(1차 캐시에 모두 등록되어 있기 떄문이다)
//        ※ 영속상태에 등록되어 있는 Id인지 잘 확인하고 단위 테스트를 진행해야 한다.
//        boardDAO.findById(26L).get().getReplies().get(0); // 엔티티를 통해 참조 엔티티를 접근(SELECT 실행되지 않음)
        boardDAO.findReplyById(33L); // 직접 참조 엔티티를 접근(SELECT 실행되지 않음)

//        ※ Hibernate의 쓰기 지연 저장소 쿼리 실행 순서
//        find, update, insert, remove 순서로 실행된다.
//        find를 하기 전에 대상 엔티티 쿼리가 있다면, flush 후 find가 진행된다.
    }

    @Test
    public void findByIdTest(){
//        boardDAO.findById(3L).map(Board::toString).ifPresent(log::info);
//        boardDAO.findById(3L).ifPresent(board -> board.getReplies().get(0));
//        boardDAO.findById(3L).map(Board::toString).ifPresent(log::info);
        boardDAO.findById(23L).map(Board::toString).ifPresent(log::info);
    }

    @Test
    public void findAllTest(){
        boardDAO.findAll().stream().map(Board::toString).forEach(log::info);
    }

    @Test
    public void updateTest(){
//        boardDAO.findById(3L).ifPresent(board -> board.setBoardTitle("수정된 제목"));
        boardDAO.findById(3L).ifPresent(board -> board.getReplies().get(0).setReplyContent("수정된 댓글 내용1"));
    }

    @Test
    public void deleteTest(){
        boardDAO.findById(3L).ifPresent(boardDAO::delete);
    }
}
