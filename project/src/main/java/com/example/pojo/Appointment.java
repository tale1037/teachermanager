package com.example.pojo;


import lombok.Data;

@Data
public class Appointment {
    private int id;
    private String timeSlot;
    private String teacherEmail; // 外键
    private String studentEmail;
    private String reason;
    private String student_name;
    private String teacher_name;
    private String date;// 外键
    private int Astatus=0;//状态 初始时等待响应-0，然后有被拒绝-1 被撤销-2、通过-3其他三种状态
    private String refusereason;
}
