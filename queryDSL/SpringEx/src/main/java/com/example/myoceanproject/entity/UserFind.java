package com.example.myoceanproject.entity;

import com.example.myoceanproject.domain.UserFindDTO;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_FINDUSER")
@Getter
@ToString
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserFind{
    @Id @GeneratedValue
    private Long userFindid;
    @NotNull
    private Long userId;
    @NotNull
    private String userUuid;
    @NotNull
    private String userEmail;

    @CreatedDate
    private LocalDateTime userFindtime;

    @Builder
    public UserFind(Long userFindid,Long userId,String userUuid,String userEmail,LocalDateTime userFindtime){
        this.userFindid=userFindid;
        this.userId=userId;
        this.userUuid=userUuid;
        this.userEmail=userEmail;
        this.userFindtime=userFindtime;
    }

}
