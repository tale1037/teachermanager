package com.example.service.Impl;

import org.apache.ibatis.annotations.Param;

public interface RatingService {

    void addRating(String teacherEmail, String studentEmail, float rating);

    Float findRating(String teacherEmail, String studentEmail);

    void updateRating(String teacherEmail, String studentEmail, float rating);

    void deleteRating(String teacherEmail, String studentEmail);
    void deleteRatingByteacherEmail(String teacherEmail);
    void deleteRatingBystudentEmail(String studentEmail);
}
