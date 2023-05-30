package com.example.intermediate.entity.employee;

import com.example.intermediate.repository.employee.DeveloperRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class DeveloperRepositoryTests {
    @Autowired
    private DeveloperRepository developerRepository;

    @Test
    public void saveTest(){
        for (int i=0; i<100; i++){
            developerRepository.save(
                Developer.builder()
                        .name("개발자-" + (i + 1))
                        .birth(LocalDate.of(1900+i, i % 12 + 1, i % 28 + 1))
                        .developingLevel(i % 5 + 1)
                        .career(i % 20 + 1)
                        .projectCount(i % 101)
                        .build()
            );
        }
    }

    @Test
    public void findAllWithPagingTest(){
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        Page<Developer> developersWithPaging = developerRepository.findAll(pageRequest);
        developersWithPaging.getContent().stream().map(Developer::toString).forEach(log::info); // 전달한 페이지의 정보 전체
        log.info(String.valueOf(developersWithPaging.getTotalPages())); // 전체 페이지 개수(마지막 페이지 번호)

//        만약 전체 게시글 정보가 한 페이지 안에 가져올 수 있을 때에는, List의 size()를 구한다.
//        하지만 2페이지 이상부터 count 쿼리가 작성된다.
        log.info(String.valueOf(developersWithPaging.getTotalElements())); // 전체 정보 개수
        log.info(String.valueOf(developersWithPaging.getNumber())); // 현재 페이지 번호
        log.info(String.valueOf(developersWithPaging.getNumberOfElements())); // 페이지 당 나오는 정보의 개수
        log.info(String.valueOf(developersWithPaging.hasNext())); // 다음 페이지의 여부
        log.info(String.valueOf(developersWithPaging.hasPrevious())); // 이전 페이지의 여부
        log.info(String.valueOf(developersWithPaging.isFirst())); // 현재 페이지가 첫 페이지인지 검사
        log.info(String.valueOf(developersWithPaging.isLast())); // 현재 페이지가 마지막 페이지인지 검사
        log.info(developersWithPaging.previousPageable().toString()); // 이전 페이지에 대한 정보
        log.info(developersWithPaging.previousOrFirstPageable().toString()); // 이전 페이지에 대한 정보, 만약 이전 페이지가 없으면 현재 페이지(1페이지)
        log.info(developersWithPaging.nextPageable().toString()); // 다음 페이지에 대한 정보
        log.info(developersWithPaging.nextOrLastPageable().toString()); // 다음 페이지에 대한 정보, 만약 다음 페이지가 없으면 현재 페이지(마지막 페이지)
    }
}
























