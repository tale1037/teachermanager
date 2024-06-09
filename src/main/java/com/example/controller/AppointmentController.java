package com.example.controller;

import com.example.pojo.Appointment;
import com.example.pojo.Result;
import com.example.pojo.Student;
import com.example.service.Impl.AppointmentService;
import com.example.service.Impl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class AppointmentController {
        @Autowired
        private AppointmentService appointmentService;
        @GetMapping("/appointments")
        public Result findall(){
            List<Appointment> appointmentList=appointmentService.findAll();
            return Result.success(appointmentList);
        }
        @GetMapping("/appointments/teacher/{teacherEmail}")
        public Result  findByTeacherEmail(@PathVariable String teacherEmail){
            List<Appointment> appointmentList=appointmentService.findByTeacherEmail(teacherEmail);
            return Result.success(appointmentList);
        }
        @DeleteMapping("/appointments/{teacherEmail}/{studentEmail}/{timeSlot}")
        public Result deleteByTeacherAndStudentEmailAndTimeSlot(@PathVariable String teacherEmail,@PathVariable String studentEmail, @PathVariable int timeSlot){
            appointmentService.deleteByTeacherAndStudentEmailAndTimeSlot(teacherEmail,studentEmail,timeSlot);
            return Result.success();
        }
        @PostMapping("/appointments/student/{studentEmail}")
        public Result  findByStudentEmail(@PathVariable String studentEmail){
        List<Appointment> appointmentList=appointmentService.findByStudentEmail(studentEmail);
        return Result.success(appointmentList);
        }
        @PostMapping("/appointments/{teacherEmail}/{studentEmail}/{timeSlot}")
        public Result addAppointment(@PathVariable String teacherEmail,@PathVariable String studentEmail, @PathVariable int timeSlot) {
          appointmentService.add(teacherEmail, studentEmail, timeSlot);
          return Result.success();
        }
}
