package com.example.controller;

import com.example.pojo.FreeTime;
import com.example.pojo.Result;
import com.example.pojo.UserPassword;
import com.example.service.Impl.FreeTimeService;
import com.example.service.Impl.UserPasswordService;
import com.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class UserPasswordController {
//    UserPassword findUserPasswordByEmail(String email);
//
//    void insertUserPassword(UserPassword userPassword);
//
//    void updateUserPassword(UserPassword userPassword);
//
//    void deleteUserPasswordByEmail(String email);
    @Autowired
    private UserPasswordService userPasswordService;
    @GetMapping("/UserPasswords")
    public Result findUserPasswordAll(){
        List<UserPassword> userPasswordList=userPasswordService.findUserPasswordAll();
        return Result.success(userPasswordList);
    }
    @GetMapping("/UserPasswords/{email}")
    public Result  findUserPasswordByEmail(@PathVariable String email){
        UserPassword userPassword=userPasswordService.findUserPasswordByEmail(email);
        return Result.success(userPassword);
    }
    @DeleteMapping("/UserPasswords/{email}")
    public Result deleteUserPasswordByEmail(@PathVariable String email){
       userPasswordService.deleteUserPasswordByEmail(email);
       return Result.success();
    }
    @PostMapping("/UserPasswords")
    public Result insertUserPassword(@RequestBody UserPassword userPassword){
        try {
            userPasswordService.insertUserPassword(userPassword);
            System.out.println(userPassword);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error("this email has been used");
        }
        return  Result.success();
    }
    @PostMapping("/user/login")
    public Result logint(@RequestBody UserPassword user) {
        System.out.println(user.toString());
        UserPassword userPassword = userPasswordService.findUserPasswordByEmail(user.getEmail());
        if(!(userPassword==null)) {
            System.out.println(userPassword.getEmail());
            System.out.println(userPassword.getPassword());
        }
        if (userPassword==null || !userPassword.getPassword().equals(user.getPassword())) {
            // model.addAttribute("error", "Invalid email or password");
            return Result.error("Invalid email or password");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("isteacher",userPassword.getIsTeacher());
        System.out.println(userPassword.getIsTeacher());
        String token = JwtUtil.genToken(claims);
        if (userPassword.getIsTeacher())
            return Result.success("teacher",token,true);
        else
            return Result.success("success",token,false);
    }
    @PutMapping("/UserPasswords")
    public Result updateUserPassword(@RequestBody UserPassword userPassword){
        userPasswordService.updateUserPassword(userPassword);
        return  Result.success();
    }
}
