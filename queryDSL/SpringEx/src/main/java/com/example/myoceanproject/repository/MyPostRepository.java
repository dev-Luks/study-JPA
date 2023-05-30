package com.example.myoceanproject.repository;

import com.example.myoceanproject.entity.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MyPostRepository extends JpaRepository<CommunityPost, Long> {

//    public CommunityPost findTop1ByOrderByCommunityPostIdDesc();
//    @Query("select p.communityPostId from CommunityPost p where p.user.userId=:userId")
//    public Long findpostByuserid(@Param("userId") Long userid);
}
