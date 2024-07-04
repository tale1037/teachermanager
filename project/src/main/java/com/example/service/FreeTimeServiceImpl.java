package com.example.service;

import com.example.mapper.AppointmentMapper;
import com.example.mapper.FreeTimeMapper;
import com.example.mapper.TeacherMapper;
import com.example.pojo.Appointment;
import com.example.pojo.FreeTime;
import com.example.pojo.Teacher;
import com.example.service.Impl.FreeTimeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FreeTimeServiceImpl implements FreeTimeService {
    @Autowired
    private FreeTimeMapper freeTimeMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private AppointmentMapper appointmentMapper;
    public List<FreeTime> findAll() {
        List<FreeTime> freeTimeList=freeTimeMapper.findAll();
        for (FreeTime freeTime : freeTimeList){
            freeTime.setTeachername(teacherMapper.findByEmail(freeTime.getTeacherEmail()).getName());
        }
        return freeTimeList;
    }

    @Override
    public List<FreeTime> findByTeacherEmail(String teacherEmail) {
        List<FreeTime> freeTimeList=freeTimeMapper.findByTeacherEmail(teacherEmail);
        if(freeTimeList==null){
            return new ArrayList<>();
        }
        for (FreeTime freeTime : freeTimeList){
            freeTime.setTeachername(teacherMapper.findByEmail(freeTime.getTeacherEmail()).getName());
        }
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
        PageHelper.startPage(pageNum, pageSize);
        List<FreeTime> freeTimeList=freeTimeMapper.findAllNo();
        for (FreeTime freeTime : freeTimeList){
            freeTime.setTeachername(teacherMapper.findByEmail(freeTime.getTeacherEmail()).getName());
        }
        return PageInfo.of(freeTimeList);
    }
    @Override//新添加
    public PageInfo<FreeTime> findForStudentList(int pageNum, int pageSize,String studentEmail) {
        PageHelper.startPage(pageNum, pageSize);
        List<FreeTime> freeTimeList=freeTimeMapper.findFreeTimeno(studentEmail);
        for (FreeTime freeTime : freeTimeList){
            freeTime.setTeachername(teacherMapper.findByEmail(freeTime.getTeacherEmail()).getName());
        }
        return PageInfo.of(freeTimeList);
    }
    public PageInfo<FreeTime> findByTeacherNameForStudent(int pageNum, int pageSize, String teacherName, String studentEmail) {
        PageHelper.startPage(pageNum, pageSize);
        List<Teacher> teacherList=teacherMapper.findByName(teacherName);
        List<FreeTime> freeTimeList =new ArrayList<>();
        for(Teacher teacher : teacherList){
            List<FreeTime> freeTime=freeTimeMapper.findByTeacherEmail2(teacher.getEmail(),studentEmail);
            freeTimeList.addAll(freeTime);
        }
        for (FreeTime freeTime : freeTimeList){
            freeTime.setTeachername(teacherMapper.findByEmail(freeTime.getTeacherEmail()).getName());
        }
        return PageInfo.of(freeTimeList);
    }
    public PageInfo<FreeTime> findList(int pageNum, int pageSize, String email) {
        PageHelper.startPage(pageNum, pageSize);
        List<FreeTime> freeTimeList=freeTimeMapper.findByTeacherEmail(email);

        for (FreeTime freeTime : freeTimeList){
            freeTime.setTeachername(teacherMapper.findByEmail(freeTime.getTeacherEmail()).getName());
        }
        return PageInfo.of(freeTimeList);
    }

    @Override
    public  PageInfo<FreeTime> findByTeacherName(int pageNum, int pageSize, String teacherName) {
        List<Teacher> teacherList=teacherMapper.findByName(teacherName);
        List<FreeTime> freeTimeList =new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        for(Teacher teacher : teacherList){
            List<FreeTime> freeTime=freeTimeMapper.findByTeacherEmail(teacher.getEmail());
            freeTimeList.addAll(freeTime);
        }
        for (FreeTime freeTime : freeTimeList) {
            freeTime.setTeachername(teacherMapper.findByEmail(freeTime.getTeacherEmail()).getName());
        }
        return PageInfo.of(freeTimeList);
    }

    @Override
    public List<FreeTime> findAllNo() {
        List<FreeTime> freeTimeList=freeTimeMapper.findAllNo();
        for (FreeTime freeTime : freeTimeList){
            freeTime.setTeachername(teacherMapper.findByEmail(freeTime.getTeacherEmail()).getName());
        }
        return freeTimeList;
    }
    @Override
    public FreeTime findByID(int id) {
        FreeTime freeTime = freeTimeMapper.findById(id);
        return freeTime;
    }
}
