package com.example.myoceanproject.dtoTest;

import com.example.myoceanproject.domain.QUserDTO;
import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.entity.CommunityLike;
import com.example.myoceanproject.entity.CommunityPost;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.community.like.CommunityLikeRepository;
import com.example.myoceanproject.repository.community.like.CommunityLikeRepositoryImpl;
import com.example.myoceanproject.repository.community.post.CommunityPostRepository;
import com.example.myoceanproject.repository.UserRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.myoceanproject.entity.QCommunityLike.communityLike;
import static com.example.myoceanproject.entity.QUser.user;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(false)
public class CommunityLikeTest {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommunityPostRepository communityPostRepository;

    @Autowired
    private CommunityLikeRepositoryImpl likeRepositoryImpl;
    @Autowired
    CommunityLikeRepository communityLikeRepository;


    @Test
    public void saveCommunitylikeTest(){
        
//      시나리오: 커뮤니티 게시글(4L) 작성자에게 다른 유저(2L)가 좋아요를 누른다.
//       ! 유저에 관한 중복검사는 프론트 단에서 진행되므로, 백단에서는 진행하지 않는다.

//      communityPostRepository 인터페이스 구현체 hibernate의 findById메서드를 이용해서
//      게시글 번호에 해당하는 게시글 내용 검색한다.
        CommunityPost communityPost = communityPostRepository.findById(4L).get();

//      userRepository 인터페이스 구현체 hibernate의 findById메서드를 이용해서
//      다른 유저를 검색한다.
        Optional<User> user = userRepository.findById(2L);
        
//      화면에서 입력받는 내용은 없으며, 다른 유저의 요청에 의해 
//      커뮤니티 게시판에 출력되는 좋아요 값을 변경하기 위해 communityLike객체 선언
        CommunityLike communityLike = new CommunityLike();

//      userRepository 인터페이스 구현체 hibernate의 findbyid메서드로 유저를 검색후 추가
        communityLike.setUser(user.get());
        
//      communityPostRepository 인터페이스 구현체 hibernate의 findbyid메서드로 게시글 검색후 추가
        communityLike.setCommunityPost(communityPost);

//      커뮤니티 좋아요 테이블에 해당 내용 저장
        communityLikeRepository.save(communityLike);

//        update 메서드, 실행하면 라이크 카운트 1증가
        communityPost.updateLikePlusCount();

    }

//    @Test
//    public void findAllTest(){
////        이건 화면에 출력되지 않으므로, 엔티티로 진행한다.
////        -----------좋아요를 누른 유저의 정보를 가져오는것으로 변경함--------------
//        List<UserDTO> communityLikes = jpaQueryFactory.select( new QUserDTO(
//                    user.userNickname,
//                    user.userFileName,
//                    user.userFilePath,
//                    user.userFileSize,
//                    user.userFileUuid
//                )).from(user).join(communityLike).on(communityLike.userId.eq(user.userId)).fetch();
//        log.info("----------------------------------------------------------");
//        communityLikes.stream().map(UserDTO::toString).forEach(log::info);
//        log.info("----------------------------------------------------------");
//    }

    @Test
    public void findById(){
//        ------------------------좋아요를 누른지 체크하는 로직으로 변경함---------------------------------
//        프론트에서 컨트롤러로 포스트번호랑, 유저번호를 변수로 보냄
        Long postId = 4L;
        Long userId = 2L;

//        유저번호와 포스트번호로 라이크여부를 가려주는 메소드 실행,
//        좋아요를 하지 않았으면 true, 좋아요를 했으면 false
        log.info("----------------------------------------------------------");
        log.info(likeRepositoryImpl.findByCommunityPostAndUser(userId, postId) ? "좋아요하지 않았음" : "좋아요했음");
        log.info("----------------------------------------------------------");

    }


    @Test
    public void deleteTest(){
        Long postId = 4L;
        Long userId = 2L;
//        유효성검사는 프런트에서 진행되므로, 백단에서는 진행하지 않는다
        likeRepositoryImpl.deleteByCommunityPostAndUser(userId, postId);

    }
}
