package com.example.myoceanproject.entity;

import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.embeddable.GroupMemberLimit;
import com.example.myoceanproject.type.CommunityCategory;
import com.example.myoceanproject.type.GroupLocationType;
import com.example.myoceanproject.type.GroupStatus;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_GROUP")
@Getter
@ToString(exclude = "user")
@NoArgsConstructor/*(access = AccessLevel.PROTECTED)*/
public class Group extends Period{

    @Id
    @GeneratedValue
    private Long groupId; //PK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @NotNull
    private User user; //FK
    @NotNull
    private String groupName;
    @NotNull//직접 입력임. 선택x
    @Column(length=10000)
    private String groupContent;
    @NotNull
    private String groupCategory;
    @NotNull
    private int groupPoint;
    private String groupOverSea;
    private String groupLocationName;
    private String groupLocation;
    private String groupLocationDetail;
    private String groupParkingAvailable;
    private String groupMoreInformation;
    @NotNull
    private String groupFilePath;
    @NotNull
    private String groupFileName;
    @NotNull
    private String groupFileUuid;
    @NotNull
    private Long groupFileSize;
    @NotNull
    @Enumerated(EnumType.STRING)
    private GroupLocationType groupLocationType; //Enum
    @NotNull
    @Enumerated(EnumType.STRING)
    private GroupStatus groupStatus; // Enum

    private String reason;


    @Embedded
    private GroupMemberLimit groupMemberLimit;

//  양방향
    public void setUser(User user){
        this.user = user;
    }

    @Builder
    public Group(User user, String groupName, String groupCategory, String groupContent, int groupPoint, String groupOverSea, String groupLocationName, String groupLocation, String groupLocationDetail, String groupParkingAvailable, String groupMoreInformation, GroupLocationType groupLocationType, GroupStatus groupStatus, GroupMemberLimit groupMemberLimit, String groupFileName, String groupFilePath, Long groupFileSize, String groupFileUuid, String reason) {
        this.user = user;
        this.groupName = groupName;
        this.groupCategory = groupCategory;
        this.groupContent = groupContent;
        this.groupPoint = groupPoint;
        this.groupOverSea = groupOverSea;
        this.groupLocationName = groupLocationName;
        this.groupLocation = groupLocation;
        this.groupLocationDetail = groupLocationDetail;
        this.groupParkingAvailable = groupParkingAvailable;
        this.groupMoreInformation = groupMoreInformation;
        this.groupLocationType = groupLocationType;
        this.groupStatus = groupStatus;
        this.groupMemberLimit = groupMemberLimit;
        this.groupFileName = groupFileName;
        this.groupFilePath = groupFilePath;
        this.groupFileUuid = groupFileUuid;
        this.groupFileSize = groupFileSize;
        this.reason = reason;
    }

//  모임의 경우 모든 내용을 수정할 수 있기 때문에 Builder와 동일하게 update가 들어간다.
//  파일의 경우, 삭제 후 추가기 때문에 update에 포함되지 않는다.
    public void update(GroupDTO groupDTO) {
        groupMemberLimit.setMaxMember(groupDTO.getMaxMember());
        groupMemberLimit.setMinMember(groupDTO.getMinMember());

        this.groupName = groupDTO.getGroupName();
        this.groupCategory = groupDTO.getGroupCategory();
        this.groupContent = groupDTO.getGroupContent();
        this.groupPoint = groupDTO.getGroupPoint();
        this.groupOverSea = groupDTO.getGroupOverSea();
        this.groupLocationName = groupDTO.getGroupLocationName();
        this.groupLocation = groupDTO.getGroupLocation();
        this.groupLocationDetail = groupDTO.getGroupLocationDetail();
        this.groupParkingAvailable=groupDTO.getGroupParkingAvailable();
        this.groupMoreInformation=groupDTO.getGroupMoreInformation();
        this.groupStatus = GroupStatus.change(groupDTO.getGroupStatus());
        this.groupFileSize = groupDTO.getGroupFileSize();
        this.groupFileName = groupDTO.getGroupFileName();
        this.groupFilePath = groupDTO.getGroupFilePath();
        this.groupFileUuid = groupDTO.getGroupFileUuid();
        this.reason = groupDTO.getReason();
    }
    public void updateStatus(GroupStatus status){
        this.groupStatus = status;
    }

}