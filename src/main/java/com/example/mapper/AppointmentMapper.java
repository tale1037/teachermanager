package com.example.mapper;

import com.example.pojo.Appointment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AppointmentMapper {

    // 查询所有预约时间信息
    @Select("SELECT * FROM appointment")
    List<Appointment> findAll();

    // 根据教师邮箱查询预约时间信息
    @Select("SELECT * FROM appointment WHERE teacherEmail = #{teacherEmail}")
    List<Appointment> findByTeacherEmail(String teacherEmail);

    // 根据学生邮箱查询预约时间信息
    @Select("SELECT * FROM appointment WHERE studentEmail = #{studentEmail}")
    List<Appointment> findByStudentEmail(String studentEmail);

    // 根据教师邮箱、学生邮箱和时间段删除预约时间信息
    @Delete("DELETE FROM appointment WHERE teacherEmail = #{teacherEmail} AND studentEmail = #{studentEmail} AND timeSlot = #{timeSlot}")
    void deleteByTeacherAndStudentEmailAndTimeSlot(@Param("teacherEmail") String teacherEmail, @Param("studentEmail") String studentEmail, @Param("timeSlot") int timeSlot);

    @Delete("DELETE FROM appointment WHERE teacherEmail = #{teacherEmail}")
    void deleteByTeacherEmail(@Param("teacherEmail") String teacherEmail);
    @Delete("DELETE FROM appointment WHERE studentEmail = #{studentEmail}")
    void deleteByStudentEmail(@Param("studentEmail") String studentEmail);

    @Insert("INSERT INTO appointment (teacherEmail, studentEmail, timeSlot) VALUES (#{teacherEmail}, #{studentEmail}, #{timeSlot})")
    void add(@Param("teacherEmail") String teacherEmail, @Param("studentEmail") String studentEmail, @Param("timeSlot") int timeSlot);
}