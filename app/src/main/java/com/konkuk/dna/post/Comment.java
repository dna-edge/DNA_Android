package com.konkuk.dna.post;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Comment implements Serializable {
    private String avatar;
    private String nickname;

    private String date;
    private String content;

    public Comment(String avatar, String nickname, String date, String content) {
        this.avatar = avatar;
        this.nickname = nickname;
        this.date = date;
        this.content = content;
    }

    public Comment(String date, String content) {
        this.avatar = avatar;
        this.nickname = nickname;
        this.date = date;
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
