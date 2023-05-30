package com.example.myoceanproject.service.community;

import com.example.myoceanproject.aspect.annotation.ReplyAlarm;
import com.example.myoceanproject.domain.CommunityReplyDTO;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.entity.CommunityReply;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.repository.community.post.CommunityPostRepository;
import com.example.myoceanproject.repository.community.reply.CommunityReplyRepository;
import com.example.myoceanproject.repository.community.reply.CommunityReplyRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommunityReplyService {
    @Autowired
    private CommunityReplyRepositoryImpl replyRepositoryImpl;
    @Autowired
    private CommunityReplyRepository replyRepository;
    @Autowired
    private CommunityPostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

//  전체 목록 출력
    public List<CommunityReplyDTO> findAllByCommunityId(Long communityPostId){
        List<CommunityReplyDTO> communityReplyDTO = replyRepositoryImpl.findAllById(communityPostId);
        return communityReplyDTO;
    }

//  삭제
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long communityReplyId){replyRepository.deleteById(communityReplyId);}

//  커뮤니티 아이디로 해당 글 정보 출력
    public Page<CommunityReplyDTO> showReply(Pageable pageable, Criteria criteria){
        return criteria.getKeyword().equals("null") ? replyRepositoryImpl.findAllFree(pageable) : replyRepositoryImpl.findAllFree(pageable,criteria);
    }
//    고민상담용
    public Page<CommunityReplyDTO> showReplyByCounseling(Pageable pageable, Criteria criteria){
        return criteria.getKeyword().equals("null") ? replyRepositoryImpl.findAllByCategory(pageable) : replyRepositoryImpl.findAllByCategory(pageable,criteria);
    }

    public void remove(Long communityReplyId){
        replyRepository.deleteById(communityReplyId);
    }

//    댓글 등록
    @ReplyAlarm
    @Transactional(rollbackFor = Exception.class)
    public void add(CommunityReplyDTO communityReplyDTO) {
        CommunityReply communityReply = communityReplyDTO.toEntity();
        communityReply.setUser(userRepository.findById(communityReplyDTO.getUserId()).get());
        communityReply.setCommunityPost(postRepository.findById(communityReplyDTO.getCommunityPostId()).get());
        replyRepository.save(communityReply);
    }

}
