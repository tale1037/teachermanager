package com.example.controller;

import com.example.pojo.FreeTime;
import com.example.pojo.Result;
import com.example.pojo.UserPassword;
import com.example.service.Impl.FreeTimeService;
import com.example.service.Impl.UserPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        userPasswordService.insertUserPassword(userPassword);
        return  Result.success();
    }
    @PutMapping("/UserPasswords")
    public Result updateUserPassword(@RequestBody UserPassword userPassword){
        userPasswordService.updateUserPassword(userPassword);
        return  Result.success();
    }
}
