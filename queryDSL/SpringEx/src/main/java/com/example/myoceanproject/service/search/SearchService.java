package com.example.myoceanproject.service.search;


import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.domain.QCommunityPostDTO;
import com.example.myoceanproject.domain.QGroupDTO;
import com.example.myoceanproject.entity.*;
import com.example.myoceanproject.repository.community.like.CommunityLikeCustomRepository;
import com.example.myoceanproject.repository.community.post.CommunityPostRepository;
import com.example.myoceanproject.repository.community.reply.CommunityReplyCustomRepository;
import com.example.myoceanproject.repository.community.reply.CommunityReplyRepository;
import com.example.myoceanproject.type.CommunityCategory;
import com.example.myoceanproject.type.GroupStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.myoceanproject.entity.QCommunityPost.*;
import static com.example.myoceanproject.entity.QGroup.group;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    private final JPAQueryFactory jpaQueryFactory;
    private final CommunityReplyCustomRepository replyCustomRepository;
    private final CommunityLikeCustomRepository likeCustomRepository;

//    커뮤니티 무한스크롤
    @Transactional
    public Slice<CommunityPostDTO> showList(Pageable pageable, String search, ArrayList<CommunityCategory> categoryList, HttpSession session){

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(categoryList.size() != 0 ){
            booleanBuilder.and(communityPost.communityCategory.eq(categoryList.get(0)));
            if(categoryList.size() >1){
                for (int i = 1 ; i<categoryList.size(); i++){
                    booleanBuilder.or(communityPost.communityCategory.eq(categoryList.get(i)));
                }
            }
        }

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
        )).from(communityPost).where(communityPost.communityTitle.like("%"+search+"%").and(booleanBuilder))
        .orderBy(communityPost.communityPostId.desc())
        .offset(pageable.getOffset()).limit(pageable.getPageSize()+1).fetch();





        Long userId = (Long)session.getAttribute("userId");
        if(userId != null){
            list.stream().forEach(communityPostDTO -> {
                communityPostDTO.setCommunityReplyCount(replyCustomRepository.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
                communityPostDTO.setCheckLike(likeCustomRepository.findByCommunityPostAndUser(userId,communityPostDTO.getCommunityPostId()));
            });
        }else{
            list.stream().forEach(communityPostDTO -> {
                communityPostDTO.setCommunityReplyCount(replyCustomRepository.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
            });
        }



        ArrayList<CommunityPostDTO> content = (ArrayList<CommunityPostDTO>)list;
        boolean hasNext = false;
        if(content.size() > pageable.getPageSize()){
            content.remove(pageable.getPageSize());
            hasNext=true;
        }
        return new SliceImpl<>(content,pageable,hasNext);

    }

//    모임 페이징처리
    @Transactional
    public Page<GroupDTO> showList(Pageable pageable, String keyword){
        List<GroupDTO> list = jpaQueryFactory.select(new QGroupDTO(group.groupId,group.user.userId,group.user.userFileName,group.user.userFilePath,group.user.userFileSize,group.user.userFileUuid,group.user.userNickname,group.groupName,group.groupCategory
                ,group.groupContent,group.groupPoint,group.groupOverSea,group.groupLocationName,group.groupLocation,group.groupLocationDetail,group.groupParkingAvailable,
                group.groupMoreInformation,group.groupLocationType,group.groupStatus,group.groupFilePath,group.groupFileName,group.groupFileUuid,group.groupFileSize
                ,group.groupMemberLimit.maxMember,group.groupMemberLimit.minMember,group. createDate,group. updatedDate,group.reason
        )).from(group).where(group.groupStatus.eq(GroupStatus.APPROVED).and(group.groupName.like("%"+keyword+"%"))).orderBy(group.groupId.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

        long total = jpaQueryFactory.selectFrom(group)
                .where(group.groupStatus.eq(GroupStatus.APPROVED).and(group.groupName.like("%"+keyword+"%")))
                .fetch().size();


        return new PageImpl<>(list, pageable, total);
    }


}
