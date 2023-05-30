package com.example.myoceanproject.repository;

import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.type.CommunityCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyPostCustomRepository {
    public Page<CommunityPostDTO> findAll(Pageable pageable,Long userId);
    public Page<CommunityPostDTO> findAll(Pageable pageable, Criteria criteria,Long userId);
    public Page<CommunityPostDTO> findAllByCategory(Pageable pageable, CommunityCategory communityCategory,Long userId);
    public Page<CommunityPostDTO> findAllByCategory(Pageable pageable, CommunityCategory communityCategory, Criteria criteria,Long userId);
}
