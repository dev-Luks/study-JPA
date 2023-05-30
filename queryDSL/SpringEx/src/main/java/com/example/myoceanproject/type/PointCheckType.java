package com.example.myoceanproject.type;

public enum PointCheckType {
    BEFOREREFUND("처음"),AFTERREFUND("이후"), REWARD("보상");

    private String value;
    private PointCheckType(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return this.value; //will return , or ' instead of COMMA or APOSTROPHE
    }
    //    value(한글) -> UserAccountStatus로
    public static PointCheckType change(String value){
        PointCheckType result = null;
        for(PointCheckType category : PointCheckType.values()){
            if(category.toString().equals(value)){
                result = category;
                break;
            }
        }
        return result;
    }
}
