package com.example.controller;


import com.example.pojo.Result;
import com.example.pojo.Student;
import com.example.pojo.schedule;
import com.example.service.Impl.ScheduleService;
import com.example.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/teacher/getmyschedule")
    public Result getMySchedule(@RequestHeader(name = "Authorization", required = false) String token, HttpServletResponse response) {
        try {
            System.out.println(1);
            Map<String, Object> claims = JwtUtil.parseToken(token);
            String email = (String) claims.get("email");
            System.out.println(claims.toString());
            boolean isteacher = (boolean) claims.get("isteacher");
            List<schedule> schedules = scheduleService.getByteahcerEmail(email);
            return Result.success(schedules);
        } catch (Exception e) {
            response.setStatus(401);
            return Result.error("weidenglu");
        }
    }
    @PostMapping("/teacher/addschedule")
    public Result addSchedule(@RequestHeader(name = "Authorization", required = false) String token, HttpServletResponse response,@RequestBody schedule schedule) {
        try {
            System.out.println(schedule.toString());

            Map<String, Object> claims = JwtUtil.parseToken(token);
            String email = (String) claims.get("email");
            boolean isteacher = (boolean) claims.get("isteacher");
           // schedule.setTeacher_email(email);
        //String email = "2439082470@qq.com";

            //System.out.println(schedule.getDate().split("T")[0].split("-"));
            String thisdate = schedule.getDate().split("T")[0];
            String year = thisdate.split("-")[0];
            String month = thisdate.split("-")[1];
            String day = thisdate.split("-")[2];
            int intthisday = Integer.parseInt(day);
            intthisday = intthisday + 1;
            String trueday = String.valueOf(intthisday);
            String thistruedate = year +"-" +month+"-"+trueday;
            schedule.setDate(thistruedate);


            schedule.setTeacher_email(email);
            scheduleService.addschedule(schedule);
            return Result.success(1);
        } catch (Exception e) {
           response.setStatus(401);
            System.out.println(e.getMessage());
           return Result.error("weidenglu");
        }
    }
    @PostMapping("/teacher/deleteschedule")
    public Result deleteSchedule(@RequestHeader(name = "Authorization", required = false) String token, HttpServletResponse response,@RequestBody schedule schedule) {
        int id = schedule.getId();
        scheduleService.deleteByid(id);
        return Result.success(1);
    }
    @PostMapping("/user/cleanschedule")
    public Result cleanschedule(@RequestHeader(name="Authorization",required = false) String token, HttpServletResponse response) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date);
        System.out.println(today);
        scheduleService.cleanSchedule(today);
        return Result.success(1);
    }
}
