package com.example.myoceanproject.domain;

import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.type.UserAccountStatus;
import com.example.myoceanproject.type.UserLoginMethod;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Component
@Data
@NoArgsConstructor
public class UserDTO {

    private Long userId;

    private String userEmail;
    private String userPassword;
    private String userNickname;

    private String userAccountStatus;

    private String userLoginMethod;
    //    private int totalPoint;
    private int userTotalPoint;
    private String userFileName;
    private String userFilePath;
    private Long userFileSize;
    private String userFileUuid;

    private String createDate;

    private String updatedDate;


    private int userPostCount;
    private int userReplyCount;
    private int badgeCount;

    private List<UserDTO> userList;
    private int endPage;
    private String userOauthId;

    @QueryProjection
    public UserDTO(Long userId, String userPassword, String userNickname, UserAccountStatus userAccountStatus, String userFileName, String userFilePath, Long userFileSize, String userFileUuid, String userEmail, UserLoginMethod userLoginMethod, int userTotalPoint, LocalDateTime createDate, LocalDateTime updatedDate,String userOauthId) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.userAccountStatus = userAccountStatus.toString();
        this.userFileName=userFileName;
        this.userFilePath=userFilePath;
        this.userFileSize=userFileSize;
        this.userFileUuid=userFileUuid;
        this.userEmail=userEmail;
        this.userLoginMethod=userLoginMethod.toString();
        this.userTotalPoint=userTotalPoint;
        this.createDate = createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.updatedDate = updatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.userOauthId=userOauthId;
    }

    public User toEntity(){
        return User.builder()
                .userPassword(userPassword)
                .userNickname(userNickname)
                .userEmail(userEmail)
                .userAccountStatus(UserAccountStatus.change(userAccountStatus))
                .userTotalPoint(userTotalPoint)
                .userOauthId(userOauthId)
                .build();
    }


}
