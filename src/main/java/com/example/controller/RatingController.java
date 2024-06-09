package com.example.controller;

import com.example.pojo.Appointment;
import com.example.pojo.Result;
import com.example.service.Impl.AppointmentService;
import com.example.service.Impl.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class RatingController {
//    void addRating(String teacherEmail, String studentEmail, float rating);
//
//    Float findRating(String teacherEmail, String studentEmail);
//
//    void updateRating(String teacherEmail, String studentEmail, float rating);
//
//    void deleteRating(String teacherEmail, String studentEmail);
//    void deleteRatingByteacherEmail(String teacherEmail);
//    void deleteRatingBystudentEmail(String studentEmail);
    @Autowired
    private RatingService ratingService;
    @GetMapping("/ratings")
    public Result findRating(String teacherEmail, String studentEmail){
       Float rating=ratingService.findRating(teacherEmail,studentEmail);
       return Result.success(rating);
    }
    @DeleteMapping("/ratings/{teacherEmail}/{studentEmail}")
    public Result deleteByTeacherAndStudentEmailAndTimeSlot(@PathVariable String teacherEmail,@PathVariable String studentEmail){
        ratingService.deleteRating(teacherEmail,studentEmail);
        return Result.success();
    }
}
