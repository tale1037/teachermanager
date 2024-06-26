package com.example.service.Impl;

import com.example.pojo.FreeTime;
import com.example.pojo.Teacher;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FreeTimeService {

    List<FreeTime> findAll();

    List<FreeTime> findByTeacherEmail(String teacherEmail);

    void insert(FreeTime freeTime);

    void deleteByTeacherEmailAndTimeSlot(String teacherEmail, String timeSlot);

    void deleteByTeacherEmail(String teacherEmail);
    void deleteById(int id);
    void update(FreeTime freeTime);
    void updateStatus(@Param("id") int id, @Param("Tstatus") boolean Tstatus);
    PageInfo<FreeTime> findList(int pageNum, int pageSize);
    PageInfo<FreeTime> findList(int pageNum, int pageSize, String email);

    PageInfo<FreeTime> findByTeacherName(int pageNum, int pageSize, String teacherName);

    List<FreeTime> findAllNo();

    FreeTime findByID(int id);
}
