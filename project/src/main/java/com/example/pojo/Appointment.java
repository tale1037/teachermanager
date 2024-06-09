package com.example.pojo;


import lombok.Data;

@Data
public class Appointment {
    private int id;
    private String timeSlot;
    private String teacherEmail; // 外键
    private String studentEmail; // 外键
}
