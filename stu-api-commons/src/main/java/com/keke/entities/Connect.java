package com.keke.entities;


import java.io.Serializable;

public class Connect implements Serializable {

    private Integer mid;

    private Integer rid;

    private Integer fid;

    private String username;

    private String mainfile;

    private String refile;

    private String original_url;

    private Integer layer;

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public String getRefile() {
        return refile;
    }

    public void setRefile(String refile) {
        this.refile = refile;
    }

    public String getOriginal_url() {
        return original_url;
    }

    public void setOriginal_url(String original_url) {
        this.original_url = original_url;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getMainfile() {
        return mainfile;
    }

    public void setMainfile(String mainfile) {
        this.mainfile = mainfile;
    }
}
