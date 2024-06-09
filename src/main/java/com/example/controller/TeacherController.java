package com.example.controller;

import com.example.mapper.UserPasswordMapper;
import com.example.pojo.Result;
import com.example.pojo.Teacher;
import com.example.service.Impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/teachers")
    public Result findall(){
        List<Teacher> teacherList=teacherService.findAll();
        return Result.success(teacherList);
    }
    @GetMapping("/teachers/{email}")
    public Result findByEmail(@PathVariable String email){
        Teacher teacher=teacherService.findByEmail(email);
        return Result.success(teacher);
    }
    @DeleteMapping("/teachers/{email}")
    public Result deleteByEmail(@PathVariable String email){
        teacherService.deleteByEmail(email);

        return Result.success();
    }
    @PostMapping("/teachers")
    public Result insert(@RequestBody Teacher teacher){
        teacherService.insert(teacher);
        return Result.success();
    }
    @PutMapping("/teachers")
    public Result update(@RequestBody Teacher teacher){
        teacherService.update(teacher);
        return Result.success();
    }
    // 根据教师部门查询教师信息
    @GetMapping("/teachers/department/{department}")
    public Result findBydepartment(@PathVariable String department) {
        List<Teacher> teacherList=teacherService.findByDepartment(department);
        return Result.success(teacherList);
    }
    @GetMapping("/teachers/name/{name}")
    public Result findByName(@PathVariable String name) {
        List<Teacher> teacherList=teacherService.findByName(name);
        return Result.success(teacherList);
    }
    // 获取所有教师的部门选项
    @GetMapping("teachers/departments")
    public Result findAllDepartments() {
        List<String> stringList=teacherService.findAllDepartments();
        return Result.success(stringList);
    }
}
