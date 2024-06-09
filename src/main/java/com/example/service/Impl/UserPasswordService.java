package com.example.service.Impl;

import com.example.pojo.UserPassword;

import java.util.List;

public interface UserPasswordService {

    UserPassword findUserPasswordByEmail(String email);

    void insertUserPassword(UserPassword userPassword);

    void updateUserPassword(UserPassword userPassword);

    void deleteUserPasswordByEmail(String email);
    List<UserPassword> findUserPasswordAll();
}
