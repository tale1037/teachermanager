package com.example.service.Impl;

import com.example.pojo.FreeTime;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface FreeTimeService {

    List<FreeTime> findAll();

    List<FreeTime> findByTeacherEmail(String teacherEmail);

    void insert(FreeTime freeTime);

    void deleteByTeacherEmailAndTimeSlot(String teacherEmail, String timeSlot);

    void deleteByTeacherEmail(String teacherEmail);
    void deleteById(int id);
    void update(FreeTime freeTime);
    void updateStatus(int id, boolean Tstatus);
    PageInfo<FreeTime> findList(int pageNum, int pageSize);
    PageInfo<FreeTime> findList(int pageNum, int pageSize, String email);

    PageInfo<FreeTime> findByTeacherName(int pageNum, int pageSize, String teacherName);
    PageInfo<FreeTime> findByTeacherNameForStudent(int pageNum, int pageSize, String teacherName,String studentEmail);
    PageInfo<FreeTime> findForStudentList(int pageNum, int pageSize,String studentEmail);
    List<FreeTime> findAllNo();

    FreeTime findByID(int id);
}
