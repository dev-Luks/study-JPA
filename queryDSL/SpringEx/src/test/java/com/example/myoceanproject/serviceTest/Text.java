package com.example.myoceanproject.serviceTest;

import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.entity.*;
import com.example.myoceanproject.repository.GroupRepository;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.type.CommunityCategory;
import com.example.myoceanproject.type.GroupLocationType;
import com.example.myoceanproject.type.GroupStatus;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.myoceanproject.entity.QCommunityPost.communityPost;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(false)
public class Text {
    @Autowired
    JPAQueryFactory j;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Test
    public void test1(){
        System.out.println("안녕 : "+j.selectFrom(QUser.user).where(QUser.user.userOauthId.eq("94vyCZE-yebR1W7a5tBAUDm5a5aPkaIqn_6znsAxr3Y")).fetchOne().getUserOauthId());
    }

    @Test
    public void test2() {
        int number = 0 ;
        for (int i = 0 ; i<100 ; i++) {
            GroupDTO groupDTO = new GroupDTO();
            groupDTO.setGroupCategory("운동하기");
            groupDTO.setGroupContent("같이 풋살해요");
            groupDTO.setGroupLocation("장소");
            groupDTO.setGroupName("난 공밖에 몰라"+i);
            groupDTO.setGroupPoint(200);
            groupDTO.setGroupStatus(GroupStatus.DISAPPROVED.toString());
            groupDTO.setGroupLocationType(GroupLocationType.ONLINE);
            groupDTO.setGroupFileSize(10L);
            groupDTO.setGroupFilePath("https://letspl.s3.ap-northeast-2.amazonaws.com/images/project_thumb_"+(i%20)+".png");
            groupDTO.setGroupFileName("groupName");
            groupDTO.setGroupLocation("서울 강남");
            groupDTO.setGroupLocationDetail("sdkshgkh");
            groupDTO.setGroupParkingAvailable("dsafhdsakdfh");
            groupDTO.setGroupOverSea("해외");
            groupDTO.setGroupLocationName("서울 강남");
            groupDTO.setGroupMoreInformation("장소의 더보기");
//        임베드 타입 set해줌
            groupDTO.setMaxMember(10);
            groupDTO.setMinMember(2);
//        DTO를 엔티티로 바꿔서 저장해줌
            Group group1 = groupDTO.toEntity();
            group1.setUser(userRepository.findById(1L).get());
            groupRepository.save(group1);
        }
    }

    @Test
    public void saveTest(){
        ArrayList<String> strings = new ArrayList<>();

        strings.add(CommunityCategory.MOVIE.toString());
        strings.add(CommunityCategory.EXERCISE.toString());
        strings.add(CommunityCategory.COUNSELING.toString());
        strings.add(CommunityCategory.BOOK.toString());
        strings.add(CommunityCategory.FREEBOARD.toString());
        strings.add(CommunityCategory.COOK.toString());



        for(int i = 0 ; i<100 ; i++){
            CommunityPostDTO communityPostDTO = new CommunityPostDTO();

            communityPostDTO.setCommunityTitle("게시글 제목"+i);
            communityPostDTO.setCommunityContent("게시글 내용"+i);
            communityPostDTO.setCommunityCategory(strings.get(i%6));

        }

    }
    @Test
    public void test4(){
        List<Tuple> pages = jpaQueryFactory.select(Projections.fields(CommunityPost.class,
                communityPost.user.userId.as("userId"),
                communityPost.user.userNickname.as("userNickName"),
                communityPost.user.userFileName.as("userFileName"),
                communityPost.user.userFilePath.as("userFilePath"),
                communityPost.user.userFileSize.as("userFileSize"),
                communityPost.user.userFileUuid.as("userFileUuid"),
                communityPost.communityCategory.as("communityCategory"),
                communityPost.communityTitle.as("communityTitle"),
                communityPost.communityContent.as("communityContent"),
                communityPost.communityViewNumber.as("communityViewNumber"),
                communityPost.communityPostId.as("communityPostId"),
                communityPost.communityFilePath.as("communityFilePath"),
                communityPost.communityFileName.as("communityFileName"),
                communityPost.communityFileUuid.as("communityFileUuid"),
                communityPost.communityFileSize.as("communityFileSize"),
                ExpressionUtils.as(JPAExpressions
                        .select(QCommunityReply.communityReply.count())
                        .from(QCommunityReply.communityReply).where(QCommunityReply.communityReply.communityPost.communityPostId.eq(communityPost.communityPostId)), "communityReplyCount")
                ),
                communityPost.createDate.as("createDate"),
                communityPost.updatedDate.as("updatedDate")
        ).from(communityPost).fetch();


        log.info(pages.toString());


    }
}
