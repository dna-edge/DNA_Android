package com.konkuk.dna.chat;

public class ChatMessage {
    private String userName;    // 보낸이
    private String avatar;      // 프로필 이미지 url
    private String message;     // 메시지
    private String time;        // 시간
    private String like;       // 좋아요

    public ChatMessage(){}

    public ChatMessage(String userName, String avatar, String message, String time, String like){
        this.userName = userName;
        this.avatar = avatar;
        this.message = message;
        this.time = time;
        this.like = like;
    }

    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }

    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }

    public String getLike() { return like; }
    public void setLike(String like) { this.like = like; }
}
