package com.example.myoceanproject.dtoTest;


import com.example.myoceanproject.domain.CommunityReplyDTO;
import com.example.myoceanproject.domain.QCommunityReplyDTO;
import com.example.myoceanproject.entity.*;
import com.example.myoceanproject.repository.community.post.CommunityPostRepository;
import com.example.myoceanproject.repository.community.reply.CommunityReplyRepository;
import com.example.myoceanproject.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.myoceanproject.entity.QCommunityReply.communityReply;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(false)
public class CommunityReplyTest {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommunityPostRepository communityPostRepository;
    @Autowired
    private CommunityReplyRepository communityReplyRepository;


    @Test
    public void saveCommunityReplyTest() {

//      시나리오: 커뮤니티 게시글(1L) 작성자에게 다른 유저(3L)가 댓글을 추가한다.

//      userRepository 인터페이스 구현체 hibernate의 findById메서드를 이용해서
//      다른 유저를 검색한다.
        for(int i = 0; i < 4; i++){
        Optional<User> user = userRepository.findById(17L);

//      communityPostRepository 인터페이스 구현체 hibernate의 findById메서드를 이용해서
//      게시글 번호에 해당하는 게시글 내용 검색한다.
        Optional<CommunityPost> communityPost = communityPostRepository.findById(65L);

//      화면에서 댓글내용을 입력받기 위해 댓글 DTO 객체 생성
        CommunityReplyDTO communityReplyDTO = new CommunityReplyDTO();

//      댓글 내용을 화면에서 입력받는다.
        communityReplyDTO.setCommunityReplyContent(i + " 댓글 추가");

//      추가 저장을 위해 toentity메서드를 통해 communityReply객체에 저장
        CommunityReply communityReply = communityReplyDTO.toEntity();

//      userRepository 인터페이스 구현체 hibernate의 findbyid메서드로 유저를 검색후 추가
        communityReply.setUser(user.get());

//      communityPostRepository 인터페이스 구현체 hibernate의 findbyid메서드로 게시글 검색후 추가
        communityReply.setCommunityPost(communityPost.get());

//      커뮤니티 댓글 테이블에 해당 내용 저장
        communityReplyRepository.save(communityReply);
        }
    }

//    @Test
//    public void findAllTest(){
////        포스트 전체 댓글 불러오기
//        List<CommunityReplyDTO> communityReplies = jpaQueryFactory.select(new QCommunityReplyDTO(
//                communityReply.user.userId,
//                communityReply.user.userNickname,
//                communityReply.communityPost.communityPostId,
//                communityReply.communityReplyContent,
//                communityReply.user.userFileName,
//                communityReply.user.userFilePath,
//                communityReply.user.userFileSize,
//                communityReply.user.userFileUuid
//        )).from(communityReply).where(communityReply.communityPost.communityPostId.eq(15L)).fetch();
//        log.info("---------------------------------------------------------------------");
//        communityReplies.stream().map(CommunityReplyDTO::toString).forEach(log::info);
//        log.info("---------------------------------------------------------------------");
//    }
////
//    @Test
//    public void findById(){
////        내 댓글... 이런 기능이 있나?
//        List<CommunityReplyDTO> communityReplies = jpaQueryFactory.select(new QCommunityReplyDTO(
//                communityReply.user.userId,
//                communityReply.user.userNickname,
//                communityReply.communityPost.communityPostId,
//                communityReply.communityReplyContent,
//                communityReply.user.userFileName,
//                communityReply.user.userFilePath,
//                communityReply.user.userFileSize,
//                communityReply.user.userFileUuid
//        )).from(communityReply).where(communityReply.user.userId.eq(14L)).fetch();
//
//        communityReplies.stream().map(CommunityReplyDTO::toString).forEach(log::info);
//
//    }
////
//    @Test
//    public void updateTest(){
////        화면에서 선택한 댓글을 수정하고 컨트롤러에 전달함(DTO)
////        컨트롤러에서 해당 댓글을 서비스를 통해 업데이트함(DTO -> Entity)
//
//        CommunityReplyDTO replyDTO = new CommunityReplyDTO(14L, "칸초코", 15L, "댓글 수정합니다", null, null, null, null);
////        외부에서 넘겨온 포스트번호로 개체를 가져온다(영속성 컨텍스트가 관리하는 개체)
//        CommunityReply reply = jpaQueryFactory.selectFrom(communityReply).where(communityReply.communityReplyId.eq(24L)).fetchOne();
//        Objects.requireNonNull(reply).setCommunityPost(communityPostRepository.findById(replyDTO.getCommunityPostId()).get());
//        reply.setCommunityReplyId(24L);
//
//        log.info("---------------------------------------------------------------------");
//        log.info(reply.toString());
//        log.info("---------------------------------------------------------------------");
//
//        reply.update(replyDTO.getCommunityReplyContent());
//    }
////
//    @Test
//    public void deleteTest(){
////      n번 리플 삭제
//        communityReplyRepository.delete(communityReplyRepository.findById(23L).get());
//    }

}
