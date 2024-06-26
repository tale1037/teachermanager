package com.example.pojo;


import lombok.Data;

import java.util.Date;

@Data
public class schedule {

    private int id;
    private String teacher_email;
    private String timeslot;
    private String date;
    private String plan;

}
