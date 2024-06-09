package com.example.service.Impl;

import com.example.pojo.FreeTime;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FreeTimeService {

    List<FreeTime> findAll();

    List<FreeTime> findByTeacherEmail(String teacherEmail);

    void insert(FreeTime freeTime);

    void deleteByTeacherEmailAndTimeSlot(String teacherEmail, int timeSlot);

    void deleteByTeacherEmail(String teacherEmail);
}
