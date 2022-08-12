package com.example.duofingo;

public class UserTopicsModel {
    public Integer chapterID;
    public String topicName;
    public Integer total_chapters;
    public String userID;

    public void setChapterID(Integer chapterID) {
        this.chapterID = chapterID;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public void setNumChapters(Integer numChapters) {
        this.total_chapters = numChapters;
    }

    public void setUserName(String userName) {
        this.userID = userName;
    }

}
