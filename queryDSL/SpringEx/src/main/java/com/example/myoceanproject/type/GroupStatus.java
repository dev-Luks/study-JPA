package com.example.myoceanproject.type;

public enum GroupStatus {
    //    승인, 승인 거절, 승인 대기
    APPROVED("승인완료"),DISAPPROVED("승인거절"), WAITING("승인대기"), TEMPORARY("임시저장");

    private String value;
    private GroupStatus(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return this.value; //will return , or ' instead of COMMA or APOSTROPHE
    }

    public static GroupStatus change(String value){
        GroupStatus result = null;
        for(GroupStatus status : GroupStatus.values()){
            if(status.toString().equals(value)){
                result = status;
                break;
            }
        }
        return result;
    }
}
