package com.example.myoceanproject.domain;

import com.example.myoceanproject.entity.UserFind;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@NoArgsConstructor
public class UserFindDTO {
    private Long userId;
    private String userUuid;
    private String userEmail;
    private LocalDateTime userFindtime;

    @QueryProjection
    public UserFindDTO(Long userId,String userUuid,String userEmail,LocalDateTime userFindtime){
        this.userId=userId;
        this.userUuid=userUuid;
        this.userEmail=userEmail;
        this.userFindtime=userFindtime;
    }

    public UserFind toentity(){
        return UserFind.builder()
                .userId(userId)
                .userUuid(userUuid)
                .userEmail(userEmail)
                .userFindtime(userFindtime)
                .build();
    }

}
