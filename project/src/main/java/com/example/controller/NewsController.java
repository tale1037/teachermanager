package com.example.controller;


import com.example.pojo.News;
import com.example.pojo.Result;
import com.example.pojo.Student;
import com.example.service.Impl.NewsService;
import com.example.service.Impl.StudentService;
import com.example.service.Impl.TeacherService;
import com.example.service.Impl.UserPasswordService;
import com.example.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class NewsController {
        @Autowired
        NewsService newsService;
        @Autowired
        UserPasswordService userPasswordService;
        @Autowired
        TeacherService teacherService;
        @Autowired
        StudentService studentService;
        @GetMapping("/user/getmynews")
        public Result getMyNews(@RequestHeader(name="Authorization",required = false) String token, HttpServletResponse response) {
                try {
                       // System.out.println(1);
                        Map<String,Object> claims = JwtUtil.parseToken(token);
                        String email = (String)claims.get("email");
                        //System.out.println(claims.toString());
                        boolean isteacher = (boolean)claims.get("isteacher");
                        return Result.success(newsService.findByEmail(email));
                        //return Response.success(teachers);
                }catch (Exception e){
                        System.out.println(e.getMessage());
                        response.setStatus(401);
                        return Result.error("weidenglu");
                }
        }
        @PostMapping("/user/setread")
        public Result setRead(@RequestHeader(name="Authorization",required = false) String token, HttpServletResponse response, @RequestBody News news) {
                newsService.setreadByID(news.getId());
                return Result.success(1);
        }
        @PostMapping("user/sendnews")
        public Result sendnews(@RequestHeader(name="Authorization",required = false) String token, HttpServletResponse response, @RequestBody News news) {
                try {
                        System.out.println(1);
                        System.out.println(news.toString());
                        Map<String,Object> claims = JwtUtil.parseToken(token);
                        String email = (String)claims.get("email");
                        boolean isteacher = (boolean)claims.get("isteacher");
                        System.out.println(isteacher);
                        System.out.println(claims.toString());
                        news.setTitle("留言");
                        Date date = new Date();
                        news.setIsread(false);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        news.setDate(sdf.format(date));
                        if(news.getSender()!="system"&&news.getSender()!=null)
                        {
                                if(isteacher){
                                        news.setEmail(studentService.findStudentByName(news.getSender()).get(0).getEmail());
                                }
                                else
                                      news.setEmail(teacherService.findByName(news.getSender()).get(0).getEmail());
                        }
                        if(isteacher){
                                news.setSender(teacherService.findByEmail(email).getName());
                        }
                        else {
                                news.setSender(studentService.findStudentByEmail(email).getName());
                        }
                        newsService.inserrtNews(news);
                        return Result.success(1);
                        //return Response.success(teachers);
                }catch (Exception e) {
                        System.out.println(e.getMessage());
                        response.setStatus(401);
                        return Result.error("weidenglu");
                }

        }
        @PostMapping("/user/deletenews")
        public Result delete(@RequestHeader(name="Authorization",required = false) String token, HttpServletResponse response, @RequestBody News news) {
                try {
                        System.out.println(1);
                        Map<String,Object> claims = JwtUtil.parseToken(token);
                        String email = (String)claims.get("email");
                        System.out.println(claims.toString());
                        newsService.deleteByEmail(email);
                        return Result.success(1);
                        //return Response.success(teachers);
                }catch (Exception e){
                        System.out.println(e.getMessage());
                        response.setStatus(401);
                        return Result.error("weidenglu");
                }
        }
        @PostMapping("/user/inserinfo")
        public Result insert(@RequestHeader(name="Authorization",required = false) String token, HttpServletResponse response, @RequestBody News news) {
                newsService.inserrtNews(news);
                return Result.success(1);
        }

        @PostMapping("/user/cleannews")
        public Result clean(@RequestHeader(name="Authorization",required = false) String token, HttpServletResponse response) {
                try {
                        System.out.println(1);
                        Map<String,Object> claims = JwtUtil.parseToken(token);
                        String email = (String)claims.get("email");
                        System.out.println(claims.toString());
                        newsService.deleteByEmailandisread(email);
                        return Result.success(1);
                        //return Response.success(teachers);
                }catch (Exception e){
                        System.out.println(e.getMessage());
                        response.setStatus(401);
                        return Result.error("weidenglu");
                }
        }

}
