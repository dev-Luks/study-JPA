package com.example.myoceanproject.entity;

import com.example.myoceanproject.type.ReadStatus;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name = "TBL_CHATTING_STATUS")
@Getter
@ToString(exclude = {"receiverGroupMember"})
@NoArgsConstructor
//        (access = AccessLevel.PROTECTED)
public class ChattingStatus extends Period{

    @Id
    @GeneratedValue
    private Long ChattingStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_GROUP_MEMBER_ID")
    @NotNull
    private GroupMember receiverGroupMember;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHATTING_ID")
    @NotNull
    private Chatting chatting;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReadStatus readStatus;

    //  채팅을 읽는 순간 READ로 값이 변경되므로 update
    public void update(ReadStatus readStatus){
        this.readStatus = readStatus;
    }

    public void setReceiverGroupMember(GroupMember groupMember){
        this.receiverGroupMember = groupMember;
    }
    public void setChatting(Chatting chatting){this.chatting = chatting;}

    @Builder
    public ChattingStatus(ReadStatus readStatus) {
        this.readStatus = readStatus;
    }

}
