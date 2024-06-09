package com.example.service;

import com.example.mapper.UserPasswordMapper;
import com.example.pojo.UserPassword;
import com.example.service.Impl.UserPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPasswordServiceImpl implements UserPasswordService {

        @Autowired
        private UserPasswordMapper userPasswordMapper;

        @Override
        public UserPassword findUserPasswordByEmail(String email) {
                UserPassword userPassword = userPasswordMapper.findUserPasswordByEmail(email);
                return userPassword;
        }

        @Override
        public void insertUserPassword(UserPassword userPassword) {
                userPasswordMapper.insertUserPassword(userPassword);
        }

        @Override
        public void updateUserPassword(UserPassword userPassword) {
                userPasswordMapper.updateUserPassword(userPassword);
        }

        @Override
        public void deleteUserPasswordByEmail(String email) {
                userPasswordMapper.deleteUserPasswordByEmail(email);
        }

        @Override
        public List<UserPassword> findUserPasswordAll() {
                List<UserPassword> userPasswordList=userPasswordMapper.findUserPasswordAll();
                return userPasswordList;
        }
}
