package com.example.service.Impl;

import com.example.pojo.schedule;

import java.util.List;

public interface ScheduleService {


    List<schedule> getByteahcerEmail(String email);

    void addschedule(schedule schedule);

    void deleteByid(int id);

    void cleanSchedule(String today);
}
