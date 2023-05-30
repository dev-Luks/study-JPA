package com.example.myoceanproject.embeddable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class GroupMemberLimit {
    private int maxMember;
    private int minMember;

    public void create(int maxMember, int minMember){
        this.maxMember=maxMember;
        this.minMember=minMember;
    }
}
