//  손호현, 메인 MainService
package com.example.myoceanproject.service.main;

import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.domain.QCommunityPostDTO;
import com.example.myoceanproject.domain.QGroupDTO;
import com.example.myoceanproject.entity.Group;
import com.example.myoceanproject.entity.QCommunityPost;
import com.example.myoceanproject.entity.QGroup;
import com.example.myoceanproject.type.CommunityCategory;
import com.example.myoceanproject.type.GroupStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static com.example.myoceanproject.entity.QGroup.group;
import static com.example.myoceanproject.entity.QCommunityPost.communityPost;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainService {
    private final JPAQueryFactory jpaQueryFactory;

//    모임 최근 게시글 4개
    public List<GroupDTO> showGroupList(){
        List<GroupDTO> list = jpaQueryFactory.select(new QGroupDTO(group.groupId,group.user.userId,
                group.user.userFileName,group.user.userFilePath,group.user.userFileSize,
                group.user.userFileUuid,group.user.userNickname,group.groupName,
                group.groupCategory,group.groupContent,group.groupPoint,
                group.groupOverSea,group.groupLocationName,group.groupLocation,
                group.groupLocationDetail,group.groupParkingAvailable,group.groupMoreInformation,
                group.groupLocationType,group.groupStatus,group.groupFilePath,
                group.groupFileName,group.groupFileUuid,group.groupFileSize,
                group.groupMemberLimit.maxMember,group.groupMemberLimit.minMember,group. createDate,
                group. updatedDate,group.reason
        )).from(group).where(group.groupStatus.eq(GroupStatus.APPROVED))
                .orderBy(group.groupId.desc()).limit(4l).fetch();
        return list;
    }

//    이야기 최근 게시글 4개
    public List<CommunityPostDTO> showCommunityList(){
        List<CommunityPostDTO> list = jpaQueryFactory.select(new QCommunityPostDTO(
                communityPost.communityPostId,
                communityPost.user.userId,
                communityPost.user.userNickname,
                communityPost.user.userFileName,
                communityPost.user.userFilePath,
                communityPost.user.userFileSize,
                communityPost.user.userFileUuid,
                communityPost.communityCategory,
                communityPost.communityTitle,
                communityPost.communityContent,
                communityPost.communityFilePath,
                communityPost.communityFileName,
                communityPost.communityFileUuid,
                communityPost.communityFileSize,
                communityPost.communityViewNumber,
                communityPost.communityLikeNumber,
                communityPost.createDate,
                communityPost.updatedDate
        )).from(communityPost)
                .orderBy(communityPost.communityPostId.desc()).limit(4l).fetch();
        return list;
    }

//    운동 최근 게시글 4개
    public List<CommunityPostDTO> showExerciseList(){
        List<CommunityPostDTO> list = jpaQueryFactory.select(new QCommunityPostDTO(communityPost.communityPostId,
                communityPost.user.userId,
                communityPost.user.userNickname,
                communityPost.user.userFileName,
                communityPost.user.userFilePath,
                communityPost.user.userFileSize,
                communityPost.user.userFileUuid,
                communityPost.communityCategory,
                communityPost.communityTitle,
                communityPost.communityContent,
                communityPost.communityFilePath,
                communityPost.communityFileName,
                communityPost.communityFileUuid,
                communityPost.communityFileSize,
                communityPost.communityViewNumber,
                communityPost.communityLikeNumber,
                communityPost.createDate,
                communityPost.updatedDate)).from(communityPost)
                .where(communityPost.communityCategory.eq(CommunityCategory.EXERCISE))
                .orderBy(communityPost.communityPostId.desc()).limit(4l).fetch();
        return list;
    }

//    요리 최근 게시글 4개
    public List<CommunityPostDTO> showCookList(){
        List<CommunityPostDTO> list = jpaQueryFactory.select(new QCommunityPostDTO(communityPost.communityPostId,
                communityPost.user.userId,
                communityPost.user.userNickname,
                communityPost.user.userFileName,
                communityPost.user.userFilePath,
                communityPost.user.userFileSize,
                communityPost.user.userFileUuid,
                communityPost.communityCategory,
                communityPost.communityTitle,
                communityPost.communityContent,
                communityPost.communityFilePath,
                communityPost.communityFileName,
                communityPost.communityFileUuid,
                communityPost.communityFileSize,
                communityPost.communityViewNumber,
                communityPost.communityLikeNumber,
                communityPost.createDate,
                communityPost.updatedDate)).from(communityPost)
                .where(communityPost.communityCategory.eq(CommunityCategory.COOK))
                .orderBy(communityPost.communityPostId.desc()).limit(4l).fetch();
        return list;
    }

//    고민 최근 게시글 4개
    public List<CommunityPostDTO> showAnonymousList(){
        List<CommunityPostDTO> list = jpaQueryFactory.select(new QCommunityPostDTO(communityPost.communityPostId,
                communityPost.user.userId,
                communityPost.user.userNickname,
                communityPost.user.userFileName,
                communityPost.user.userFilePath,
                communityPost.user.userFileSize,
                communityPost.user.userFileUuid,
                communityPost.communityCategory,
                communityPost.communityTitle,
                communityPost.communityContent,
                communityPost.communityFilePath,
                communityPost.communityFileName,
                communityPost.communityFileUuid,
                communityPost.communityFileSize,
                communityPost.communityViewNumber,
                communityPost.communityLikeNumber,
                communityPost.createDate,
                communityPost.updatedDate)).from(communityPost)
                .where(communityPost.communityCategory.eq(CommunityCategory.COUNSELING))
                .orderBy(communityPost.communityPostId.desc()).limit(4l).fetch();
        return list;
    }

//    책,영화 최근 게시글 3개
    public List<CommunityPostDTO> showReviewList(){
        List<CommunityPostDTO> list = jpaQueryFactory.select(new QCommunityPostDTO(communityPost.communityPostId,
                communityPost.user.userId,
                communityPost.user.userNickname,
                communityPost.user.userFileName,
                communityPost.user.userFilePath,
                communityPost.user.userFileSize,
                communityPost.user.userFileUuid,
                communityPost.communityCategory,
                communityPost.communityTitle,
                communityPost.communityContent,
                communityPost.communityFilePath,
                communityPost.communityFileName,
                communityPost.communityFileUuid,
                communityPost.communityFileSize,
                communityPost.communityViewNumber,
                communityPost.communityLikeNumber,
                communityPost.createDate,
                communityPost.updatedDate)).from(communityPost)
                .where(communityPost.communityCategory.eq(CommunityCategory.MOVIE).or(communityPost.communityCategory.eq(CommunityCategory.BOOK)))
                .orderBy(communityPost.communityPostId.desc()).limit(3l).fetch();
        return list;
    }

}
