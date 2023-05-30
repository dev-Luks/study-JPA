package com.example.myoceanproject.repository.community.reply;

import com.example.myoceanproject.entity.CommunityReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityReplyRepository extends JpaRepository<CommunityReply, Long> {
    
}
