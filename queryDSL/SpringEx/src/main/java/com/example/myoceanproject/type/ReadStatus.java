package com.example.myoceanproject.type;

public enum ReadStatus {
    READ("읽음"), UNREAD("안 읽음");

    private String value;

    private ReadStatus(String value){this.value = value;}

    public String toString(){
        return this.value;
    }

    public static ReadStatus change(String value){
        ReadStatus result = null;
        for(ReadStatus status : ReadStatus.values()){
            if(status.toString().equals(value)){
                result = status;
                break;
            }
        }
        return result;
    }


}
