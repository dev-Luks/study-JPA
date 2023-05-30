package com.example.myoceanproject.repository.community.like;

import com.example.myoceanproject.entity.CommunityLike;
import com.example.myoceanproject.entity.CommunityPost;

import java.util.List;

//사용자 지정 레파지토리 인터페이스
public interface CommunityLikeCustomRepository {
//    포스트 객체로 포스트 파일을 찾아내서 리스트로 반환하는 메소드
    public boolean findByCommunityPostAndUser(Long userId, Long postId);

    //    포스트 객체로 포스트 파일을 찾아내서 전체 삭제하는 메소드
    public void deleteByCommunityPostAndUser(Long userId, Long postId);

    //    전체 라이크 삭제하는 메소드
    public void deleteByCommunityPost(CommunityPost communityPost);

    //    전체 라이크 리스트 반환하는 메소드
    public List<CommunityLike> findByCommunityPost(CommunityPost communityPost);

}
