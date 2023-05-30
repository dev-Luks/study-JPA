package com.example.myoceanproject.entity;

import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.type.CommunityCategory;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "TBL_COMMUNITY_POST")
@Getter
@ToString(exclude = "user")
@NoArgsConstructor
//        (access = AccessLevel.PROTECTED)
public class CommunityPost extends Period{
    @Id
    @GeneratedValue
    private Long communityPostId; //PK
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user; //FK
    @NotNull
    @Enumerated(EnumType.STRING)
    private CommunityCategory communityCategory;
    private String communityTitle;
    @Column(length=10000)
    private String communityContent;
    private int communityViewNumber;

    @NotNull
    private String communityFilePath;
    @NotNull
    private String communityFileName;
    @NotNull
    private String communityFileUuid;
    @NotNull
    private Long communityFileSize;

    private int communityLikeNumber;

//  양방향
    public void setUser(User user){
        this.user = user;
    }

    @Builder
    public CommunityPost(Long communityPostId, User user, CommunityCategory communityCategory, String communityTitle, String communityContent, int communityViewNumber, String communityFilePath, String communityFileName, String communityFileUuid, Long communityFileSize, int communityLikeNumber) {
        this.communityPostId = communityPostId;
        this.user = user;
        this.communityCategory = communityCategory;
        this.communityTitle = communityTitle;
        this.communityContent = communityContent;
        this.communityViewNumber = communityViewNumber;
        this.communityFilePath = communityFilePath;
        this.communityFileName = communityFileName;
        this.communityFileUuid = communityFileUuid;
        this.communityFileSize = communityFileSize;
        this.communityLikeNumber = communityLikeNumber;
    }

//  커뮤니티 게시글은 카테고리와 제목, 내용이 수정 가능하고
//  조회수는 유저가 조회할 경우 계속 업데이트가 된다.
    public void update(CommunityPostDTO communityPostDTO){
        this.communityCategory = CommunityCategory.change(communityPostDTO.getCommunityCategory().toString());
        this.communityTitle = communityPostDTO.getCommunityTitle();
        this.communityContent = communityPostDTO.getCommunityContent();
        this.communityViewNumber = communityPostDTO.getCommunityViewNumber();
        this.communityFilePath = communityPostDTO.getCommunityFilePath();
        this.communityFileName = communityPostDTO.getCommunityFileName();
        this.communityFileUuid = communityPostDTO.getCommunityFileUuid();
        this.communityFileSize = communityPostDTO.getCommunityFileSize();
    }

//    변경을 자동감지해서 업데이트되므로,
//    조회할 경우 updateView메소드를 실행하여 조회수를 증가한다.
    public void updateReadCount(){
        this.communityViewNumber++;
    }
    public void updateLikePlusCount(){
        this.communityLikeNumber++;
    }

    public void updateLikeMinusCount(){
        this.communityLikeNumber--;
    }
}
