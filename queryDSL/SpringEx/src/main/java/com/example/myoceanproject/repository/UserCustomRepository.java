package com.example.myoceanproject.repository;

import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.type.UserAccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

// 유저 엔티티 사용자 지정 레포지토리
public interface UserCustomRepository {
    public int findCountByemail(String email);
    public List<UserDTO> findAllByActive();

    public Page<UserDTO> findAll(Pageable pageable);
    public Page<UserDTO> findAll(Pageable pageable, Criteria criteria);

    public Page<UserDTO> findAllByStatus(Pageable pageable, UserAccountStatus userAccountStatus);
    public Page<UserDTO> findAllByStatus(Pageable pageable, Criteria criteria, UserAccountStatus userAccountStatus);

    public UserDTO findUserById(Long userId);

    public UserDTO findAllByDashboard();

    public UserDTO findByUserId(Long userId);
}
