package com.example.myoceanproject.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "TBL_COMMUNITY_REPLY")
@Getter
@ToString(exclude = {"communityPost", "user"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityReply extends Period{

    @Id
    @GeneratedValue
    private Long communityReplyId; //PK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMUNITY_POST_ID")
    @NotNull
    @OnDelete(action=OnDeleteAction.CASCADE)
    private CommunityPost communityPost; //FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @NotNull
    @OnDelete(action=OnDeleteAction.CASCADE)
    private User user; //FK
    @NotNull
    private String communityReplyContent;



    public void setCommunityPost(CommunityPost communityPost){
        this.communityPost = communityPost;
    }
    public void setUser(User user){
        this.user = user;
    }


    @Builder
    public CommunityReply(User user, String communityReplyContent) {
        this.user = user;
        this.communityReplyContent = communityReplyContent;
    }

    public void update(String communityReplyContent){
        this.communityReplyContent = communityReplyContent;
    }

}
