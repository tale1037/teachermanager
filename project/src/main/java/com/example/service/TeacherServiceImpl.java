package com.example.service;

import com.example.mapper.TeacherMapper;
import com.example.pojo.Student;
import com.example.pojo.Teacher;
import com.example.pojo.listquery;
import com.example.service.Impl.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private  TeacherMapper teacherMapper;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private FreeTimeService freeTimeService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserPasswordService userPasswordService;
    @Override
    public List<Teacher> findAll() {
        List<Teacher> teacherlist=teacherMapper.findAll();
        return teacherlist;
    }

    @Override
    public Teacher findByEmail(String email) {
        Teacher teacher=teacherMapper.findByEmail(email);
        return teacher;
    }

    @Override
    public List<Teacher> findByName(String name) {
        return teacherMapper.findByName(name);
    }

    @Override
    public void insert(Teacher teacher) {
        teacherMapper.insert(teacher);
    }

    @Override
    public void update(Teacher teacher) {
        teacherMapper.update(teacher);
    }

    @Override
    public void deleteByEmail(String email) {
        teacherMapper.deleteByEmail(email);
        appointmentService.findByTeacherEmail(email);
        freeTimeService.deleteByTeacherEmail(email);
        ratingService.deleteRatingByteacherEmail(email);
        userPasswordService.deleteUserPasswordByEmail(email);
    }

    @Override
    public List<Teacher> findByDepartment(String department) {
        List<Teacher> teacherlist=teacherMapper.findByDepartment(department);
        return teacherlist;
    }

    @Override
    public List<String> findAllDepartments() {
        List<String> stringList=teacherMapper.findAllDepartments();
        return stringList;
    }

    @Override
    public PageInfo<Teacher> findList(int pageNum, int pageSize) {
        List<Teacher> teacherlist=teacherMapper.findAll();
        PageHelper.startPage(pageNum, pageSize);
        return PageInfo.of(teacherlist);
    }

    @Override
    public PageInfo<Teacher> findList(int pageNum, int pageSize, String name) {
        List<Teacher> teacherlist=teacherMapper.findByName(name);
        PageHelper.startPage(pageNum, pageSize);
        return PageInfo.of(teacherlist);
    }
}
