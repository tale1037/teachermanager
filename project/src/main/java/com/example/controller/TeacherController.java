package com.example.controller;

import com.example.mapper.UserPasswordMapper;
import com.example.pojo.Result;
import com.example.pojo.Student;
import com.example.pojo.Teacher;
import com.example.service.Impl.*;
import com.example.utils.JwtUtil;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

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
    @PostMapping("/teachers/delete")
    public Result deleteByEmail(@RequestBody Teacher teacher){
        teacherService.deleteByEmail(teacher.getEmail());

        return Result.success();
    }
    @PostMapping("/teachers")
    public Result insert(@RequestBody Teacher teacher){
        System.out.println("enter!");
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
    @GetMapping("/youselfnews")
    public Result yourInfo(@RequestHeader(name="Authorization",required = false) String token, HttpServletResponse response){
        try {
            System.out.println(1);
            Map<String,Object> claims = JwtUtil.parseToken(token);
            String email = (String)claims.get("email");
            System.out.println(claims.toString());
            boolean isteacher = (boolean)claims.get("isteacher");
            if(isteacher) {
                return Result.success(teacherService.findByEmail(email));
            }
            else{
                System.out.println("enter this");
                Student student = studentService.findStudentByEmail(email);
                System.out.println(student.toString());
                return Result.success(studentService.findStudentByEmail(email));
            }
            //return Response.success(teachers);
        }catch (Exception e){
            response.setStatus(401);
            return Result.error("weidenglu");
        }
    }
    @GetMapping("/teacher/getlist")
    public Result findAll(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam(required = false) String inputname) {
        if(inputname==null||inputname.equals("")) {
            System.out.println("teacher_name==null");
            return Result.success(teacherService.findList(pageNum, pageSize));
        }
        else{
            System.out.println("teacher_name=="+inputname);
            return Result.success(teacherService.findList(pageNum,pageSize,inputname));
        }
        //return Result.success(teacherList);
    }
}
