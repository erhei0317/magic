package com.candata.magic.pojo;

import java.util.Date;

public class Portal {
    private Integer tid;

    private String theme;

    private Date begintime;

    private Date endtime;

    private String link;

    private String linkedcode;

    private Integer read;

    private Integer gid;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public String getLinkedcode() {
        return linkedcode;
    }

    public void setLinkedcode(String linkedcode) {
        this.linkedcode = linkedcode == null ? null : linkedcode.trim();
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }
}