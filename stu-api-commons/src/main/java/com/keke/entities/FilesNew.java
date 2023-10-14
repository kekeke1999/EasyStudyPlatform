package com.keke.entities;

import java.util.Date;

public class FilesNew extends Files {

    private String location;

    public FilesNew(Files files, String location){
        super(files.getOriginalUrl(),files.getPdfUrl(), files.getCreateTime(), files.getKeyword(),files.getFid(), files.getFilename(), files.getUsername(), files.getLayer());
        this.location = location;
    }

    public FilesNew(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
