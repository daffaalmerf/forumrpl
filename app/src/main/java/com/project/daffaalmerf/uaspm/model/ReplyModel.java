package com.project.daffaalmerf.uaspm.model;

import java.util.Date;

public class ReplyModel {

    private String by;
    private Date timestamp;
    private String reply;

    public ReplyModel(){

    }

    public ReplyModel(String by, Date timestamp, String reply){

        this.by = by;
        this.timestamp = timestamp;
        this.reply = reply;

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
}
