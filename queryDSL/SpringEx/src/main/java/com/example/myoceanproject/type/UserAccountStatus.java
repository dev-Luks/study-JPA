package com.example.myoceanproject.type;

public enum UserAccountStatus {
//    정지 계정, 정상 계정
    ACTIVE("정상"), BANNED("정지");

    private String value;
    private UserAccountStatus(String value)
    {
        this.value = value;
    }
//value
    public String toString()
    {
        return this.value; //will return , or ' instead of COMMA or APOSTROPHE
    }

//    value(한글) -> UserAccountStatus로
    public static UserAccountStatus change(String value){
        UserAccountStatus result = null;
        for(UserAccountStatus status : UserAccountStatus.values()){
            if(status.toString().equals(value)){
                result = status;
                break;
            }
        }
        return result;
    }

}
