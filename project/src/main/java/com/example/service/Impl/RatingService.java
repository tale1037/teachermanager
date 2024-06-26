package com.example.service.Impl;

import com.example.pojo.Rating;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RatingService {

    void addRating(String teacherEmail, String studentEmail, float rating);

    Rating findRating(String teacherEmail, String studentEmail);

    void updateRating(String teacherEmail, String studentEmail, float rating);

    void deleteRating(String teacherEmail, String studentEmail);
    void deleteRatingByteacherEmail(String teacherEmail);
    void deleteRatingBystudentEmail(String studentEmail);
    List<Rating> findteacherRating(String teacherEmail);
    List<Rating> findstudentRating(String studentEmail);

    PageInfo<Rating> findstudentRating1(int pageNum, int pageSize, String studentEmail);
    List<Rating> findAll();
}
