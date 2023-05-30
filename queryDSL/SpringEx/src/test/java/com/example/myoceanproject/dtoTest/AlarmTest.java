//package com.example.myoceanproject.dtoTest;
//
//import com.example.myoceanproject.domain.*;
//import com.example.myoceanproject.entity.*;
//import com.example.myoceanproject.repository.UserRepository;
//import com.example.myoceanproject.repository.community.post.CommunityPostRepository;
//import com.example.myoceanproject.repository.community.reply.CommunityReplyRepository;
//import com.example.myoceanproject.type.*;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.extern.slf4j.Slf4j;
//
//import static com.example.myoceanproject.entity.QAlarm.alarm;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@SpringBootTest
//@Slf4j
//@Transactional
//@Rollback(false)
//public class AlarmTest {
//    @Autowired
//    private AlarmDTO alarmDTO;
//    @Autowired
//    private AlarmRepository alarmRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JPAQueryFactory jpaQueryFactory;
//
//    @Autowired
//    private CommunityPostRepository communityPostRepository;
//
//    @Autowired
//    private CommunityReplyRepository communityReplyRepository;
//
//
//    @Test
//    public void saveAlarmTest(){
//
////      댓글 작성 시 알람이 게시글 작성자에게 오도록 함
////      replyDTO를 선언하고 여기에 댓글 내용이 추가되면,
////      post 작성자에게 알람 메세지가 오도록 함
////      따라서 postRepository의 findById를 해서 알람이 오도록 진행
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUserEmail("akjhdgaiafd");
//        userDTO.setUserNickname("dlsdud");
//        userDTO.setUserPassword("qakjghlig");
//        userDTO.setUserLoginMethod(UserLoginMethod.GENERAL);
//        userDTO.setUserAccountStatus(UserAccountStatus.ACTIVE);
//        userRepository.save(userDTO.toEntity());
//        CommunityPostDTO communityPostDTO = new CommunityPostDTO();
//
//        communityPostDTO.setCommunityCategory(CommunityCategory.BOOK);
//        communityPostDTO.setCommunityTitle("안녕");
//        communityPostDTO.setCommunityContent("ahffkahffk");
//        CommunityPost communityPost = communityPostDTO.toEntity();
//        communityPost.setUser(userRepository.findById(1L).get());
//        communityPostRepository.save(communityPost);
//
//
//        CommunityReplyDTO communityReplyDTO = new CommunityReplyDTO();
//        communityReplyDTO.setCommunityReplyContent("댓글을 달았습니다.");
//        communityReplyDTO.setUserId(2L);
////      postId 받아오기
//        communityReplyDTO.setCommunityPostId(1L);
//        communityReplyRepository.save(communityReplyDTO.toEntity());
//
//
////      1번 유저 불러오기
//        User user = communityPostRepository.findById(2L).get().getUser();
//
//        Alarm alarm1 = new Alarm();
//        alarm1.setAlarmContent("알ㄹ마알망ㄹ");
//        alarm1.setUser(user);
//        alarm1.setReadStatus(ReadStatus.UNREAD);
//
//        alarmRepository.save(alarm1);
//
//
//
//    }
//
//    @Test
//    public void findAllTest(){
//        List<AlarmDTO> alarms = jpaQueryFactory.select(new QAlarmDTO(
//                alarm.user.userId,
//                alarm.user.userNickname,
//                alarm.alarmContent,
//                alarm.readStatus)).from(alarm).fetch();
//        alarms.stream().map(AlarmDTO::toString).forEach(log::info);
//    }
//
//    @Test
//    public void findById(){
////        시나리오 : 유저의 아이디가 1인 알람만 select
//        List<AlarmDTO> alarms = jpaQueryFactory.select(new QAlarmDTO(
//                alarm.user.userId,
//                alarm.user.userNickname,
//                alarm.alarmContent,
//                alarm.readStatus)).where(alarm.user.userId.eq(5L)).from(alarm).fetch();
//        alarms.stream().map(AlarmDTO::toString).forEach(log::info);
//
//    }
//
//    @Test
//    public void updateTest(){
////        시나리오: 유저가 읽지 않은 알림을 누르면 해당 알림의 읽음 상태가 안읽음->읽음으로 변한다
//
////        유저가 클릭한 알림을 db에서 가져온다.
//        Alarm alarm1 = jpaQueryFactory.selectFrom(QAlarm.alarm).where(QAlarm.alarm.user.userId.eq(1L).and(QAlarm.alarm.alarmId.eq(4L))).fetchOne();
////        사용자가 알림을 클릭했을때
//
//        alarm1.update(ReadStatus.READ);
//
//    }
//
//    //    여러개의 컬럼을 update할땐 set을 여러번 사용한다
////    @Test
////    public void updateMultipleTest(){
////
////
////        List<Alarm> alarms = jpaQueryFactory.selectFrom(alarm)
////                .where(alarm.readStatus.eq(ReadStatus.UNREAD))
////                .fetch();
////
////        alarms.stream().forEach(v->{v.update(ReadStatus.READ);});
////    }
////
////    @Test
////    public void deleteTest(){
////        Long count = jpaQueryFactory
////                .delete(alarm)
////                .where(alarm.alarmContent.eq("두번째수정"))
////                .execute();
////    }
//
//
//}