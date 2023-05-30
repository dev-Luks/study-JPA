//package com.example.myoceanproject.dtoTest;
//
//import com.example.myoceanproject.entity.Quest;
//import com.example.myoceanproject.entity.QuestAchievement;
//import com.example.myoceanproject.entity.User;
//import com.example.myoceanproject.repository.QuestAchievementRepository;
//import com.example.myoceanproject.repository.quest.QuestRepository;
//import com.example.myoceanproject.repository.UserRepository;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Slf4j
//@Transactional
//@Rollback(false)
//public class QuestAchievementTest {
//
//    @Autowired
//    JPAQueryFactory jpaQueryFactory;
//
//    @Autowired
//    QuestAchievementRepository questAchievementRepository;
//
//    @Autowired
//    QuestRepository questRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    public void saveTest(){
////        시나리오 : 세션에서 받아온 유저 아이디로 user 정보를 저장하고, 회원의 활동에 따라 어떤 퀘스트를 달성했는지 찾는다.
////        그후 유저와 퀘스트를 fk로 받아서 questAchievement 테이블에 저장한다.
//
////        기존에 저장해놓은 user와 quest가 없으면 userDTO와 qusetDTO부터 만들어서 set해줘야함
////        >>위 언급한 방식은 groupMemberTest 참고
////        db에 저장해둔 user와 quest가 있기 때문에 findById만 사용해서 user엔티티와 quest엔티티를 변수에 담아줌
//        Optional<User> user = userRepository.findById(2L);
//        Optional<Quest> quest = questRepository.findById(1L);
////        QuestAchievement의 경우 컬럼이 fk 두개로 구성돼있기 때문에 DTO가 없음
////        따라서 엔티티 객체를 바로 생성
//        QuestAchievement questAchievement = new QuestAchievement();
////        quest와 user 모두 fk 이기 때문에 change메소드를 이용해서 set 해줌
//        questAchievement.setQuest(quest.get());
//        questAchievement.setUser(user.get());
////        questAchievement db에 저장해줌
//        questAchievementRepository.save(questAchievement);
//    }
//
//    @Test
//    public void findAllTest(){
//        assertThat(questAchievementRepository.findAll().size()).isEqualTo(2);
//    }
//
//    @Test
//    public void findByIdTest(){
//        assertThat(questAchievementRepository.findById(3L).get().getQuestAchievementId()).isEqualTo(3L);
//    }
//
//    @Test
//    public void deleteTest(){
//        QuestAchievement questAchievement = questAchievementRepository.findById(4L).get();
//        questAchievementRepository.delete(questAchievement);
//    }
//}
