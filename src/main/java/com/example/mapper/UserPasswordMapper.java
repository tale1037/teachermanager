package com.example.mapper;

import com.example.pojo.UserPassword;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserPasswordMapper {

    // 根据邮箱查询用户密码信息
    @Select("SELECT * FROM userpassword WHERE email = #{email}")
    UserPassword findUserPasswordByEmail(String email);
    // 根据邮箱查询用户密码信息
    @Select("SELECT * FROM userpassword")
    List<UserPassword> findUserPasswordAll();
    // 新增用户密码信息
    @Insert("INSERT INTO userpassword(email, password, isTeacher) " +
            "VALUES (#{email}, #{password}, #{isTeacher})")
    void insertUserPassword(UserPassword userPassword);

    // 更新用户密码信息
    @Update("UPDATE userpassword SET password = #{password}, isTeacher = #{isTeacher} WHERE email = #{email}")
    void updateUserPassword(UserPassword userPassword);
    // 根据邮箱删除用户密码信息
    @Delete("DELETE FROM user_password WHERE email = #{email}")
    void deleteUserPasswordByEmail(String email);
}

