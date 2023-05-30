package com.example.myoceanproject.repository.community.reply;

import com.example.myoceanproject.domain.CommunityReplyDTO;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.entity.CommunityPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//사용자 지정 레파지토리 인터페이스
public interface CommunityReplyCustomRepository {

    //    전체 댓글 삭제하는 메소드
    public void deleteByCommunityPost(CommunityPost communityPost);

    public List<CommunityReplyDTO> findByCommunityPost(CommunityPost communityPost);
    //   게시글 전체 댓글 갯수
    public Integer countReplyByCommunityPost(Long communityPostId);

    public List<CommunityReplyDTO> findAllById(Long communityPostId);

    public List<CommunityReplyDTO> findAll(Long communityPostId);

    //  전체 댓글 불러오기
    public Page<CommunityReplyDTO> findAllFree(Pageable pageable);

    //  전체 댓글 불러오기, 검색어
    public Page<CommunityReplyDTO> findAllFree(Pageable pageable, Criteria criteria);

    //    고민상담 댓글 전체
    public Page<CommunityReplyDTO> findAllByCategory(Pageable pageable);

    //    고민상담 댓글 전체, 검색어
    public Page<CommunityReplyDTO> findAllByCategory(Pageable pageable, Criteria criteria);

    public Integer countReplyByUser(Long userId);

    public CommunityReplyDTO findAllByDashboard();
}
