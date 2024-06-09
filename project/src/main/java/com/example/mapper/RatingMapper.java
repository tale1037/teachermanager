package com.example.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface RatingMapper {

    // 新增评分信息
    @Insert("INSERT INTO rating(teacherEmail, studentEmail, rating) " +
            "VALUES (#{teacherEmail}, #{studentEmail}, #{rating})")
    void addRating(@Param("teacherEmail") String teacherEmail, @Param("studentEmail") String studentEmail, @Param("rating") float rating);

    // 根据教师邮箱和学生邮箱查询评分信息
    @Select("SELECT * FROM rating WHERE teacherEmail = #{teacherEmail} AND studentEmail = #{studentEmail}")
    Float findRating(@Param("teacherEmail") String teacherEmail, @Param("studentEmail") String studentEmail);

    // 更新评分信息
    @Update("UPDATE rating SET rating = #{rating} WHERE teacherEmail = #{teacherEmail} AND studentEmail = #{studentEmail}")
    void updateRating(@Param("teacherEmail") String teacherEmail, @Param("studentEmail") String studentEmail, @Param("rating") float rating);

    // 根据教师邮箱和学生邮箱删除评分信息
    @Delete("DELETE FROM rating WHERE teacherEmail = #{teacherEmail} AND studentEmail = #{studentEmail}")
    void deleteRating(@Param("teacherEmail") String teacherEmail, @Param("studentEmail") String studentEmail);
    // 根据教师邮箱删除评分信息
    @Delete("DELETE FROM rating WHERE teacherEmail = #{teacherEmail}")
    void deleteRatingByteacherEmail(@Param("teacherEmail") String teacherEmail);
    // 根据学生邮箱删除评分信息
    @Delete("DELETE FROM rating WHERE studentEmail = #{studentEmail}")
    void deleteRatingBystudentEmail(@Param("studentEmail") String studentEmail);
}

