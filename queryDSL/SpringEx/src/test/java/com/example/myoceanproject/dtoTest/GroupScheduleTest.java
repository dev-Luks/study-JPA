//package com.example.myoceanproject.dtoTest;
//
//import com.example.myoceanproject.domain.GroupDTO;
//import com.example.myoceanproject.domain.GroupScheduleDTO;
//import com.example.myoceanproject.domain.QGroupScheduleDTO;
//import com.example.myoceanproject.domain.UserDTO;
//import com.example.myoceanproject.entity.Ask;
//import com.example.myoceanproject.entity.Group;
//import com.example.myoceanproject.entity.GroupSchedule;
//import com.example.myoceanproject.repository.GroupRepository;
//import com.example.myoceanproject.repository.GroupScheduleRepository;
//import com.example.myoceanproject.repository.UserRepository;
//import com.example.myoceanproject.type.AskCategory;
//import com.example.myoceanproject.type.AskStatus;
//import com.example.myoceanproject.type.UserAccountStatus;
//import com.example.myoceanproject.type.UserLoginMethod;
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
//import java.util.Optional;
//
//import static com.example.myoceanproject.entity.QAsk.ask;
//import static com.example.myoceanproject.entity.QGroup.group;
//import static com.example.myoceanproject.entity.QGroupSchedule.groupSchedule;
//
//@SpringBootTest
//@Slf4j
//@Transactional
//@Rollback(false)
//public class GroupScheduleTest {
//    @Autowired
//    private JPAQueryFactory jpaQueryFactory;
//    @Autowired
//    private GroupScheduleRepository groupScheduleRepository;
//    @Autowired
//    private GroupRepository groupRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void saveGroupScheduleTest(){
//
//
//        //      유저 db에 저장
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUserEmail("akjhdgaiafd");
//        userDTO.setUserNickname("dlsdud");
//        userDTO.setUserPassword("qakjghlig");
//        userDTO.setUserLoginMethod(UserLoginMethod.GENERAL);
//        userDTO.setUserAccountStatus(UserAccountStatus.ACTIVE);
//        userRepository.save(userDTO.toEntity());
//
////      모임 불러오기
//        Optional<Group> group = groupRepository.findById(1L);
//        GroupScheduleDTO groupScheduleDTO = new GroupScheduleDTO();
//
////      groupScheduleDTO에 필요한 값 저장
//        groupScheduleDTO.setGroupScheduleDate(LocalDateTime.now());
//        groupScheduleDTO.setGroupScheduleStartTime(LocalDateTime.now());
//        groupScheduleDTO.setGroupScheduleEndTime(LocalDateTime.now());
//
////      entity 변환
//        GroupSchedule groupSchedule1 = groupScheduleDTO.toEntity();
//
////      모임 정보 저장
//        groupSchedule1.setGroup(groupRepository.findById(2L).get());
//
////      엔티티에 값 저장
//        groupScheduleRepository.save(groupSchedule1);
//    }
//
//    @Test
//    public void findAllTest(){
//        List<GroupScheduleDTO> groupSchedules = jpaQueryFactory.select(new QGroupScheduleDTO(
//                groupSchedule.groupScheduleId,
//                groupSchedule.groupScheduleDate,
//                groupSchedule.groupScheduleStartTime,
//                groupSchedule.groupScheduleEndTime
//        )).from(groupSchedule).fetch();
//        log.info("------------------------------------------------------------");
//        groupSchedules.stream().map(GroupScheduleDTO::toString).forEach(log::info);
//        log.info("------------------------------------------------------------");
//    }
//
//    @Test
//    public void findById(){
//        List<GroupScheduleDTO> groupSchedules = jpaQueryFactory.select(new QGroupScheduleDTO(
//                groupSchedule.groupScheduleId,
//                groupSchedule.groupScheduleDate,
//                groupSchedule.groupScheduleStartTime,
//                groupSchedule.groupScheduleEndTime
//        )).from(groupSchedule).where(groupSchedule.group.groupId.eq(2L)).fetch();
////      2번 모임의 모임 스케줄
//        log.info("------------------------------------------------------------");
//        groupSchedules.stream().map(GroupScheduleDTO::toString).forEach(log::info);
//        log.info("------------------------------------------------------------");
//    }
//
//    @Test
//    public void updateTest(){
//        GroupScheduleDTO groupScheduleDTO = new GroupScheduleDTO();
//        groupScheduleDTO.setGroupId(2L);
//        groupScheduleDTO.setGroupScheduleDate(LocalDateTime.now());
//        groupScheduleDTO.setGroupScheduleStartTime(LocalDateTime.now());
//        groupScheduleDTO.setGroupScheduleEndTime(LocalDateTime.now());
//
//        GroupSchedule groupSchedule1 = jpaQueryFactory.selectFrom(groupSchedule).where(groupSchedule.groupScheduleId.eq(5L)).fetchOne();
//        groupSchedule1.setGroup(groupRepository.findById(groupScheduleDTO.getGroupId()).get());
//
//        groupSchedule1.update(groupScheduleDTO);
//    }
//
//    @Test
//    public void deleteTest(){
////      5번 스케줄 삭제
//        GroupSchedule groupSchedule = groupScheduleRepository.findById(5L).get();
//        groupScheduleRepository.delete(groupSchedule);
//    }
//}
