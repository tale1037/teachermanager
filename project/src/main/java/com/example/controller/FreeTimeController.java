package com.example.controller;

import com.example.pojo.*;
import com.example.service.Impl.*;
import com.example.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private NewsService newsService;

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
    //学生根据老师姓名获取所有freetime
    @GetMapping("/freeTimes/teacherName")
    public Result  findByTeacherName(@RequestHeader(name="Authorization",required = false) String token,@RequestParam Integer pageNum,@RequestParam Integer pageSize ,HttpServletResponse response, @RequestParam String teacherName){
        try {
            System.out.println(1252);
            Map<String,Object> claims = JwtUtil.parseToken(token);
            String email = (String)claims.get("email");
            return Result.success(freeTimeService.findByTeacherName(pageNum,pageSize,teacherName));
        }catch (Exception e){
            System.out.println(e.getMessage());
            response.setStatus(401);
            return Result.error("weidenglu");
        }
    }
    @PostMapping("/freeTimes")
    public Result insert(@RequestHeader(name="Authorization",required = false) String token, HttpServletResponse response, @RequestBody FreeTime freeTime){
        try {
            System.out.println(1252);
            Map<String,Object> claims = JwtUtil.parseToken(token);
            String email = (String)claims.get("email");
            freeTime.setTeacherEmail(email);
            String date1 = freeTime.getDate();
            String thisdate = date1.split("T")[0];
            String year = thisdate.split("-")[0];
            String month = thisdate.split("-")[1];
            String day = thisdate.split("-")[2];
            int intthisday = Integer.parseInt(day);
            intthisday = intthisday + 1;
            String trueday = String.valueOf(intthisday);
            String thistruedate = year +"-" +month+"-"+trueday;
            freeTime.setDate(thistruedate);
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
            System.out.println("teacher'sname");
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
            schedule schedule1 = new schedule();
            String student_name = studentService.findStudentByEmail(appointment.getStudentEmail()).getName();
            schedule1.setPlan("空闲时间预约："+student_name+"预约了您");
            schedule1.setTeacher_email(appointment.getTeacherEmail());
            schedule1.setTimeslot(appointment.getTimeSlot());
            schedule1.setDate(freeTime.getDate().split("T")[0]);
            scheduleService.addschedule(schedule1);
            News news1 = new News();
            Teacher teacher = teacherService.findByEmail(appointment.getTeacherEmail());
            news1.setContent("您对"+teacher.getName()+"老师在"+freeTime.getDate()+"日的预约已成功！请联系老师并准时参加！！");
            news1.setTitle("预约成功");
            news1.setIsread(false);
            news1.setEmail(studentService.findStudentByEmail(appointment.getStudentEmail()).getEmail());
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            news1.setDate(formatter.format(date));
            System.out.println(news1.toString());
            newsService.inserrtNews(news1);
            News news2 = new News();
            news2.setIsread(false);
            news2.setContent("您已通过对"+student_name+"同学在"+freeTime.getDate()+"日的预约申请，同学会在近期跟您联系请注意留意！");
            news2.setTitle("预约通过成功");
            news2.setEmail(appointment.getTeacherEmail());
            news2.setDate(formatter.format(date));
            System.out.println(news2.toString());
            newsService.inserrtNews(news2);
            appointmentService.updateAStatusTo(3,appointment.getId());
            return Result.success("");
            //return Response.success(teachers);
        }catch (Exception e){
            System.out.println(4555);
            System.out.println(e.toString());
            //response.setStatus(401);
            return Result.error("weidenglu");
        }

    }
    @PostMapping("/teacher/rejectstudent")
    public Result rejectStudent(@RequestHeader(name="Authorization",required = false) String token,@RequestBody Appointment appointment){
        try {
            System.out.println("refuse");

//            Map<String,Object> claims = JwtUtil.parseToken(token);
//            String email = (String)claims.get("email");
            System.out.println(appointment.toString());
            //Appointment appointment2 = appointmentService.findByTeacherEmailandTimeSlot(appointment.getTimeSlot(),appointment.getTeacherEmail());
            FreeTime freeTime = freeTimeService.findByID(appointment.getId());
            //freeTimeService.updateStatus(freeTime.getId(),true);
            News news1 = new News();
            Teacher teacher = teacherService.findByEmail(appointment.getTeacherEmail());
            System.out.println(teacher.toString());
            news1.setContent("您对"+teacher.getName()+"老师在"+freeTime.getDate()+"日的预约未通过！老师拒绝理由为："+appointment.getRefusereason());
            news1.setTitle("预约未通过");
            news1.setIsread(false);
            news1.setEmail(studentService.findStudentByEmail(appointment.getStudentEmail()).getEmail());
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            news1.setDate(formatter.format(date));
            System.out.println(news1.toString());
            newsService.inserrtNews(news1);
            appointmentService.updateAStatusTo(1,appointment.getId());
            return Result.success("");
            //return Response.success(teachers);
        }catch (Exception e){
            System.out.println(4555);
            System.out.println(e.toString());
            //response.setStatus(401);
            return Result.error("weidenglu");
        }

    }

}
