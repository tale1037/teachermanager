package com.example.controller;

import com.example.pojo.Appointment;
import com.example.pojo.Result;
import com.example.pojo.Student;
import com.example.pojo.Teacher;
import com.example.service.Impl.AppointmentService;
import com.example.service.Impl.StudentService;
import com.example.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AppointmentController {
        @Autowired
        private AppointmentService appointmentService;
        @GetMapping("/appointments")
        public Result findall(){
            List<Appointment> appointmentList=appointmentService.findAll();
            return Result.success(appointmentList);
        }
        @GetMapping("/appointments/teacherapplication")
        public Result  findByTeacherEmail(@RequestHeader(name="Authorization",required = false) String token, HttpServletResponse response){
            try {
                System.out.println(1);
                Map<String,Object> claims = JwtUtil.parseToken(token);
                String email = (String)claims.get("email");
                System.out.println(claims.toString());
                List<Appointment> appointmentList=appointmentService.findByTeacherEmail(email);
                return Result.success(appointmentList);
                //return Response.success(teachers);
            }catch (Exception e){
                System.out.println(e.getMessage());
                response.setStatus(401);
                return Result.error("weidenglu");
            }
        }
        @DeleteMapping("/appointments/{teacherEmail}/{studentEmail}/{timeSlot}")
        public Result deleteByTeacherAndStudentEmailAndTimeSlot(@PathVariable String teacherEmail,@PathVariable String studentEmail, @PathVariable String timeSlot){
            appointmentService.deleteByTeacherAndStudentEmailAndTimeSlot(teacherEmail,studentEmail,timeSlot);
            return Result.success();
        }
        @PostMapping("/appointments/student/{studentEmail}")
        public Result  findByStudentEmail(@PathVariable String studentEmail){
        List<Appointment> appointmentList=appointmentService.findByStudentEmail(studentEmail);
        return Result.success(appointmentList);
        }
        @PostMapping("/appointments/{teacherEmail}/{studentEmail}/{timeSlot}")
        public Result addAppointment(@PathVariable String teacherEmail,@PathVariable String studentEmail, @PathVariable String timeSlot) {
          //appointmentService.add(teacherEmail, studentEmail, timeSlot);
          return Result.success();
        }
        //@PostMapping('/')
}
