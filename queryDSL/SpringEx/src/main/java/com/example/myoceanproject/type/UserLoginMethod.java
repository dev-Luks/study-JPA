package com.example.myoceanproject.type;

public enum UserLoginMethod {
    KAKAO("카카오"), GOOGLE("구글"), NAVER("네이버"), GENERAL("일반");
    private String value;
    private UserLoginMethod(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return this.value; //will return , or ' instead of COMMA or APOSTROPHE
    }

    public static UserLoginMethod change(String value){
        UserLoginMethod result = null;
        for(UserLoginMethod method : UserLoginMethod.values()){
            if (method.toString().equals(value)){
                result = method;
                break;
            }
        }
        return result;
    }
}

