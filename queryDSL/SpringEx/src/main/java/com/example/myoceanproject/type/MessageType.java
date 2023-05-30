package com.example.myoceanproject.type;

public enum MessageType {
    ENTER("입장"),CHAT("대화"),LEAVE("퇴장");

    private String value;

    private MessageType(String value){this.value = value;}

    public String toString()
    {
        return this.value;
    }

    public static MessageType change(String value){
        MessageType result = null;
        for(MessageType type : MessageType.values()){
            if(type.toString().equals(value)){
                result = type;
                break;
            }
        }
        return result;
    }
}
