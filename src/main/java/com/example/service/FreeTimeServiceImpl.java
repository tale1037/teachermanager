package com.example.service;

import com.example.mapper.FreeTimeMapper;
import com.example.mapper.StudentMapper;
import com.example.pojo.FreeTime;
import com.example.service.Impl.FreeTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FreeTimeServiceImpl implements FreeTimeService {
    @Autowired
    private FreeTimeMapper freeTimeMapper;

    @Override
    public List<FreeTime> findAll() {
        List<FreeTime> freeTimeList=freeTimeMapper.findAll();
        return freeTimeList;
    }

    @Override
    public List<FreeTime> findByTeacherEmail(String teacherEmail) {
        List<FreeTime> freeTimeList=freeTimeMapper.findByTeacherEmail(teacherEmail);
        return freeTimeList;
    }

    @Override
    public void insert(FreeTime freeTime) {
           freeTimeMapper.insert(freeTime);
    }

    @Override
    public void deleteByTeacherEmailAndTimeSlot(String teacherEmail, int timeSlot) {
          freeTimeMapper.deleteByTeacherEmailAndTimeSlot(teacherEmail,timeSlot);
    }

    @Override
    public void deleteByTeacherEmail(String teacherEmail) {
            freeTimeMapper.deleteByTeacherEmail(teacherEmail);
    }
}
