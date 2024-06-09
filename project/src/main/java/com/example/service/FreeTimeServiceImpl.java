package com.example.service;

import com.example.mapper.FreeTimeMapper;
import com.example.pojo.FreeTime;
import com.example.pojo.Teacher;
import com.example.service.Impl.FreeTimeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public void deleteByTeacherEmailAndTimeSlot(String teacherEmail, String timeSlot) {
          freeTimeMapper.deleteByTeacherEmailAndTimeSlot(teacherEmail,timeSlot);
    }

    @Override
    public void deleteByTeacherEmail(String teacherEmail) {
            freeTimeMapper.deleteByTeacherEmail(teacherEmail);
    }

    @Override
    public void deleteById(int id) {
        freeTimeMapper.deleteById(id);
    }

    @Override
    public void update(FreeTime freeTime) {
        freeTimeMapper.update(freeTime);
    }

    @Override
    public void updateStatus(int id, boolean Tstatus) {
        freeTimeMapper.updateStatus(id,Tstatus);
    }

    @Override
    public PageInfo<FreeTime> findList(int pageNum, int pageSize) {
        List<FreeTime> teacherlist=freeTimeMapper.findAll();
        PageHelper.startPage(pageNum, pageSize);
        return PageInfo.of(teacherlist);
    }

    @Override
    public PageInfo<FreeTime> findList(int pageNum, int pageSize, String email) {
        List<FreeTime> freeTimeList=freeTimeMapper.findByTeacherEmail(email);
        PageHelper.startPage(pageNum, pageSize);
        return PageInfo.of(freeTimeList);
    }

    @Override
    public FreeTime findByID(int id) {
        FreeTime freeTime = freeTimeMapper.findById(id);
        return freeTime;
    }
}
