package com.example.myoceanproject.entity;

import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.type.UserAccountStatus;
import com.example.myoceanproject.type.UserLoginMethod;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TBL_USER")
@Getter
@ToString
@NoArgsConstructor
//        (access = AccessLevel.PROTECTED)
public class User extends Period {

    @Id
    @GeneratedValue
    private Long userId; //PK
    @NotNull
    private String userEmail;
    private String userPassword;
    @NotNull
    private String userNickname;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserAccountStatus userAccountStatus; //Enum으로 사용
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserLoginMethod userLoginMethod;

    @NotNull
    private int userTotalPoint;

    private String userFileName;
    private String userFilePath;
    private Long userFileSize;
    private String userFileUuid;

    private String userOauthId;

    //    그룹 테이블 양방향
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Group> groups;

    //    투두리스트 테이블 양방향
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<ToDoList> toDoLists;

    //    다이어리 테이블 양방향
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Diary> diaries;

    //    문의사항 테이블 양방향(나의 질문)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Ask> asks;

    //    커뮤니티 포스트 양방향
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CommunityPost> communityPosts;

    //    퀘스트 달성 테이블과 양방향
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<QuestAchievement> questAchievements;

    //    알람테이블과 양방향
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Alarm> alarms;

    @Builder
    public User(String userPassword, String userNickname, String userEmail,UserAccountStatus userAccountStatus,UserLoginMethod userLoginMethod, int userTotalPoint, String userFileName, String userFilePath, Long userFileSize, String userFileUuid,String userOauthId) {
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.userAccountStatus = userAccountStatus;
        this.userLoginMethod = userLoginMethod;
        this.userTotalPoint = userTotalPoint;
        this.userFileUuid = userFileUuid;
        this.userFileName = userFileName;
        this.userFilePath = userFilePath;
        this.userFileSize = userFileSize;
        this.userOauthId=userOauthId;
    }

    //  가입 후 유저 비밀번호와 닉네임, 유저 포인트 변경 및 업데이트가 가능하다.
//  관리자가 회원을 정지시키거나 정지를 해제하여 AccountStatus를 업데이트할 수 있다.
    public void updateNicknameFile(UserDTO userDTO) {
        this.userNickname = userDTO.getUserNickname();
        this.userFileUuid = userDTO.getUserFileUuid();
        this.userFileName = userDTO.getUserFileName();
        this.userFilePath = userDTO.getUserFilePath();
        this.userFileSize = userDTO.getUserFileSize();
    }
    public void updatePassword(UserDTO userDTO) {
        this.userPassword = userDTO.getUserPassword();
    }
    public void updateManager(UserAccountStatus userAccountStatus) {
        this.userAccountStatus = userAccountStatus;
    }

    public void updateUserTotalPoint(int userTotalPoint){this.userTotalPoint=userTotalPoint;}

    public void setUserId(Long userId){
        this.userId = userId;
    }
    public void setUserLoginMethod(UserLoginMethod userLoginMethod){
        this.userLoginMethod = userLoginMethod;
    }
    public void setUserNickname(String userNickname){
        this.userNickname = userNickname;
    }

}