package com.example.pojo;


import lombok.Data;

@Data
public class Rating {
    private String teacherEmail; // 外键
    private String studentEmail; // 外键
    private float rating;
}