package com.example.service.Impl;

import com.example.pojo.Appointment;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppointmentService {

    List<Appointment> findAll();

    List<Appointment> findByTeacherEmail(String teacherEmail);

    List<Appointment> findByStudentEmail(String studentEmail);

    void updateAStatusTo(int Astatus, int id,String studentEmail);

    void deleteByTeacherAndStudentEmailAndTimeSlot(String teacherEmail, String studentEmail, String timeSlot);
    void deleteByTeacherEmail(@Param("teacherEmail") String teacherEmail);
    void deleteByStudentEmail(@Param("studentEmail") String studentEmail);

    void add(String teacherEmail, String studentEmail, String timeSlot,int id,int Astatus,String reason);


    PageInfo<Appointment> getlistbystuEmail(int pageNum, int pageSize, String email);

    List<Appointment> findByID(int id);
    Appointment findBykey(int id,String studentEmail);

    void deleteid(int id);
}
