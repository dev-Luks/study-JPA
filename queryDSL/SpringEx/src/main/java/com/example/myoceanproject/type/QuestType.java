package com.example.myoceanproject.type;

public enum QuestType {
    BASIC("일반"), EVENT("이벤트"), TODAY("오늘의 퀘스트");
    private String value;

    private QuestType(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value; //will return , or ' instead of COMMA or APOSTROPHE
    }

    public static QuestType change(String value) {
        QuestType result = null;
        for (QuestType type : QuestType.values()) {
            if (type.toString().equals(value)) {
                result = type;
                break;
            }
        }
        return result;
    }

}
