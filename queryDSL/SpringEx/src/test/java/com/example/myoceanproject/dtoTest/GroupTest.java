//package com.example.myoceanproject.dtoTest;
//
//import com.example.myoceanproject.domain.CommunityPostDTO;
//import com.example.myoceanproject.domain.GroupDTO;
//import com.example.myoceanproject.domain.QGroupDTO;
//import com.example.myoceanproject.domain.UserDTO;
//import com.example.myoceanproject.entity.*;
//import com.example.myoceanproject.repository.GroupRepository;
//import com.example.myoceanproject.repository.UserRepository;
//import com.example.myoceanproject.type.*;
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
//
//@SpringBootTest
//@Slf4j
//@Transactional
//@Rollback(false)
//public class GroupTest {
//
//    @Autowired
//    private JPAQueryFactory jpaQueryFactory;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private GroupRepository groupRepository;
//
//    @Test
//    public void saveGroupTest() {
////        시나리오 : 모임 작성자의 아이디를 세션에서 받아온다.(=>맞는지 확인 필요), 그 후 사용자가 화면에 입력한 그룹에 대한 정보를 groupDTO에 set해준다.
////        유저가 db에 없을 경우를 대비해서 유저 먼저 db에 저장해줬다. db에 유저가 있다면 findById를 사용하여 찾아오기만 하면 된다.
//
////      유저 db에 저장
////        UserDTO userDTO = new UserDTO();
////        userDTO.setUserEmail("akjhdgaiafd");
////        userDTO.setUserNickname("dlsdud");
////        userDTO.setUserPassword("qakjghlig");
////        userDTO.setUserLoginMethod(UserLoginMethod.GENERAL);
////        userDTO.setUserAccountStatus(UserAccountStatus.ACTIVE);
////        userRepository.save(userDTO.toEntity());
//        for (int i = 0; i < 10; i++) {
////      모임 정보 저장
//            GroupDTO groupDTO = new GroupDTO();
//            groupDTO.setGroupCategory("운동하기");
//            groupDTO.setGroupContent("같이 풋살해요");
//            groupDTO.setGroupLocation("장소");
//            groupDTO.setGroupName("난 공밖에 몰라");
//            groupDTO.setGroupPoint(200);
//            groupDTO.setGroupStatus(GroupStatus.DISAPPROVED);
//            groupDTO.setGroupLocationType(GroupLocationType.ONLINE);
//            groupDTO.setGroupFileSize(10L);
//
//            groupDTO.setGroupFilePath("https://letspl.s3.ap-northeast-2.amazonaws.com/images/project_thumb_20.png");
//            groupDTO.setGroupFileName("groupName");
//            groupDTO.setGroupLocation("서울 강남");
//            groupDTO.setGroupLocationDetail("sdkshgkh");
//            groupDTO.setGroupParkingAvailable("dsafhdsakdfh");
//            groupDTO.setGroupOverSea("해외");
//            groupDTO.setGroupLocationName("서울 강남");
//            groupDTO.setGroupMoreInformation("장소의 더보기");
////        임베드 타입 set해줌
//            groupDTO.setMaxMember(10);
//            groupDTO.setMinMember(2);
////        DTO를 엔티티로 바꿔서 저장해줌
//            Group group1 = groupDTO.toEntity();
//            group1.setUser(userRepository.findById(1L).get());
//            groupRepository.save(group1);
//
//        }
//    }
//
////    @Test
////    public void findAllTest(){
////        List<GroupDTO> groups = jpaQueryFactory.select(new QGroupDTO(
////                group.groupId,
////                group.user.userId,
////                group.user.userNickname,
////                group.groupName,
////                group.groupCategory,
////                group.groupContent,
////                group.groupPoint,
////                group.groupOverSea,
////                group.groupLocationName,
////                group.groupLocation,
////                group.groupLocationDetail,
////                group.groupParkingAvailable,
////                group.groupMoreInformation,
////                group.groupLocationType,
////                group.groupStatus,
////                group.groupFilePath,
////                group.groupFileName,
////                group.groupFileUuid,
////                group.groupFileSize,
////                group.groupMemberLimit.maxMember,
////                group.groupMemberLimit.minMember)).from(group).fetch();
////        log.info("------------------------------------------------------------");
////        groups.stream().map(GroupDTO::toString).forEach(log::info);
////        log.info("------------------------------------------------------------");
////    }
////
////    @Test
////    public void findById(){
////        List<GroupDTO> groups = jpaQueryFactory.select(new QGroupDTO(
////                group.groupId,
////                group.user.userId,
////                group.user.userNickname,
////                group.groupName,
////                group.groupCategory,
////                group.groupContent,
////                group.groupPoint,
////                group.groupOverSea,
////                group.groupLocationName,
////                group.groupLocation,
////                group.groupLocationDetail,
////                group.groupParkingAvailable,
////                group.groupMoreInformation,
////                group.groupLocationType,
////                group.groupStatus,
////                group.groupFilePath,
////                group.groupFileName,
////                group.groupFileUuid,
////                group.groupFileSize,
////                group.groupMemberLimit.maxMember,
////                group.groupMemberLimit.minMember)).from(group).where(group.user.userId.eq(1L)).fetch();
////
////        log.info("------------------------------------------------------------");
////        groups.stream().map(GroupDTO::toString).forEach(log::info);
////        log.info("------------------------------------------------------------");
////    }
////
////
////
////    @Test
////    public void updateTest(){
//////  모임을 오픈한 유저가 해당 모임의 상세보기 페이지로 들어가 수정을 클릭
//////  수정 페이지에서 모임 정보를 조정한 후 다시 저장
////
//////      주소 파라미터로 모임 게시글 번호가 전달됨
////        GroupDTO groupDTO = new GroupDTO();
////        groupDTO.setUserId(1L);
////        groupDTO.setUserNickName("수정된 닉네임");
////        groupDTO.setGroupName("수정된 모임 이름");
////        groupDTO.setGroupCategory("수정 카테고리");
////        groupDTO.setGroupContent("수정 내용");
////        groupDTO.setGroupPoint(155);
////        groupDTO.setGroupLocation("수정된 장소");
////        groupDTO.setGroupLocationType(GroupLocationType.ONLINE);
////        groupDTO.setGroupStatus(GroupStatus.WAITING);
////        groupDTO.setGroupFilePath("수정 파일");
////        groupDTO.setGroupFileName("수정 파일 이름");
////
////        groupDTO.setGroupFileSize(155L);
////        groupDTO.setMaxMember(15);
////        groupDTO.setMinMember(1);
////
//////      외부에서 넘겨온 모임 게시글 번호로 영속성 컨텍스트가 관리하는 개체를 가져온다.
////        Group group1 = jpaQueryFactory.selectFrom(group).where(group.groupId.eq(2L)).fetchOne();
////
//////      entity로 변환하며 수정이 불가한 내용들은 지워지고 update 메소드에 포함된 내용만 저장이 된다.
////        group1.setUser(userRepository.findById(groupDTO.getUserId()).get());
////
////        group1.update(groupDTO);
////    }
////
////    @Test
////    public void deleteTest() {
//////      모임 게시글 번호를 받아온다.
////
//////      13번 게시글을 찾아 객체 선언
////        Group group = groupRepository.findById(13L).get();
////
//////      파일 테이블이 따로 없으니 바로 삭제 진행
////        groupRepository.delete(group);
////    }
//}
