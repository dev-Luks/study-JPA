package com.example.myoceanproject.type;

public enum AskCategory {
    USINGINFO("이용"), ACCOUNTINFO("회원"), POINTINFO("포인트"), QUESTINFO("퀘스트");

    private String value;
    private AskCategory(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return this.value; //will return , or ' instead of COMMA or APOSTROPHE
    }

    //    value(한글) -> UserAccountStatus로
    public static AskCategory change(String value){
        AskCategory result = null;
        for(AskCategory status : AskCategory.values()){
            if(status.toString().equals(value)){
                result = status;
                break;
            }
        }
        return result;
    }
}
