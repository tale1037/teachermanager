package com.example.controller;

import com.example.pojo.Appointment;
import com.example.pojo.FreeTime;
import com.example.pojo.Result;
import com.example.service.Impl.AppointmentService;
import com.example.service.Impl.FreeTimeService;
import com.example.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FreeTimeController {
//    List<FreeTime> findAll();
//
//    List<FreeTime> findByTeacherEmail(String teacherEmail);
//
//    void insert(FreeTime freeTime);
//
//    void deleteByTeacherEmailAndTimeSlot(String teacherEmail, int timeSlot);
//
//    void deleteByTeacherEmail(String teacherEmail);
    @Autowired
    private FreeTimeService freeTimeService;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/freeTimes")
    public Result findAll(){
        List<FreeTime> freeTimeList=freeTimeService.findAll();
        return Result.success(freeTimeList);
    }
    @GetMapping("/freeTimes/{teacherEmail}")
    public Result  findByTeacherEmail(@PathVariable String teacherEmail){
        List<FreeTime> freeTimeList=freeTimeService.findByTeacherEmail(teacherEmail);
        return Result.success(freeTimeList);
    }
    @PostMapping("/freeTimes/deletefreetimebyslot")
    public Result deleteByTeacherEmailAndTimeSlot(@RequestBody FreeTime freeTime){
        System.out.println(15444);
        freeTimeService.deleteByTeacherEmailAndTimeSlot(freeTime.getTeacherEmail(),freeTime.getTimeSlot());
        return Result.success();
    }
    @DeleteMapping("/freeTimes/{teacherEmail}")
    public Result deleteByTeacherEmail(@PathVariable String teacherEmail){
        freeTimeService.deleteByTeacherEmail(teacherEmail);
        return Result.success();
    }
    @DeleteMapping("/freeTimes/id/{id}")
    public Result deleteById(@PathVariable int id){
        freeTimeService.deleteById(id);
        return Result.success();
    }
    @PostMapping("/freeTimes")
    public Result insert(@RequestHeader(name="Authorization",required = false) String token, HttpServletResponse response, @RequestBody FreeTime freeTime){
        try {
            System.out.println(1252);
            Map<String,Object> claims = JwtUtil.parseToken(token);
            String email = (String)claims.get("email");
            freeTime.setTeacherEmail(email);
            String date1 = freeTime.getDate();
            String[] ts = date1.split("T");
            freeTime.setDate(ts[0]);
            freeTime.setTstatus(false);
            String tips = freeTime.getTips();
            tips = tips.replace("<p>","");
            tips = tips.replace("</p>","");
            freeTime.setTips(tips);
            System.out.println(freeTime.getDate());
            freeTimeService.insert(freeTime);
            return  Result.success();
            //return Response.success(teachers);
        }catch (Exception e){
            System.out.println(e.getMessage());
            response.setStatus(401);
            return Result.error("weidenglu");
        }

    }
    @GetMapping("/teacher/freetime/getlist")
    public Result getFreeTime(@RequestHeader(name="Authorization",required = false) String token,@RequestParam Integer pageNum,@RequestParam Integer pageSize ,HttpServletResponse response){
        try {
            System.out.println(1252);
            Map<String,Object> claims = JwtUtil.parseToken(token);
            String email = (String)claims.get("email");

            return Result.success(freeTimeService.findList(pageNum,pageSize,email));
            //return Response.success(teachers);
        }catch (Exception e){
            System.out.println(4555);
            System.out.println(e.getMessage());
            response.setStatus(401);
            return Result.error("weidenglu");
        }
    }
    @GetMapping("/freetime/getlist")
    public Result getallFreeTime(@RequestHeader(name="Authorization",required = false) String token,@RequestParam Integer pageNum,@RequestParam Integer pageSize ,HttpServletResponse response){
        try {
            System.out.println(1252);
            Map<String,Object> claims = JwtUtil.parseToken(token);
            String email = (String)claims.get("email");

            return Result.success(freeTimeService.findList(pageNum,pageSize));
            //return Response.success(teachers);
        }catch (Exception e){
            System.out.println(4555);
            System.out.println(e.getMessage());
            response.setStatus(401);
            return Result.error("weidenglu");
        }
    }
    @PostMapping("/teacher/selectstudent")
    public Result selectStudent(@RequestHeader(name="Authorization",required = false) String token, @RequestBody Appointment appointment){
        try {
            System.out.println(12525152);

//            Map<String,Object> claims = JwtUtil.parseToken(token);
//            String email = (String)claims.get("email");
            System.out.println(appointment.toString());
            //Appointment appointment2 = appointmentService.findByTeacherEmailandTimeSlot(appointment.getTimeSlot(),appointment.getTeacherEmail());
            FreeTime freeTime = freeTimeService.findByID(appointment.getId());
            freeTimeService.updateStatus(freeTime.getId(),true);
            appointmentService.deleteByTeacherAndStudentEmailAndTimeSlot(appointment.getTeacherEmail(),appointment.getStudentEmail(),appointment.getTimeSlot());
            return Result.success("");
            //return Response.success(teachers);
        }catch (Exception e){
            System.out.println(4555);
            System.out.println(e.getMessage());
            //response.setStatus(401);
            return Result.error("weidenglu");
        }

    }

}
