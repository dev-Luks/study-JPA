//package com.example.myoceanproject.dtoTest;
//
//import com.example.myoceanproject.domain.QQuestDTO;
//import com.example.myoceanproject.domain.QuestDTO;
//import com.example.myoceanproject.entity.Quest;
//import com.example.myoceanproject.repository.quest.QuestRepository;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static com.example.myoceanproject.entity.QQuest.quest;
//
//@SpringBootTest
//@Slf4j
//@Transactional
//@Rollback(false)
//public class QuestTest {
//    @Autowired
//    JPAQueryFactory jpaQueryFactory;
//
//    @Autowired
//    QuestRepository questRepository;
//
//    @Test
//    public void saveTest(){
////      시나리오 : 퀘스트는 fk 없이 독립적이기 때문에 별도의 시나리오는 필요없다고 판단.
////      DTO에 값을 set해서 저장만 해주면됨
//        QuestDTO questDTO = new QuestDTO();
//
//        questDTO.setQuestCategory("활동2");
//        questDTO.setQuestContent("회원가입시 지급2");
//        questDTO.setQuestName("회원가입뱃지2");
//        questDTO.setQuestDeadLine(String.valueOf(LocalDateTime.now()));
//        questDTO.setQuestFilePath("FilePath");
//        questDTO.setQuestFileSize(392847L);
//        questDTO.setQuestFileName("FileName");
//        questDTO.setQuestFileUuid("Uuid");
//
//        Quest quest1 = questDTO.toEntity();
//        questRepository.save(quest1);
//    }
//
//    @Test
//    public void findAllTest(){
//        List<QuestDTO> quests = jpaQueryFactory.select(new QQuestDTO(
//                quest.questCategory,
//                quest.questName,
//                quest.questContent,
//                quest.questDeadLine,
//                quest.questPoint,
//                quest.questFilePath,
//                quest.questFileName,
//                quest.questFileUuid,
//                quest.questFileSize
//        )).from(quest).fetch();
//        log.info("------------------------------------------------------------");
//        quests.stream().map(QuestDTO::toString).forEach(log::info);
//        log.info("------------------------------------------------------------");
//    }
//
//    @Test
//    public void findByIdTest(){
//        List<QuestDTO> quests = jpaQueryFactory.select(new QQuestDTO(
//                quest.questCategory,
//                quest.questName,
//                quest.questContent,
//                quest.questDeadLine,
//                quest.questPoint,
//                quest.questFilePath,
//                quest.questFileName,
//                quest.questFileUuid,
//                quest.questFileSize
//        )).from(quest).where(quest.questId.eq(2L)).fetch();
//        log.info("------------------------------------------------------------");
//        quests.stream().map(QuestDTO::toString).forEach(log::info);
//        log.info("------------------------------------------------------------");
//    }
//
//    @Test
//    public void updateTest(){
////      퀘스트 관련 정보 수정
//        QuestDTO questDTO = new QuestDTO();
//
////      quest 테이블의 ID 2인 값을 수정하기 위해 값을 입력한다.
////      이렇게 입력을 다 하더라도, update 메소드에서 update가 되는 내용만 update가 된다.
//        questDTO.setQuestCategory("활동2 수정");
//        questDTO.setQuestContent("회원가입시 지급2 수정");
//        questDTO.setQuestName("회원가입뱃지2 수정");
//        questDTO.setQuestDeadLine(String.valueOf(LocalDateTime.now()));
//        questDTO.setQuestFilePath("FilePath");
//        questDTO.setQuestFileSize(392847L);
//        questDTO.setQuestFileName("FileName");
//        questDTO.setQuestFileUuid("Uuid");
//
////      외부에서 넘어온 quest 번호를 통해 개체를 가져온다.
//        Quest quest1 = jpaQueryFactory.selectFrom(quest).where(quest.questId.eq(2L)).fetchOne();
//
////      FK값이 없기 때문에 따로 set할 내용은 없음
//
////      quest update 메소드에서 업데이트가 가능한 내용만 업데이트가 되고, 나머지 내용은 삭제된다.
//        quest1.update(questDTO);
//    }
//
//    @Test
//    public void deleteTest(){
////      2번 quest 삭제
//        Quest quest = questRepository.findById(2L).get();
//        questRepository.delete(quest);
//    }
//}
