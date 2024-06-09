package com.example.service.Impl;

import com.example.pojo.Appointment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppointmentService {

    List<Appointment> findAll();

    List<Appointment> findByTeacherEmail(String teacherEmail);

    List<Appointment> findByStudentEmail(String studentEmail);

    void deleteByTeacherAndStudentEmailAndTimeSlot(String teacherEmail, String studentEmail, int timeSlot);
    void deleteByTeacherEmail(@Param("teacherEmail") String teacherEmail);
    void deleteByStudentEmail(@Param("studentEmail") String studentEmail);

    void add(String teacherEmail, String studentEmail, int timeSlot);
}
