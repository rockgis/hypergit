package com.goodmit.hypergit.sendmail.service;

public class MailTO {

    private String address;
    private String title;
    private String message;

    /*
    getter, setter 생략
    */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
