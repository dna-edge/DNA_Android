package com.konkuk.dna.friend.manage;

public class Friend {
    private String nickname;
    private String avatar;
    private Boolean status;

    public Friend(String nickname, String avatar, Boolean status) {
        this.nickname = nickname;
        this.avatar = avatar;
        this.status = status;
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

    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean inside) {
        this.status = status;
    }
}
