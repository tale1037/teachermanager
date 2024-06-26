package com.example.service.Impl;

import com.example.pojo.Teacher;
import com.example.pojo.listquery;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TeacherService {

    List<Teacher> findAll();

    Teacher findByEmail(String email);
    List<Teacher> findByName(String name);
    void insert(Teacher teacher);
    void update(Teacher teacher);
    void deleteByEmail(String email);
    // 根据教师部门查询教师信息
    List<Teacher> findByDepartment(String department);
    List<String> findAllDepartments();
    PageInfo<Teacher> findList(int pageNum, int pageSize);
    PageInfo<Teacher> findList(int pageNum, int pageSize, String name);

    PageInfo<Teacher> findListbyDepartment(Integer pageNum, Integer pageSize, String inputDepartment);
}
