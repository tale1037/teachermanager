package com.example.pojo;

public class UserPassword {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    private String email; // 主键
    private String password;
    private boolean isTeacher;

    // getter和setter方法
}
