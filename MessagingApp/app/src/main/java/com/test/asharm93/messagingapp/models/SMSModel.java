package com.test.asharm93.messagingapp.models;

/**
 * Created by asharm93 on 7/8/2016.
 */
public class SMSModel {
    // Number from witch the sms was send
    private String number;
    // SMS text body
    private String body;
    public SMSModel() {
    }

    public SMSModel(String number, String body) {
        this.number = number;
        this.body = body;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
