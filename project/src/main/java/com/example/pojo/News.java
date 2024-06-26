package com.example.pojo;


import lombok.Data;

@Data
public class News {
    private String title;
    private int id;
    private String content;
    private boolean isread;
    private String email;
    private String date;
    private String sender;
}
