package com.example.myoceanproject.repository;

import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("select u from User u where u.userEmail=:userEmail")
//    public User searchUser(@Param("userEmail") String userEmail);

    public User findByUserEmail(String email);
    public Optional<User> findByUserOauthId(String id);
}
