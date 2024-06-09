package com.example.mapper;

import com.example.pojo.FreeTime;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FreeTimeMapper {

    // 查询所有空闲时间信息
    @Select("SELECT * FROM freetime")
    List<FreeTime> findAll();

    // 根据教师邮箱查询空闲时间信息
    @Select("SELECT * FROM freetime WHERE teacherEmail = #{teacherEmail}")
    List<FreeTime> findByTeacherEmail(String teacherEmail);

    // 新增空闲时间信息
    @Insert("INSERT INTO freetime(timeSlot, teacherEmail) VALUES (#{timeSlot}, #{teacherEmail})")
    void insert(FreeTime freeTime);

    // 根据教师邮箱和时间段删除空闲时间信息
    @Delete("DELETE FROM freetime WHERE teacherEmail = #{teacherEmail} AND timeSlot = #{timeSlot}")
    void deleteByTeacherEmailAndTimeSlot(@Param("teacherEmail") String teacherEmail, @Param("timeSlot") int timeSlot);

    // 根据老师邮箱删除空闲时间记录
    @Delete("DELETE FROM FreeTime WHERE teacherEmail = #{teacherEmail}")
    void deleteByTeacherEmail(@Param("teacherEmail") String teacherEmail);
}
