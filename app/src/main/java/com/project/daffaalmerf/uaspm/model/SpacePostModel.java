package com.project.daffaalmerf.uaspm.model;

import java.util.Date;

public class SpacePostModel {

    private String by;
    private Date timestamp;
    private String category;
    private String content;

    public SpacePostModel(){

    }

    public SpacePostModel(String by, Date timestamp, String category, String content){

        this.by = by;
        this.timestamp = timestamp;
        this.category = category;
        this.content = content;

    }


    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
