package com.example.myoceanproject.service.community;

import com.example.myoceanproject.aspect.annotation.LikeAlarm;
import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.entity.CommunityLike;
import com.example.myoceanproject.entity.CommunityPost;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.repository.community.like.CommunityLikeRepository;
import com.example.myoceanproject.repository.community.like.CommunityLikeRepositoryImpl;
import com.example.myoceanproject.repository.community.post.CommunityPostRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CommunityLikeService {

    private final JPAQueryFactory queryFactory;
    private final CommunityLikeRepositoryImpl communityLikeRepositoryImpl;

    private final CommunityLikeRepository communityLikeRepository;
    private final UserRepository userRepository;
    private final CommunityPostRepository communityPostRepository;

//  해당 게시글을 좋아요 눌렀는지 확인
    @Transactional(rollbackFor = Exception.class)
    public boolean checkLike(Long userId, Long communityPostId){
        return communityLikeRepositoryImpl.findByCommunityPostAndUser(userId, communityPostId);
    }


//  Like테이블에 좋아요 누른 유저 추가
    @Transactional(rollbackFor = Exception.class)
    @LikeAlarm
    public void addLike(Long userId, Long communityPostId){
        CommunityLike communityLike = new CommunityLike();
        User user = userRepository.findById(userId).get();
        CommunityPost communityPost = communityPostRepository.findById(communityPostId).get();

        communityLike.setUser(user);
        communityLike.setCommunityPost(communityPost);
        communityLikeRepository.save(communityLike);
    }



//  Like테이블에 좋아요 삭제
    @Transactional(rollbackFor = Exception.class)
    public void minusLike(Long userId, Long communityPostId){
        communityLikeRepositoryImpl.deleteByCommunityPostAndUser(userId, communityPostId);
    }
}
