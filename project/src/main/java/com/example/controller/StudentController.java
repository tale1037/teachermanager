package com.example.controller;

import com.example.pojo.FreeTime;
import com.example.pojo.Result;
import com.example.pojo.Student;
import com.example.pojo.Teacher;
import com.example.service.Impl.*;
import com.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class StudentController {
      @Autowired
      private StudentService studentService;
      @Autowired
      private AppointmentService appointmentService;
      @Autowired
      private FreeTimeService freeTimeService;

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
         System.out.println(student.toString());
     studentService.addStudent(student);
     return  Result.success();
      }
      @PutMapping("/students")
      public Result updateStudent(@RequestBody Student student){
      studentService.updateStudent(student);
      return Result.success();
    }
    @PostMapping("/getfreeTimestoappointmment")
    public Result getfreeTimestoappointmment(@RequestHeader(name="Authorization") String token,@RequestBody FreeTime freeTime){
        Map<String,Object> map= JwtUtil.parseToken(token);
        String email= (String) map.get("email");
        appointmentService.add(freeTime.getTeacherEmail(),email,freeTime.getTimeSlot(),freeTime.getId(),freeTime.isTstatus());
        freeTimeService.updateStatus(freeTime.getId(), false);
        return Result.success();
    }
}
