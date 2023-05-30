package com.example.myoceanproject.domain;

import com.example.myoceanproject.entity.CommunityPost;
import com.example.myoceanproject.entity.CommunityReply;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.type.CommunityCategory;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Data
@NoArgsConstructor
public class CommunityReplyDTO {
    private Long userId;
    private String userNickName;
    private String userFileName;
    private String userFilePath;
    private Long userFileSize;
    private String userFileUuid;

    private Long communityPostId;

    private String communityPostTitle;

    private CommunityCategory communityCategory;

    private Long writerId;

    private Long communityReplyId;
    private String communityReplyContent;

    private String createDate;

    private String updatedDate;

    private List<CommunityReplyDTO> replyList;

    private int endPage;


    @QueryProjection
    public CommunityReplyDTO(Long userId, String userNickName, String userFileName, String userFilePath, Long userFileSize, String userFileUuid, Long communityPostId, String communityPostTitle, Long communityReplyId ,String communityReplyContent, LocalDateTime createDate, LocalDateTime updatedDate, Long writerId) {
        this.userId = userId;
        this.userNickName = userNickName;
        this.userFileName = userFileName;
        this.userFilePath = userFilePath;
        this.userFileSize = userFileSize;
        this.userFileUuid = userFileUuid;
        this.communityPostId = communityPostId;
        this.communityPostTitle = communityPostTitle;
        this.communityReplyId = communityReplyId;
        this.communityReplyContent = communityReplyContent;
        this.createDate = createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.updatedDate = updatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.writerId = writerId;
    }
    @QueryProjection
    public CommunityReplyDTO(Long userId, String userNickName, String userFileName, String userFilePath, Long userFileSize, String userFileUuid, Long communityPostId, String communityPostTitle, Long communityReplyId ,String communityReplyContent, LocalDateTime createDate, LocalDateTime updatedDate, CommunityCategory communityCategory) {
        this.userId = userId;
        this.userNickName = userNickName;
        this.userFileName = userFileName;
        this.userFilePath = userFilePath;
        this.userFileSize = userFileSize;
        this.userFileUuid = userFileUuid;
        this.communityPostId = communityPostId;
        this.communityPostTitle = communityPostTitle;
        this.communityReplyId = communityReplyId;
        this.communityReplyContent = communityReplyContent;
        this.createDate = createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.updatedDate = updatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.communityCategory = communityCategory;
    }

    public CommunityReply toEntity(){

       return CommunityReply.builder()
               .communityReplyContent(communityReplyContent)
               .build();
   }
}
