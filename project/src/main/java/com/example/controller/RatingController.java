package com.example.controller;

import com.example.pojo.Appointment;
import com.example.pojo.Rating;
import com.example.pojo.Result;
import com.example.service.Impl.AppointmentService;
import com.example.service.Impl.RatingService;
import com.example.service.Impl.TeacherService;
import com.example.utils.JwtUtil;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RatingController {
    @Data
    public static class RatingRequest {
        private int id;
        private float rating;
    }
    @Autowired
    private RatingService ratingService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private TeacherService teacherService;
    @GetMapping("/ratings")
    public Result findRating(String teacherEmail, String studentEmail){
       Rating rating=ratingService.findRating(teacherEmail,studentEmail);
       return Result.success(rating);
    }
    @DeleteMapping("/deleteratings")
    public Result deleteByTeacherAndStudentEmailAndTimeSlot(@RequestParam Rating rating){
        ratingService.deleteRating(rating.getTeacherEmail(),rating.getStudentEmail());
        return Result.success();
    }
//    //学生评分(平均)
//    @PostMapping("/student/setratings")
//    public Result resetstudent(@RequestHeader(name="Authorization",required = false) String token, @RequestBody Rating rating){
//        try {
//            //System.out.println(appointment.toString());
//            //if(appointment.getAstatus()==3){
//            System.out.println(rating.toString());
//            Map<String,Object> claims = JwtUtil.parseToken(token);
//            String email = (String)claims.get("email");
//            rating.setStudentEmail(email);
//                System.out.println("可评分");
//                if(ratingService.findRating(rating.getTeacherEmail(), rating.getStudentEmail())!=null){
//                    System.out.println("平均分");
//                    Rating oldrating = ratingService.findRating(rating.getTeacherEmail(), rating.getStudentEmail());
//                    float newrating = (oldrating.getRating() + rating.getRating())/2;
//                    ratingService.updateRating(rating.getTeacherEmail(),rating.getStudentEmail(),newrating);
//                    return Result.success("成功计分");
//                }
//                else {
//                    ratingService.addRating(rating.getTeacherEmail(),rating.getStudentEmail(),rating.getRating());
//                    return Result.success("成功计分");
//                }
//            //}
//
//        }catch (Exception e){
//            System.out.println(4555);
//            System.out.println(e.getMessage());
//            return Result.error("weidenglu");
//        }
//    }
//学生评分(平均)
@PostMapping("/student/setratings")
public Result resetstudent(@RequestHeader(name="Authorization",required = false) String token, @RequestBody RatingRequest ratingRequest){
    try {
        //System.out.println(appointment.toString());
        //if(appointment.getAstatus()==3){
        int id = ratingRequest.getId();
        float rating = ratingRequest.getRating();
        Map<String,Object> claims = JwtUtil.parseToken(token);
        String email = (String)claims.get("email");
        System.out.println(appointmentService.findBykey(id,email));
        Appointment appointment=appointmentService.findBykey(id,email);
        System.out.println("可评分");
        if(ratingService.findRating(appointment.getTeacherEmail(), appointment.getStudentEmail())!=null){
            System.out.println("平均分");
            Rating oldrating = ratingService.findRating(appointment.getTeacherEmail(), appointment.getStudentEmail());
            float newrating = (oldrating.getRating() + rating)/2;
            ratingService.updateRating(appointment.getTeacherEmail(),appointment.getStudentEmail(),newrating);
            appointmentService.updateAStatusTo(4,appointment.getId(),email);
            return Result.success("成功计分");
        }
        else {
            ratingService.addRating(appointment.getTeacherEmail(),appointment.getStudentEmail(),rating);
            return Result.success("成功计分");
        }
        //}

    }catch (Exception e){
        System.out.println(4555);
        System.out.println(e.getMessage());
        return Result.error("weidenglu");
    }
}

    //学生获取已评分表
    @GetMapping("/student/getratings")
    public Result getratings(@RequestHeader(name="Authorization",required = false) String token, @RequestParam Integer pageNum, @RequestParam Integer pageSize , HttpServletResponse response) {
        try {
            System.out.println(1252);
            Map<String, Object> claims = JwtUtil.parseToken(token);
            String email = (String) claims.get("email");
            PageInfo<Rating> selfratings=ratingService.findstudentRating1(pageNum,pageSize,email);
            List<Map<String, Object>> ratingInfoList = new ArrayList<>();
            for (Rating rating : selfratings.getList()) {
                String teacherName = teacherService.findByEmail(rating.getTeacherEmail()).getName();
                // 创建 Map 对象，并设置属性
                Map<String, Object> ratingInfo = new HashMap<>();
                ratingInfo.put("studentEmail", rating.getStudentEmail());
                ratingInfo.put("teacherEmail", rating.getTeacherEmail());
                ratingInfo.put("rating", rating.getRating());
                ratingInfo.put("teacherName", teacherName); // 设置老师姓名
                ratingInfoList.add(ratingInfo);
            }
            // 返回成功的结果，携带评分信息列表
            return Result.success(ratingInfoList);
            //return Response.success(teachers);
        } catch (Exception e) {
            System.out.println(4555);
            System.out.println(e.getMessage());
            response.setStatus(401);
            return Result.error("weidenglu");
        }
    }
    //老师查看自己的评价
    @GetMapping("/teacher/getselfrating")
    public Result getselfrating(@RequestHeader(name="Authorization",required = false) String token, HttpServletResponse response) {
        try {
            System.out.println("getrating");
            Map<String, Object> claims = JwtUtil.parseToken(token);
            String email = (String) claims.get("email");
            List<Rating> stores=ratingService.findteacherRating(email);
            float sumstore=0;
            for(Rating store : stores){
                sumstore+=store.getRating();
            }
            if(stores.size()==0){return Result.success("暂无"); }
            float aver=sumstore/stores.size();
            if(aver>=4.5){
                return Result.success("A+");
            }
            else if(aver<4.5&&aver>=4){
                return Result.success("A");
            }
            else if(aver<4&&aver>3){
                return Result.success("B");
            }
            else if(aver==0){
                return Result.success("暂无");
            }
            else{
                return Result.success("C");
            }
            //return Response.success(teachers);
        } catch (Exception e) {
            System.out.println(4555);
            System.out.println(e.getMessage());
            response.setStatus(401);
            return Result.error("weidenglu");
        }
    }
}