package com.example.mapper;

import com.example.pojo.Appointment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AppointmentMapper {

    // 查询所有预约时间信息
    @Select("SELECT * FROM appointment ORDER BY timeSlot ASC")
    List<Appointment> findAll();

    // 根据教师邮箱查询预约时间信息
    @Select("SELECT * FROM appointment WHERE teacherEmail = #{teacherEmail} ORDER BY Astatus ASC")
    List<Appointment> findByTeacherEmail(String teacherEmail);

    // 根据学生邮箱查询预约时间信息
    @Select("SELECT a.*\n" +
            "FROM appointment a\n" +
            "JOIN freetime f ON a.id = f.id\n" +
            "WHERE a.studentEmail = #{studentEmail}\n" +
            "ORDER BY f.date DESC\n")
    List<Appointment> findByStudentEmail(String studentEmail);


    // 根据教师邮箱、学生邮箱和时间段删除预约时间信息
    @Delete("DELETE FROM appointment WHERE teacherEmail = #{teacherEmail} AND studentEmail = #{studentEmail} AND timeSlot = #{timeSlot}")
    void deleteByTeacherAndStudentEmailAndTimeSlot(@Param("teacherEmail") String teacherEmail, @Param("studentEmail") String studentEmail, @Param("timeSlot") String timeSlot);

    @Delete("DELETE FROM appointment WHERE teacherEmail = #{teacherEmail}")
    void deleteByTeacherEmail(@Param("teacherEmail") String teacherEmail);
    @Delete("DELETE FROM appointment WHERE studentEmail = #{studentEmail}")
    void deleteByStudentEmail(@Param("studentEmail") String studentEmail);

    @Delete("DELETE FROM appointment where id = #{id} AND studentEmail = #{studentEmail}")
    void deleteBykey(@Param("id") int id,@Param("studentEmail") String studentEmail);//改为deletebykey,增加了@Param("studentEmail"

    @Delete("DELETE FROM appointment WHERE id = #{id}")
    void deleteByid(@Param("id") int id);//改为deletebyid

    @Insert("INSERT INTO appointment (teacherEmail, studentEmail, timeSlot,id,Astatus,reason) VALUES (#{teacherEmail}, #{studentEmail}, #{timeSlot},#{id},#{Astatus},#{reason})")
    void add(@Param("teacherEmail") String teacherEmail, @Param("studentEmail") String studentEmail, @Param("timeSlot") String timeSlot,@Param("id") int id,@Param("Astatus") int Astatus,@Param("reason") String reason);

    @Select("SELECT * from appointment where teacherEmail = #{teacherEmail} and timeSlot = #{timeSlot} ORDER BY timeSlot ASC")
    List<Appointment> findByTeacherEmailandTimeSlot(String teacherEmail, String timeSlot);

    @Update("UPDATE appointment SET Astatus = #{Astatus} WHERE id = #{id} AND studentEmail = #{studentEmail} ")
    void updateAStatusTo(@Param("Astatus") int Astatus ,@Param("id") int id,@Param("studentEmail") String studentEmail);
    // 根据id查询预约时间信息
    @Select("Select * from appointment where id = #{id} ORDER BY timeSlot ASC")
    List<Appointment> findByID(int id);
    @Select("Select * from appointment where id = #{id} AND studentEmail = #{studentEmail} ORDER BY timeSlot ASC")
    Appointment findBykey(int id,String studentEmail);
}