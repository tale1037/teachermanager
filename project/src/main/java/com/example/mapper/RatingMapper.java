package com.example.mapper;

import com.example.pojo.Rating;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RatingMapper {
    // 查询所有空闲时间信息
    @Select("SELECT * FROM rating")
    List<Rating> findAll();
    // 新增评分信息
    @Insert("INSERT INTO rating(teacherEmail, studentEmail, rating) " +
            "VALUES (#{teacherEmail}, #{studentEmail}, #{rating})")
    void addRating(@Param("teacherEmail") String teacherEmail, @Param("studentEmail") String studentEmail, @Param("rating") float rating);

    // 根据教师邮箱和学生邮箱查询评分信息
    @Select("SELECT * FROM rating WHERE teacherEmail = #{teacherEmail} AND studentEmail = #{studentEmail}")
    Rating findRating(@Param("teacherEmail") String teacherEmail, @Param("studentEmail") String studentEmail);

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
    @Select("SELECT * FROM rating WHERE teacherEmail = #{teacherEmail}")
    List<Rating> findteacherRating(@Param("teacherEmail") String teacherEmail);
    @Select("SELECT * FROM rating WHERE studentEmail = #{studentEmail}")
    List<Rating> findstudentRating(@Param("studentEmail") String studentEmail);
}

