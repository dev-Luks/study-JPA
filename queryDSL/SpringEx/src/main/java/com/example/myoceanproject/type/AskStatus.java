package com.example.myoceanproject.type;

public enum AskStatus {
    WAITING("답변대기"), COMPLETE("답변완료");

    private String value;
    private AskStatus(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return this.value; //will return , or ' instead of COMMA or APOSTROPHE
    }

    public static AskStatus change(String value){
        AskStatus result = null;
        for(AskStatus status : AskStatus.values()){
            if(status.toString().equals(value)){
                result = status;
                break;
            }
        }
        return result;
    }
}
