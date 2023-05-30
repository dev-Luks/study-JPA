package com.example.myoceanproject.type;

public enum AlarmCategory {
    //    정지 계정, 정상 계정
    DIARY("/myList/myExchangeDiary"), COMMUNITY("/community/read?communityPostId="), GROUP("/host/read?groupId="), POINT("/myPoint/index"), QUEST("/myQuest/myBadge"), TODAY("/myQuest/todayQuest"), ASK("/questionBoard/myQuestion"), LOGIN("/myPage/index");

    private String value;
    private AlarmCategory(String value)
    {
        this.value = value;
    }
    //value
    public String toString()
    {
        return this.value; //will return , or ' instead of COMMA or APOSTROPHE
    }

    //    value(한글) -> AlarmCategory로
    public static AlarmCategory change(String value){
        AlarmCategory result = null;
        for(AlarmCategory category : AlarmCategory.values()){
            if(category.toString().equals(value)){
                result = category;
                break;
            }
        }
        return result;
    }
}
