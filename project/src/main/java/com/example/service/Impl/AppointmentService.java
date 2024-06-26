package com.example.service.Impl;

import com.example.pojo.Appointment;
import com.example.pojo.FreeTime;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppointmentService {

    List<Appointment> findAll();

    List<Appointment> findByTeacherEmail(String teacherEmail);

    List<Appointment> findByStudentEmail(String studentEmail);

    void updateAStatusTo(int Astatus, int id);

    void deleteByTeacherAndStudentEmailAndTimeSlot(String teacherEmail, String studentEmail, String timeSlot);
    void deleteByTeacherEmail(@Param("teacherEmail") String teacherEmail);
    void deleteByStudentEmail(@Param("studentEmail") String studentEmail);

    void add(String teacherEmail, String studentEmail, String timeSlot,int id,boolean Astatus,String reason);

    Appointment findByTeacherEmailandTimeSlot(String timeSlot, String teacherEmail);

    PageInfo<Appointment> getlistbystuEmail(int pageNum, int pageSize, String email);

    Appointment findByID(int id);
}
