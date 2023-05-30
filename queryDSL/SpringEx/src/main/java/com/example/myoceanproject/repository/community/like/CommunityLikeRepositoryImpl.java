package com.example.myoceanproject.repository.community.like;

import com.example.myoceanproject.entity.CommunityLike;
import com.example.myoceanproject.entity.CommunityPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.myoceanproject.entity.QCommunityLike.communityLike;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommunityLikeRepositoryImpl implements CommunityLikeCustomRepository{
//사용자 지정 레파지토리 Impl(구현)

    private final JPAQueryFactory queryFactory;

    //        유저번호와 포스트번호로 라이크여부를 가려주는 메소드
    //        좋아요를 하지 않았으면 true, 좋아요를 했으면 false
    @Override
    public boolean findByCommunityPostAndUser(Long userId, Long postId ){
      return queryFactory.selectFrom(communityLike).where(communityLike.communityPost.communityPostId.eq(postId).and(communityLike.userId.eq(userId))).fetchOne() == null;
    }

    //        유저번호와 포스트번호로 라이크를 삭제하는 메소드
    @Override
    public void deleteByCommunityPostAndUser(Long userId, Long postId){
        queryFactory.delete(communityLike).where(communityLike.communityPost.communityPostId.eq(postId).and(communityLike.userId.eq(userId))).execute();
    }

//    해당포스트의 전체 라이크 삭제
    @Override
    public void deleteByCommunityPost(CommunityPost communityPost) {
        queryFactory.delete(communityLike)
                .where(communityLike.communityPost.communityPostId.eq(communityPost.getCommunityPostId()))
                .execute();
    }

//    해당포스트의 전체 라이크 가져오기
    @Override
    public List<CommunityLike> findByCommunityPost(CommunityPost communityPost){
        return queryFactory.selectFrom(communityLike).where(communityLike.communityPost.communityPostId.eq(communityPost.getCommunityPostId())).fetch();
    }

//    해당 회원이 좋아요를 누른 갯수
    public int countLikeByUserId(Long userId){
        return Math.toIntExact(queryFactory.select(communityLike.communityLikeId.count()).from(communityLike)
                .where(communityLike.userId.eq(userId)).fetchFirst());
    }
}
