//package com.example.myoceanproject.dtoTest;
//
//import com.example.myoceanproject.domain.AskDTO;
//import com.example.myoceanproject.domain.QAskDTO;
//import com.example.myoceanproject.entity.Ask;
//import com.example.myoceanproject.entity.User;
//import com.example.myoceanproject.repository.ask.AskRepository;
//import com.example.myoceanproject.repository.UserRepository;
//import com.example.myoceanproject.type.AskCategory;
//import com.example.myoceanproject.type.AskStatus;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.example.myoceanproject.entity.QAsk.ask;
//
//@SpringBootTest
//@Slf4j
//@Transactional
//@Rollback(false)
//public class AskTest {
//    @Autowired
//    private JPAQueryFactory jpaQueryFactory;
//    @Autowired
//    private AskRepository askRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void saveAskTest() {
////      시나리오: 해당 유저(3L)가 문의하기 내용을 남긴다.
//
//        for (int i = 0; i < 30; i++) {
////      userRepository 인터페이스 구현체 hibernate의 findById메서드를 이용해서
////      다른 유저를 검색한다.
//            Optional<User> user = userRepository.findById(26L);
//
////      화면에서 문의하기 작성내용을 입력받기 위해 AskDTO 객체 생성
//            AskDTO askDTO = new AskDTO();
//
////      화면에서 문의하기 작성 내용을 입력받는다.
//            askDTO.setAskCategory(AskCategory.QUESTINFO);
//            askDTO.setAskStatus(AskStatus.WAITING);
//            askDTO.setAskTitle("퀘스트 문의하기 제목");
//            askDTO.setAskContent("퀘스트 문의하기 내용");
//
////      추가 저장을 위해 toentity메서드를 통해 Ask객체에 저장
//            Ask ask1 = askDTO.toEntity();
//
////      userRepository 인터페이스 구현체 hibernate의 findbyid메서드로 유저를 검색후 추가
//            ask1.changeUser(user.get());
//
////      문의하기 테이블에 해당 내용 저장
//            askRepository.save(ask1);
//        }
//    }
//
////    @Test
////    public void findAllTest(){
//////      모든 문의사항을 검색한다.
//////      DTO로 반환
////        List<AskDTO> asks=jpaQueryFactory.select(new QAskDTO(
////                ask.user.userId,
////                ask.askStatus,
////                ask.askTitle,
////                ask.askContent,
////                ask.askCategory
////        )).from(ask).fetch();
////        log.info("------------------------------------------------------------");
////        asks.stream().map(AskDTO::toString).forEach(log::info);
////        log.info("------------------------------------------------------------");
////    }
////
////    @Test
////    public void findAllById(){
//////      사용자의 문의사항 전부를 가져온다.
//////      2번 사용자의 문의사항 전부를 가져온다.
////        List<AskDTO> asks=jpaQueryFactory.select(new QAskDTO(
////                ask.user.userId,
////                ask.askStatus,
////                ask.askTitle,
////                ask.askContent,
////                ask.askCategory
////        )).from(ask).where(ask.user.userId.eq(2L)).fetch();
////        log.info("------------------------------------------------------------");
////        asks.stream().map(AskDTO::toString).forEach(log::info);
////        log.info("------------------------------------------------------------");
////    }
////
////    @Test
////    public void updateTest(){
//////      시나리오 : 관리자에 의해 사용자(2L)의 문의하기 상태가 변경된다.
//////      가져온 DTO, 수정된 내용
////        AskDTO askDTO=new AskDTO(2L,AskStatus.COMPLETE,null,null,null);
//////      외부에서 넘겨받은 문의하기 번호로 개체를 가져온다.
////        Ask asks=jpaQueryFactory.selectFrom(ask).where(ask.askId.eq(8L)).fetchOne();
////
//////      entity로 변환하면서, 수정이 불가한 내용들은 모두 지워진다.
////        asks.changeUser(userRepository.findById(askDTO.getUserId()).get());
////        asks.setAskId(8L);
////
//////      문의하기 상태가 변경된다.
////        asks.update(askDTO);
////
////    }
////
////    @Test
////    public void modifyUpdateTest(){
//////      시나리오 : 사용자(2L)가 자신의 문의하기 제목과 내용을 변경
//////      가져온 DTO, 수정된 내용
////        AskDTO askDTO=new AskDTO(2L,null,"2번째 제목 변경","2번째 내용 변경",null);
////
//////      외부에서 넘겨받은 문의하기 번호로 개체를 조회
////        Ask asks=jpaQueryFactory.selectFrom(ask).where(ask.askId.eq(8L)).fetchOne();
////
//////      entity로 변환하면서, 수정이 불가한 내용들은 모두 지워진다.
////        asks.changeUser(userRepository.findById(askDTO.getUserId()).get());
////        asks.setAskId(8L);
////
//////      문의하기의 제목과 내용은 변경된다.
////        asks.modifyupdate(askDTO);
////    }
////    @Test
////    public void deleteTest(){
////
//////      시나리오 : 사용자(2L)가 자신의 문의하기 내용을 선택(QUETINFO)해서 삭제한다.
//////      화면에서 넘어온 문의하기 번호로 개체 조회
////        Ask asks=jpaQueryFactory.selectFrom(ask).where(ask.askId.eq(8L)).fetchOne();
////
//////      해당 문의하기 개체 삭제
////        askRepository.delete(asks);
////    }
//}
