package com.example.expert.repository.board;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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
    public void findBoardAndLikeCountAllWithPaging(){
//        likeRepository.findBoardAndLikeCountAllWithPaging(PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id")))
//                .getContent().forEach(boardDTO -> log.info(boardDTO.toString()));
        log.info(boardRepository.findBoardAndLikeCountAllWithPaging(PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id")))
                .getContent().toString());
//        boardRepository.findAll().forEach(board -> log.info(board.toString()));

    }
}














