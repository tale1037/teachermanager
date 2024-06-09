package com.example.controller;

import com.example.pojo.Result;
import com.example.pojo.Student;
import com.example.pojo.Teacher;
import com.example.service.Impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class StudentController {
      @Autowired
      private StudentService studentService;

      @GetMapping("/students")
      public Result findAllStudents(){
      List<Student> studentList=studentService.findAllStudents();
      return Result.success(studentList);
      }
      @GetMapping("/students/{email}")
      public Result  findStudentByEmail(@PathVariable String email){
      Student student=studentService.findStudentByEmail(email);
      return Result.success(student);
     }
     @DeleteMapping("/students/{email}")
     public Result deleteStudentByEmail(@PathVariable String email){
     studentService.deleteStudentByEmail(email);

     return Result.success();
     }
     @PostMapping("/students")
     public Result addStudent(@RequestBody Student student){
     studentService.addStudent(student);
     return  Result.success();
      }
      @PutMapping("/students")
      public Result updateStudent(@RequestBody Student student){
      studentService.updateStudent(student);
      return Result.success();
    }
}
