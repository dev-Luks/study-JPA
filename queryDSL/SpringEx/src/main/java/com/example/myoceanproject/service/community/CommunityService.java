package com.example.myoceanproject.service.community;

import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.domain.GroupDTO;

import java.util.List;

public interface CommunityService {

    //  게시글 등록
    public void add(CommunityPostDTO communityPostDTO);

    //  게시글 목록
    public List<CommunityPostDTO> showCommunity();

    // 게시글 목록 처음에 다 나오게 하기
    public List<CommunityPostDTO> findAllByList(Long userId);

    //  게시글 조회
    public CommunityPostDTO find(Long communityPostId);

    //  게시글 수정
    public void update(CommunityPostDTO communityPostDTO);

    //  게시글 삭제
    public void delete(Long communityPostId);

    // 무한스크롤
    public List<CommunityPostDTO> selectScrollBoards(int page);

    // 무한스크롤(회원전용)
    List<CommunityPostDTO> selectScrollBoards(int page, Long userId);
}
