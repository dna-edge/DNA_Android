package com.konkuk.dna.chat;

public class ChatUser {
    private int idx;
    private String nickname;
    private String avatar;
    private Boolean inside;


    public ChatUser(int idx, String nickname, String avatar, Boolean inside) {
        this.idx = idx;
        this.nickname = nickname;
        this.avatar = avatar;
        this.inside = inside;
    }


    public int getIdx() {
        return idx;
    }
    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getInside() {
        return inside;
    }
    public void setInside(Boolean inside) {
        this.inside = inside;
    }
}
