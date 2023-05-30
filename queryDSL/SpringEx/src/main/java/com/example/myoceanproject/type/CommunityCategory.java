package com.example.myoceanproject.type;

public enum CommunityCategory {
    EXERCISE("운동"), COOK("요리"), FREEBOARD("자유"), MOVIE("영화"), BOOK("책"), COUNSELING("고민");

    private String value;
    private CommunityCategory(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return this.value; //will return , or ' instead of COMMA or APOSTROPHE
    }
    //    value(한글) -> UserAccountStatus로
    public static CommunityCategory change(String value){
        CommunityCategory result = null;
        for(CommunityCategory category : CommunityCategory.values()){
            if(category.toString().equals(value)){
                result = category;
                break;
            }
        }
        return result;
    }
}