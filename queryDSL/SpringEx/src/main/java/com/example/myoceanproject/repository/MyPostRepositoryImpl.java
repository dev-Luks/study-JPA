//  손호현, 나의공간 MyPostRepositoryImpl
package com.example.myoceanproject.repository;

import com.example.myoceanproject.domain.*;
import com.example.myoceanproject.repository.community.reply.CommunityReplyRepositoryImpl;
import com.example.myoceanproject.type.CommunityCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.myoceanproject.entity.QCommunityPost.communityPost;

@Repository
@RequiredArgsConstructor
public class MyPostRepositoryImpl implements MyPostCustomRepository {
    @Autowired
    private CommunityReplyRepositoryImpl replyRepositoryImpl ;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<CommunityPostDTO> findAllByCategory(Pageable pageable, CommunityCategory communityCategory,Long userId){
        List<CommunityPostDTO> posts = jpaQueryFactory.select(new QCommunityPostDTO(
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
        ))
                .from(communityPost)
                .where(communityPost.communityCategory.eq(communityCategory).and(communityPost.user.userId.eq(userId)))
                .orderBy(communityPost.communityPostId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        posts.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
        });
        long total = jpaQueryFactory.selectFrom(communityPost)
                .where(communityPost.communityCategory.eq(communityCategory).and(communityPost.user.userId.eq(userId)))
                .fetch().size();

        return new PageImpl<>(posts, pageable, total);
    }
    @Override
    public Page<CommunityPostDTO> findAllByCategory(Pageable pageable, CommunityCategory communityCategory, Criteria criteria,Long userId){
        List<CommunityPostDTO> posts = jpaQueryFactory.select(new QCommunityPostDTO(
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
        ))
                .from(communityPost)
                .where(communityPost.communityCategory.eq(communityCategory).and(communityPost.user.userId.eq(userId)))
                .orderBy(communityPost.communityPostId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        posts.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
        });
        long total = jpaQueryFactory.selectFrom(communityPost)
                .where(communityPost.communityCategory.eq(communityCategory).and(communityPost.user.userId.eq(userId)))
                .fetch().size();

        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public Page<CommunityPostDTO> findAll(Pageable pageable,Long userId){
        List<CommunityPostDTO> posts = jpaQueryFactory.select(new QCommunityPostDTO(
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
        ))
                .from(communityPost)
                .where(communityPost.communityCategory.ne(CommunityCategory.COUNSELING).and(communityPost.user.userId.eq(userId)))
                .orderBy(communityPost.communityPostId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        posts.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
        });
        long total = jpaQueryFactory.selectFrom(communityPost)
                .where(communityPost.communityCategory.ne(CommunityCategory.COUNSELING).and(communityPost.user.userId.eq(userId)))
                .fetch().size();

        return new PageImpl<>(posts, pageable, total);
    }
    @Override
    public Page<CommunityPostDTO> findAll(Pageable pageable, Criteria criteria,Long userId){
//        검색어가 있는 경우
        List<CommunityPostDTO> posts = jpaQueryFactory.select(new QCommunityPostDTO(
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
        ))
                .from(communityPost)
                .where(communityPost.communityTitle.contains(criteria.getKeyword()).and(communityPost.communityCategory.ne(CommunityCategory.COUNSELING)).and(communityPost.user.userId.eq(userId)))
                .orderBy(communityPost.communityPostId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        posts.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
        });
        long total = jpaQueryFactory.selectFrom(communityPost)
                .where(communityPost.communityTitle.contains(criteria.getKeyword()).and(communityPost.communityCategory.ne(CommunityCategory.COUNSELING)).and(communityPost.user.userId.eq(userId)))
                .fetch().size();

        return new PageImpl<>(posts, pageable, total);
    }

//    @Override
//    public void deleteByPost(CommunityPost post){
////        삭제는 어짜피 entity로 진행되기 때문
//        List<CommunityFileDTO> postFiles = fileRepositoryImpl.findByCommunityPost(post);
//        List<CommunityLike> likes = likeRepositoryImpl.findByCommunityPost(post);
//        List<CommunityReplyDTO> replies = replyRepositoryImpl.findByCommunityPost(post);
//
//        if(!postFiles.isEmpty()) {
////        전체 삭제 메소드를 실행하여 포스트와 관련된 파일을 전체 삭제한다.
//            fileRepositoryImpl.deleteByCommunityPost(post);
//        }
////        만약 포스트객체에 라이크가 있다면?
//        if(!likes.isEmpty()) {
////        전체 삭제 메소드를 실행하여 포스트와 관련된 파일을 전체 삭제한다.
//            likeRepositoryImpl.deleteByCommunityPost(post);
//        }
////        댓글이 있다면?
//        if(!replies.isEmpty()) {
////        전체 삭제 메소드를 실행하여 포스트와 관련된 파일을 전체 삭제한다.
//            replyRepositoryImpl.deleteByCommunityPost(post);
//        }
//
//    }
//
//    @Override
//    public Integer countPostByUser(Long userId){
//        return Math.toIntExact(jpaQueryFactory.select(communityPost.communityPostId.count())
//                .from(communityPost)
//                .where(communityPost.user.userId.eq(userId))
//                .fetchFirst());
//    }


}
