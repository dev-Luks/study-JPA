package com.example.myoceanproject.type;

public enum DiaryCategory {

    CLOSEDIARY("나의 일기"), OPENDIARY("교환 일기");

    private String value;
    private DiaryCategory(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return this.value; //will return , or ' instead of COMMA or APOSTROPHE
    }

    public static DiaryCategory change(String value){
        DiaryCategory result = null;
        for(DiaryCategory status : DiaryCategory.values()){
            if(status.toString().equals(value)){
                result = status;
                break;
            }
        }
        return result;
    }
}
