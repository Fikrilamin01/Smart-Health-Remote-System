package com.SmartHealthRemoteSystem.SHSR.User;

public abstract class User {
    private String userId;
    private String name;
    private String password;
    private String contact;

    public User() {
    }

    public User(String userId, String name, String password, String contact) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.contact = contact;
    }

    public User(String name, String password, String contact) {
        this.name = name;
        this.password = password;
        this.contact = contact;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
