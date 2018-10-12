package com.konkuk.dna.friend.manage;

public class Friend {
    private String nickname;
    private String avatar;
    private String info;
    private Boolean status;

    public Friend(String nickname, String avatar, String info, Boolean status) {
        this.nickname = nickname;
        this.avatar = avatar;
        this.info = info;
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

    public String getInfo() { return info; }
    public void setInfo(String info) { this.info = info; }

    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean inside) {
        this.status = status;
    }
}
