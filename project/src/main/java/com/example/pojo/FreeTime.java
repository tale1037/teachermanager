package com.example.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FreeTime {


    private String timeSlot; // 主键
    private String teacherEmail; // 外键
    private String date;
    private int id;
    private String tips;
    private boolean Tstatus=false;
    private String teachername;
    private String reason;

    public FreeTime(String timeSlot, String teacherEmail, String date, int id, String tips, boolean tstatus, String reason) {
        this.timeSlot = timeSlot;
        this.teacherEmail = teacherEmail;
        this.date = date;
        this.id = id;
        this.tips = tips;
        Tstatus = tstatus;
        this.reason = reason;
    }
}