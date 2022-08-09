package com.example.duofingo;

public class UserTopicsModel {
    public String chapterID;
    public String topicName;
    public int numChapters;
    public int userName;

    public void setChapterID(String chapterID) {
        this.chapterID = chapterID;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public void setNumChapters(int numChapters) {
        this.numChapters = numChapters;
    }

    public void setUserName(int userName) {
        this.userName = userName;
    }

    public UserTopicsModel() {
    }
}
