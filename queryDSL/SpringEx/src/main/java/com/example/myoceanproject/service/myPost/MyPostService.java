package com.example.myoceanproject.service.myPost;

import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.repository.MyPostRepository;
import com.example.myoceanproject.repository.MyPostRepositoryImpl;
import com.example.myoceanproject.repository.community.post.CommunityPostRepository;
import com.example.myoceanproject.repository.community.post.CommunityPostRepositoryImpl;
import com.example.myoceanproject.type.CommunityCategory;
import com.example.myoceanproject.domain.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MyPostService {
    @Autowired
    private MyPostRepositoryImpl myPostRepositoryImpl;
    @Autowired
    private MyPostRepository myPostRepository;

    public Page<CommunityPostDTO> showCounseling(Pageable pageable, CommunityCategory communityCategory, Criteria criteria,Long userId){
        return criteria.getKeyword() == null ? myPostRepositoryImpl.findAllByCategory(pageable, communityCategory,userId) : myPostRepositoryImpl.findAllByCategory(pageable, communityCategory,criteria,userId);
    }

    public Page<CommunityPostDTO> showPost(Pageable pageable, Criteria criteria,Long userId){
        return criteria.getKeyword() == null ? myPostRepositoryImpl.findAll(pageable,userId) : myPostRepositoryImpl.findAll(pageable,criteria,userId);
    }
}
