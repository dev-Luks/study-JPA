package com.example.expert.repository.board;

import com.example.expert.entity.board.Board;
import com.example.expert.entity.board.Like;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class BoardRepositoryTests {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void saveTest(){
        for (int i=0; i<100; i++){
            Board board = Board.builder()
                    .boardTitle("테스트 제목" + (i + 1))
                    .boardContent("테스트 내용" + (i + 1))
                    .build();
            for (int j=0; j<new Random().nextInt(100); j++){
                Like like = new Like();
                board.addLike(like);
            }
            boardRepository.save(board);
        }
    }

    @Test
    public void findBoardAndLikeCountAllWithPaging(){
//        likeRepository.findBoardAndLikeCountAllWithPaging(PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id")))
//                .getContent().forEach(boardDTO -> log.info(boardDTO.toString()));
        int size = 5;
        int page = 1;
        log.info(boardRepository.findBoardAndLikeCountAllWithPaging(PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "id")))
                .getContent().toString());
//        boardRepository.findAll().forEach(board -> log.info(board.toString()));

    }

    @Test
    public void findPrevBoardNextBoardByIdTest(){
        //        상세보기에서 이전글과 다음글
        //        상세보기 시 N개의 정보 전달
        //        현재 -N, 현재 +N
        log.info(boardRepository.findPrevBoardNextBoardById(1522L, 3).toString());
        log.info(boardRepository.findPrevBoardById(1522L, 3).toString());
        log.info(boardRepository.findNextBoardById(1522L, 3).toString());
    }
}














