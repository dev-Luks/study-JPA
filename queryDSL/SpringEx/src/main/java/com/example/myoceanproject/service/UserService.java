package com.example.myoceanproject.service;

import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.repository.UserRepositoryImpl;
import com.example.myoceanproject.type.UserAccountStatus;
import com.example.myoceanproject.type.UserLoginMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryImpl userRepositoryImpl;

    private final UserRepository userRepository;

    public Page<UserDTO> showAllUser(Pageable pageable, Criteria criteria) {
        return criteria.getKeyword().equals("null") ? userRepositoryImpl.findAll(pageable) : userRepositoryImpl.findAll(pageable, criteria);
    }
    public Page<UserDTO> showUserByStatus(Pageable pageable, UserAccountStatus userAccountStatus, Criteria criteria) {
        return criteria.getKeyword().equals("null") ? userRepositoryImpl.findAllByStatus(pageable, userAccountStatus) : userRepositoryImpl.findAllByStatus(pageable, criteria, userAccountStatus);
    }
    public void modify(UserDTO userDTO){
        userRepository.findById(userDTO.getUserId()).get().updateManager(UserAccountStatus.change(userDTO.getUserAccountStatus()));
    }

    public UserDTO findUser(Long userId) {
        UserDTO userDTO = userRepositoryImpl.findByUserId(userId);
        return userDTO;
    }

    public void saveUser(UserDTO userDTO){
        User user=userDTO.toEntity();
        user.setUserLoginMethod(UserLoginMethod.GENERAL);
        userRepository.save(user);
    }

}
