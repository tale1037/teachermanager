package com.example.controller;

import com.example.pojo.*;
import com.example.service.Impl.*;
import com.example.utils.JwtUtil;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class FreeTimeController {
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
    //删除当前空闲时间
    @PostMapping("/freeTimes/deletefreetimebyslot")
    public Result deleteById(@RequestBody FreeTime freeTime){
        System.out.println(15444);
        appointmentService.deleteid(freeTime.getId());
        freeTimeService.deleteById(freeTime.getId());
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
    //修改为只显示未预约空闲时间
    //学生根据老师姓名获取所有freetime
    @GetMapping("/freeTimes/teacherName")
    public Result  findByTeacherName(@RequestHeader(name="Authorization",required = false) String token,@RequestParam Integer pageNum,@RequestParam Integer pageSize ,HttpServletResponse response, @RequestParam String teacherName){
        try {
            System.out.println(1252);
            Map<String,Object> claims = JwtUtil.parseToken(token);
            String email = (String)claims.get("email");

//           return Result.success(freeTimeService.findByTeacherName(pageNum,pageSize,teacherName));
            return Result.success(freeTimeService.findByTeacherNameForStudent(pageNum,pageSize,teacherName,email));
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
    //获得当前登录老师的freetime列表
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
    @GetMapping("student/getonefreetime")
    public Result getFreeTime(@RequestHeader(name="Authorization",required = false) String token,@RequestParam Integer pageNum,@RequestParam Integer pageSize ,HttpServletResponse response,@RequestParam String teacheremail){
        try {
            System.out.println("teacher'sna111me");
            Map<String,Object> claims = JwtUtil.parseToken(token);
            String email = (String)claims.get("email");
            return Result.success(freeTimeService.findByTeacherNameForStudent(pageNum,pageSize,teacherService.findByEmail(teacheremail).getName(),email));
            //return Response.success(teachers);
        }catch (Exception e){
            System.out.println(4555);
            System.out.println(e.getMessage());
            response.setStatus(401);
            return Result.error("weidenglu");
        }
    }
    //修改为只显示该学生未预约的
    //学生获取未预约所有空闲时间
    @GetMapping("/freetime/getlist")
    public Result getallFreeTime(@RequestHeader(name="Authorization",required = false) String token,@RequestParam Integer pageNum,@RequestParam Integer pageSize ,@RequestParam(required = false) String inputname,HttpServletResponse response){
        try {
            System.out.println("getfreetime");
            Map<String,Object> claims = JwtUtil.parseToken(token);
            String email = (String)claims.get("email");
            System.out.println(inputname);

            if(inputname!=null){
                return Result.success(freeTimeService.findByTeacherNameForStudent(pageNum,pageSize,inputname,email));
            }
//            PageInfo<FreeTime> freeTimePageInfo=freeTimeService.findList(pageNum,pageSize);修改为未申请空闲时
            return Result.success(freeTimeService.findForStudentList(pageNum,pageSize,email));
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
            List<Appointment> appointments =  appointmentService.findByID(appointment.getId());
            FreeTime freeTime = freeTimeService.findByID(appointment.getId());
            Teacher teacher = teacherService.findByEmail(appointment.getTeacherEmail());
            for(Appointment a:appointments){
                if(a.getStudentEmail().equals(appointment.getStudentEmail())){
                    continue;
                }
                News news1 = new News();
                appointmentService.updateAStatusTo(1,appointment.getId(),a.getStudentEmail());
                news1.setContent("您对"+teacher.getName()+"老师在"+freeTime.getDate()+"日的预约未通过！老师拒绝理由为："+"已被其他学生预约");
                news1.setTitle("预约未通过");
                news1.setIsread(false);
                news1.setSender("system");
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                news1.setDate(formatter.format(date));
                System.out.println("wda");
                System.out.println(news1.toString());
                news1.setEmail(a.getStudentEmail());
                newsService.inserrtNews(news1);
            }
            freeTimeService.updateStatus(freeTime.getId(),true);
            schedule schedule1 = new schedule();
            String student_name = studentService.findStudentByEmail(appointment.getStudentEmail()).getName();
            schedule1.setPlan("空闲时间预约："+student_name+"预约了您");
            schedule1.setTeacher_email(appointment.getTeacherEmail());
            schedule1.setTimeslot(appointment.getTimeSlot());
            schedule1.setDate(freeTime.getDate().split("T")[0]);
            scheduleService.addschedule(schedule1);
            News news1 = new News();
            news1.setContent("您对"+teacher.getName()+"老师在"+freeTime.getDate()+"日的预约已成功！请联系老师并准时参加！！");
            news1.setTitle("预约成功");
            news1.setSender("system");
            news1.setIsread(false);
            news1.setEmail(studentService.findStudentByEmail(appointment.getStudentEmail()).getEmail());
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            news1.setDate(formatter.format(date));
            System.out.println(news1.toString());
            newsService.inserrtNews(news1);
            News news2 = new News();
            news2.setIsread(false);
            news2.setContent("您已通过对"+student_name+"同学在"+freeTime.getDate()+"日的预约申请，同学会在近期跟您联系请注意留意！");
            news2.setTitle("预约通过成功");
            news2.setEmail(appointment.getTeacherEmail());
            news2.setDate(formatter.format(date));
            news2.setSender("system");
            System.out.println(news2.toString());
            newsService.inserrtNews(news2);
            appointmentService.updateAStatusTo(3,appointment.getId(),appointment.getStudentEmail());
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
            news1.setSender("system");
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            news1.setDate(formatter.format(date));
            System.out.println(news1.toString());
            newsService.inserrtNews(news1);
            appointmentService.updateAStatusTo(1,appointment.getId(),appointment.getStudentEmail());
            return Result.success("");
            //return Response.success(teachers);
        }catch (Exception e){
            System.out.println(4555);
            System.out.println(e.toString());
            //response.setStatus(401);
            return Result.error("weidenglu");
        }
    }

    //添加学生获取已申请freetime
    @GetMapping("/freeTimes/gotfreetime")
    public Result  findgotfreetime(@RequestHeader(name="Authorization",required = false) String token,@RequestParam Integer pageNum,@RequestParam Integer pageSize ,HttpServletResponse response){
        try {
            System.out.println(1252);
            Map<String,Object> claims = JwtUtil.parseToken(token);
            String email = (String)claims.get("email");
            List<FreeTime> freeTimeList=freeTimeService.findAll();
            List<FreeTime> freeTimes=new ArrayList<>();
            List<Appointment> appointmentList=appointmentService.findByStudentEmail(email);
            for(Appointment appointment:appointmentList){
                for (FreeTime freeTime :freeTimeList){
                    if(freeTime.getId()==appointment.getId()){
                       freeTimes.add(freeTime);
                    }
                }
            }
            return Result.success(freeTimes);
//            return Result.success(freeTimeService.findByTeacherNameForStudent(pageNum,pageSize,teacherName,email));
        }catch (Exception e){
            System.out.println(e.getMessage());
            response.setStatus(401);
            return Result.error("weidenglu");
        }
    }
    //老师撤销
    @PostMapping("/teacher/resetstudent")
    public Result resetstudent(@RequestHeader(name="Authorization",required = false) String token, @RequestBody Appointment appointment){
        try {

            // 打印日志
            System.out.println("Resetting student appointment: " + appointment.toString());
            // 查询相关的空闲时间条目
            FreeTime freeTime = freeTimeService.findByID(appointment.getId());
//            if(appointment.getAstatus() == 3){
            News news1 = new News();
            Teacher teacher = teacherService.findByEmail(appointment.getTeacherEmail());
            System.out.println(teacher.toString());
            news1.setContent("您对"+teacher.getName()+"老师在"+freeTime.getDate()+"日的预约被撤销！老师撤销理由为："+appointment.getRefusereason());
            news1.setTitle("预约被撤销");
            news1.setIsread(false);
            news1.setEmail(studentService.findStudentByEmail(appointment.getStudentEmail()).getEmail());
            news1.setSender("system");
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            news1.setDate(formatter.format(date));
            System.out.println(news1.toString());
            newsService.inserrtNews(news1);
            // 更新空闲时间状态为未选定
            //freeTimeService.updateStatus(freeTime.getId(), false);
            // 更新预约状态为未撤销学生
            appointmentService.updateAStatusTo(2, appointment.getId(),appointment.getStudentEmail());
            return Result.success("学生预约已成功重置");
//            } else {
//                return Result.error("只有已选定学生的预约才能重置");
//            }
        } catch (Exception e){
            // 记录异常信息
            System.out.println("Error resetting student appointment: " + e.getMessage());
            return Result.error("操作失败，请稍后重试");
        }
    }
}
