package com.example.myoceanproject.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_GROUP_MEMBER")
@Getter
@ToString(exclude = {"user","group"})
@NoArgsConstructor/*(access = AccessLevel.PROTECTED)*/
public class GroupMember extends Period{

    @Id
    @GeneratedValue
    private Long groupMemberId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @NotNull
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    @NotNull
    private Group group;


    public void setUser(User user){
        this.user = user;
    }

    public void setGroup(Group group){
        this.group = group;
    }

}
