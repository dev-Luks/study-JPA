package com.example.intermediate.entity.employee;

import com.example.intermediate.repository.employee.PlannerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.time.LocalDate;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class PlannerRepositoryTests {
    @Autowired
    private PlannerRepository plannerRepository;

    @Test
    public void saveTest(){
        for (int i=0; i<100; i++){
            plannerRepository.save(
                    Planner.builder()
                            .name("기획자-" + (i + 1))
                            .birth(LocalDate.of(1900+i, i % 12 + 1, i % 28 + 1))
                            .oa_level(i % 5 + 1)
                            .career(i % 20 + 1)
                            .clientCount(i % 101)
                            .build()
            );
        }
    }

    @Test
    public void findAllWithPagingTest(){
        PageRequest pageRequest = PageRequest.of(2, 10, Sort.by(Sort.Direction.DESC, "id"));
        Slice<Planner> plannersSlice = plannerRepository.findByClientCountNot(pageRequest, 0);
        plannersSlice.getContent().stream().map(Planner::toString).forEach(log::info); // 전달한 페이지의 정보 전체
//        Slice에는 전체 페이지 개수와 전체 정보 개수는 가져오지 않는다. 따라서 count 쿼리가 작성되지도 않는다.
//        Slice를 사용하는 이유는 페이징처리가 없는 "더보기", "무한 스크롤" 등의 작업에서 사용된다.
//        Page는 전체 개수 조회 쿼리가 나가지만, Slice는 전체 개수 조회 쿼리가 나가지 않는다.
//        둘 중 알맞는 목적으로 리턴타입을 설정해야 성능을 최적화할 수 있다.
//        log.info(String.valueOf(plannersSlice.getTotalPages())); // 전체 페이지 개수(마지막 페이지 번호)
//        log.info(String.valueOf(plannersSlice.getTotalElements())); // 전체 정보 개수
        log.info(String.valueOf(plannersSlice.getNumber())); // 현재 페이지 번호
        log.info(String.valueOf(plannersSlice.getNumberOfElements())); // 페이지 당 나오는 정보의 개수
        log.info(String.valueOf(plannersSlice.hasNext())); // 다음 페이지의 여부
        log.info(String.valueOf(plannersSlice.hasPrevious())); // 이전 페이지의 여부
        log.info(String.valueOf(plannersSlice.isFirst())); // 현재 페이지가 첫 페이지인지 검사
        log.info(String.valueOf(plannersSlice.isLast())); // 현재 페이지가 마지막 페이지인지 검사
        log.info(plannersSlice.previousPageable().toString()); // 이전 페이지에 대한 정보
        log.info(plannersSlice.previousOrFirstPageable().toString()); // 이전 페이지에 대한 정보, 만약 이전 페이지가 없으면 현재 페이지(1페이지)
        log.info(plannersSlice.nextPageable().toString()); // 다음 페이지에 대한 정보
        log.info(plannersSlice.nextOrLastPageable().toString()); // 다음 페이지에 대한 정보, 만약 다음 페이지가 없으면 현재 페이지(마지막 페이지)
    }
}






















