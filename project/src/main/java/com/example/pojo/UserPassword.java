package com.example.pojo;

import lombok.Data;

@Data
public class UserPassword {

    private String email; // 主键
    private String password;
    private Boolean isTeacher;

    // getter和setter方法
}
