package com.candata.magic.pojo;

public class Player {
    private Integer pid;

    private String username;

    private String userlink;

    private Integer hit;

    private String more;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserlink() {
        return userlink;
    }

    public void setUserlink(String userlink) {
        this.userlink = userlink == null ? null : userlink.trim();
    }

    public Integer getHit() {
        return hit;
    }

    public void setHit(Integer hit) {
        this.hit = hit;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more == null ? null : more.trim();
    }
}