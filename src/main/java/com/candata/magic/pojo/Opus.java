package com.candata.magic.pojo;

public class Opus {
    private Integer opusid;

    private String stock;

    private Integer sccode;

    private Integer hit;

    private String more;

    public Integer getOpusid() {
        return opusid;
    }

    public void setOpusid(Integer opusid) {
        this.opusid = opusid;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock == null ? null : stock.trim();
    }

    public Integer getSccode() {
        return sccode;
    }

    public void setSccode(Integer sccode) {
        this.sccode = sccode;
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