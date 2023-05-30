package com.example.myoceanproject.domain;

import com.example.myoceanproject.entity.CommunityPost;
import com.example.myoceanproject.entity.Period;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.type.CommunityCategory;
import com.querydsl.core.annotations.QueryProjection;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Data
@NoArgsConstructor
public class CommunityPostDTO {
    private Long userId;
    private String userNickName;
    private String userFileName;
    private String userFilePath;
    private Long userFileSize;
    private String userFileUuid;


    private String communityCategory;
    private String communityTitle;
    private String communityContent;
    private int communityViewNumber;

    private Long communityPostId;

    private String communityFilePath;

    private String communityFileName;

    private String communityFileUuid;

    private Long communityFileSize;

    private int communityReplyCount;

    private int communityLikeNumber;

    private String createDate;

    private String updatedDate;

    private List<CommunityPostDTO> postList;

    private int endPage;

    private boolean checkLike;

    @QueryProjection
    public CommunityPostDTO(Long communityPostId, Long userId, String userNickName, String userFileName, String userFilePath, Long userFileSize, String userFileUuid, CommunityCategory communityCategory, String communityTitle, String communityContent, String communityFilePath, String communityFileName, String communityFileUuid, Long communityFileSize, int communityViewNumber, int communityLikeNumber, LocalDateTime createDate, LocalDateTime updatedDate) {
        this.communityPostId = communityPostId;
        this.userId = userId;
        this.userNickName = userNickName;
        this.userFileName = userFileName;
        this.userFilePath = userFilePath;
        this.userFileSize = userFileSize;
        this.userFileUuid = userFileUuid;
        this.communityCategory = communityCategory.toString();
        this.communityTitle = communityTitle;
        this.communityContent = communityContent;
        this.communityFilePath = communityFilePath;
        this.communityFileName = communityFileName;
        this.communityFileUuid = communityFileUuid;
        this.communityFileSize = communityFileSize;
        this.communityViewNumber = communityViewNumber;
        this.communityLikeNumber = communityLikeNumber;
        this.createDate = createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.updatedDate = updatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }



    //  게시글 작성 시 게시글 제목, 카테고리, 내용이 처음으로 저장된다.
    public CommunityPost toEntity(){
        return CommunityPost.builder()
                .communityTitle(communityTitle)
                .communityCategory(CommunityCategory.change(communityCategory))
                .communityContent(communityContent)
                .communityFileName(communityFileName)
                .communityFilePath(communityFilePath)
                .communityFileUuid(communityFileUuid)
                .communityFileSize(communityFileSize)
                .build();
    }
}