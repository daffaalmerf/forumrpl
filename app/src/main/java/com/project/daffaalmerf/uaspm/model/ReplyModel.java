package com.project.daffaalmerf.uaspm.model;

import java.util.Date;

public class ReplyModel {

    private String by;
    private Date timestamp;
    private String reply;
    private String post_id;

    public ReplyModel(){

    }

    public ReplyModel(String by, Date timestamp, String reply, String post_id){

        this.by = by;
        this.timestamp = timestamp;
        this.reply = reply;
        this.post_id = post_id;

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

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
}
