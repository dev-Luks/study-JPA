package com.example.myoceanproject.domain;

import com.example.myoceanproject.entity.Chatting;
import com.example.myoceanproject.entity.Period;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.type.MessageType;
import com.example.myoceanproject.type.ReadStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Data
@NoArgsConstructor
public class ChattingDTO {
//    보내는 사람 관련 유저 정보
    private Long senderUserId;
    private String senderUserNickName;
    private String senderUserFileName;
    private String senderUserFilePath;
    private Long senderUserFileSize;
    private String senderUserFileUuid;


//    그룹 관련
    private Long groupId;
    private String groupName;

//    보내는 사람의 groupMemberId
    private Long senderGroupMemberId;
//    받는 사람의 Group Member ID
    private Long receiverGroupMemberId;
//    채팅 관련
    private Long chattingId;
    private String chattingContent;

    private String chattingCreateTime;
    private String chattingCreateDate;

    private String messageType;

    private String imageSrc;


    @QueryProjection
    public ChattingDTO(Long chattingId, Long senderUserId, String senderUserNickName, String senderUserFileName, String senderUserFilePath, Long senderUserFileSize, String senderUserFileUuid, Long groupId, String groupName, Long senderGroupMemberId, String chattingContent, LocalDateTime createDate, MessageType messageType) {
        this.chattingId = chattingId;
        this.senderUserId = senderUserId;
        this.senderUserNickName = senderUserNickName;
        this.senderUserFileName = senderUserFileName;
        this.senderUserFilePath = senderUserFilePath;
        this.senderUserFileSize = senderUserFileSize;
        this.senderUserFileUuid = senderUserFileUuid;
        this.groupId = groupId;
        this.groupName = groupName;
        this.senderGroupMemberId = senderGroupMemberId;
        this.chattingContent = chattingContent;
        this.chattingCreateDate = createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.chattingCreateTime = createDate.format(DateTimeFormatter.ofPattern("HH:mm"));
        this.messageType = messageType.toString();

    }





    public Chatting toEntity(){
        return Chatting.builder()
                .chattingContent(chattingContent)
                .messageType(MessageType.change(messageType))
                .build();
    }
}
