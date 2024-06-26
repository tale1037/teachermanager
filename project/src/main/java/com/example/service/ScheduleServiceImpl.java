package com.example.service;

import com.example.mapper.ScheduleMapper;
import com.example.pojo.schedule;
import com.example.service.Impl.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Override
    public List<schedule> getByteahcerEmail(String email) {
        return scheduleMapper.getbyEmail(email);
    }

    @Override
    public void addschedule(schedule schedule) {
        scheduleMapper.addschedule(schedule);
    }

    @Override
    public void deleteByid(int id) {
        scheduleMapper.deleteschedule(id);
    }

    @Override
    public void cleanSchedule(String today) {
        scheduleMapper.cleanSchedule(today);
    }
}
