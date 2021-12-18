package com.shop.onlineapp;

public class UserObject {
    public String userId;
    public String emailId;
    public String password;
    public String name;

    public UserObject(String uid, String uName, String emailId, String passStr) {
        this.userId = uid;
        this.name = uName;
        this.emailId = emailId;
        this.password = passStr;
    }
}
