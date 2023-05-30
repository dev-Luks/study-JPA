package com.example.myoceanproject.domain;

import com.example.myoceanproject.entity.Group;
import com.example.myoceanproject.entity.GroupMember;
import com.example.myoceanproject.type.GroupStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class GroupMemberDTO {

    private Long groupMemberId;
    private Long groupId;
    private Long userId;

    @QueryProjection
    public GroupMemberDTO(Long groupMemberId, Long groupId, Long userId) {
        this.groupMemberId=groupMemberId;
        this.groupId=groupId;
        this.userId=userId;
    }

//    public GroupMember toEntity(){
//        return Group.builder()
//                .groupMember
//                .build();
//    }
}
