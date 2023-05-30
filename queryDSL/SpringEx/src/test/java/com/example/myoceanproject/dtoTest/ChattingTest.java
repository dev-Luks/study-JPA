//package com.example.myoceanproject.dtoTest;
//
//import com.example.myoceanproject.domain.ChattingDTO;
//import com.example.myoceanproject.domain.ChattingStatusDTO;
//import com.example.myoceanproject.domain.GroupDTO;
//import com.example.myoceanproject.entity.*;
//import com.example.myoceanproject.repository.*;
//import com.example.myoceanproject.repository.chatting.ChattingRepository;
//import com.example.myoceanproject.repository.chatting.ChattingRepositoryImpl;
//import com.example.myoceanproject.repository.chatting.ChattingStatusRepository;
//import com.example.myoceanproject.repository.chatting.ChattingStatusRepositoryImpl;
//import com.example.myoceanproject.service.chattingService.ChattingService;
//import com.example.myoceanproject.type.ReadStatus;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@SpringBootTest
//@Slf4j
//@Transactional
//@Rollback(false)
//public class ChattingTest {
//    @Autowired
//    private JPAQueryFactory jpaQueryFactory;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private GroupRepository groupRepository;
//    @Autowired
//    private ChattingRepository chattingRepository;
//
//    @Autowired
//    private GroupMemberRepository groupMemberRepository;
//
//    @Autowired
//    private ChattingRepositoryImpl chattingRepositoryImpl;
//    @Autowired
//    private ChattingStatusRepository chattingStatusRepository;
//
//    @Autowired
//    private ChattingStatusRepositoryImpl chattingStatusRepositoryImpl;
//
//    @Autowired
//    private ChattingService chattingService;
//
//
////    @Test
////    public void saveTest(){
//////        시나리오 : 20번 멤버가 메세지를 보내면 같은 그룹의 그룹원들에게만 채팅이 전송된다.
////
//////        채팅 내용, 보낸 사람 저장(TBL_CHATTING)
////        ChattingDTO chattingDTO = new ChattingDTO();
////        chattingDTO.setChattingContent("안녕하세요 저는 김인영입니다.");
////        Chatting chatting1 = chattingDTO.toEntity();
////        chatting1.setSenderGroupMember(groupMemberRepository.findById(20L).get());
////        chatting1.setGroup(groupMemberRepository.findById(20L).get().getGroup());
////
//////        채팅 읽음 상태, 받는 사람들 저장(TBL_CHATTING_STATUS)
////        List<ChattingStatus> chattingStatusList = new ArrayList<>();
////        for (GroupMember groupMember : chattingRepositoryImpl.findByGroupId(chatting1.getGroup().getGroupId())) {
////            ChattingStatusDTO chattingStatusDTO = new ChattingStatusDTO();
////            chattingStatusDTO.setReadStatus(ReadStatus.UNREAD);
////            ChattingStatus chattingStatus1 = chattingStatusDTO.toEntity();
////            chattingStatus1.setReceiverGroupMember(groupMember);
////            chattingStatus1.setChatting(chatting1);
////            chattingStatusList.add(chattingStatus1);
////        }
////        chattingRepository.save(chatting1);
////        chattingStatusRepository.saveAll(chattingStatusList);
////    }
//
//
////    @Test
////    public void findById(){
//////        시나리오 : 특정 그룹의 채팅방에 들어가면 해당 그룹의 채팅 내용만 떠야한다.
//////        그룹 아이디가 4인 채팅 내용만 조회
////        List<ChattingDTO> chattingDTOs = jpaQueryFactory.select(new QChattingDTO(
////                        chatting.senderGroupMember.user.userId,
////                        chatting.senderGroupMember.user.userNickname,
////                        chatting.senderGroupMember.user.userFileName,
////                        chatting.senderGroupMember.user.userFilePath,
////                        chatting.senderGroupMember.user.userFileUuid,
////                        chatting.senderGroupMember.user.userFileSize,
////                        chatting.group.groupId,
////                        chatting.group.groupName,
////                        chatting.group.groupFilePath,
////                        chatting.group.groupFileName,
////                        chatting.group.groupFileUuid,
////                        chatting.group.groupFileSize,
////                        chatting.senderGroupMember.groupMemberId,
////                        chatting.chattingContent
////                )).where(chatting.group.groupId.eq(4L))
////                .from(chatting).fetch();
////
////        chattingDTOs.stream().map(ChattingDTO::toString).forEach(log::info);
////
////    }
//
//    @Test
//    public void updateTest(){
////        시나리오 : 11번 그룹 멤버가 그룹채팅방에 입장하면 chattingStatus id 32, 35만 Read로 바뀐다.
////        11번 그룹멤버의 정보를 groupMember에 담아준다.
//        GroupMember groupMember = groupMemberRepository.findById(11L).get();
//        log.info(groupMember.toString());
//        List<ChattingStatus> chattingStatusList = new ArrayList<>();
//        chattingStatusList.addAll(chattingStatusRepositoryImpl.findByGroupMemberId(groupMember));
//        chattingStatusList.stream().forEach(v-> v.update(ReadStatus.READ));
//    }
//
//    @Test
//    public void findByUserId(){
//        log.info("===================================================================================");
//        chattingRepositoryImpl.findByUserId(5L).stream().map(GroupDTO::toString).forEach(log::info);
//        log.info("===================================================================================");
//
//    }
//
//    @Test
//    public void showTest(){
//        chattingService.show(1L).stream().map(GroupDTO::toString).forEach(log::info);
//
//    }
////
////    @Test
////    public void deleteTest(){
////        Long count = jpaQueryFactory
////                .delete(chatting)
////                .where(chatting.chattingId.eq(18L))
////                .execute();
////    }
//}
