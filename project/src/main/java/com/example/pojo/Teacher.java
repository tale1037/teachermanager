package com.example.pojo;

import lombok.Data;

import java.util.List;
@Data
public class Teacher {
    private String name;
    private String department;
    private String title;
    private String email; // 主键
    private String researchArea;
    private String researchResults;
    private String officeAddress;
    private String phone;
    private String imageUrl;
    private String description;
    private String homepage;
    private float rating;
    private String country;
}

