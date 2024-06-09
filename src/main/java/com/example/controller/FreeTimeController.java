package com.example.controller;

import com.example.pojo.FreeTime;
import com.example.pojo.Result;
import com.example.service.Impl.FreeTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/freeTimes")
    public Result findAll(){
        List<FreeTime> freeTimeList=freeTimeService.findAll();
        return Result.success(freeTimeList);
    }
    @GetMapping("/freeTimes/{teacherEmail}")
    public Result  findByTeacherEmail(@PathVariable String teacherEmailemail){
        List<FreeTime> freeTimeList=freeTimeService.findByTeacherEmail(teacherEmailemail);
        return Result.success(freeTimeList);
    }
    @DeleteMapping("/freeTimes/{teacherEmail}/{timeSlot}")
    public Result deleteByTeacherEmailAndTimeSlot(@PathVariable String teacherEmail,@PathVariable int timeSlot){
        freeTimeService.deleteByTeacherEmailAndTimeSlot(teacherEmail,timeSlot);
        return Result.success();
    }
    @DeleteMapping("/freeTimes/{teacherEmail}")
    public Result deleteByTeacherEmail(@PathVariable String teacherEmail){
        freeTimeService.deleteByTeacherEmail(teacherEmail);
        return Result.success();
    }
    @PostMapping("/freeTimes")
    public Result insert(@RequestBody FreeTime freeTime){
       freeTimeService.insert(freeTime);
        return  Result.success();
    }

}
