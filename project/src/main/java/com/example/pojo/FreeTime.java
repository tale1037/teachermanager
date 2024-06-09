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

    public FreeTime(String timeSlot, String teacherEmail, String date, int id, String tips, boolean tstatus) {
        this.timeSlot = timeSlot;
        this.teacherEmail = teacherEmail;
        this.date = date;
        this.id = id;
        this.tips = tips;
        Tstatus = tstatus;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public boolean isTstatus() {
        return Tstatus;
    }

    public void setTstatus(boolean tstatus) {
        Tstatus = tstatus;
    }
}