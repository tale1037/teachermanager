package com.example.mapper;

import com.example.pojo.FreeTime;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FreeTimeMapper {

    // 查询所有空闲时间信息
    @Select("SELECT * FROM freetime ORDER BY date ASC, timeSlot ASC")
    List<FreeTime> findAll();
    @Select("SELECT * FROM freetime WHERE Tstatus = 0 and id NOT IN (SELECT id FROM appointment where appointment.studentEmail = #{studentEmail})")
    List<FreeTime> findFreeTimeno(String studentEmail);
    @Select("SELECT * FROM freetime WHERE teacherEmail = #{teacherEmail} and Tstatus = 0 and id NOT IN (SELECT id FROM appointment where appointment.studentEmail = #{studentEmail}) ORDER BY date ASC, timeSlot ASC")
    List<FreeTime> findByTeacherEmail2(String teacherEmail,String studentEmail);

    @Select("SELECT * FROM freetime WHERE Tstatus=false ORDER BY date ASC, timeSlot ASC")
    List<FreeTime> findAllNo();

    // 根据教师邮箱查询空闲时间信息
    @Select("SELECT * FROM freetime WHERE teacherEmail = #{teacherEmail} ORDER BY date ASC, timeSlot ASC")
    List<FreeTime> findByTeacherEmail(String teacherEmail);



    // 新增空闲时间信息
    @Insert("INSERT INTO freetime(timeSlot, teacherEmail,date,id,tips,Tstatus) VALUES (#{timeSlot}, #{teacherEmail},#{date}, #{id},#{tips},#{Tstatus})")
    @Results({
            @Result(property = "timeSlot", column = "timeSlot"),
            @Result(property = "teacherEmail", column = "teacherEmail"),
            @Result(property = "date", column = "date"),
            @Result(property = "id", column = "id"),
            @Result(property = "tips", column = "tips"),
            @Result(property = "Tstatus", column = "Tstatus")
    })
    void insert(FreeTime freeTime);

    // 根据教师邮箱和时间段删除空闲时间信息
    @Delete("DELETE FROM freetime WHERE teacherEmail = #{teacherEmail} AND timeSlot = #{timeSlot}")
    void deleteByTeacherEmailAndTimeSlot(@Param("teacherEmail") String teacherEmail, @Param("timeSlot") String timeSlot);

    // 根据老师邮箱删除空闲时间记录
    @Delete("DELETE FROM freetime WHERE teacherEmail = #{teacherEmail}")
    void deleteByTeacherEmail(@Param("teacherEmail") String teacherEmail);
    @Delete("DELETE FROM freetime WHERE id = #{id}")
    void deleteById(@Param("id") int id);
    @Update("UPDATE freetime SET timeSlot = #{timeSlot}, teacherEmail = #{teacherEmail}, date = #{date}, tips = #{tips}, Tstatus = #{Tstatus} WHERE id = #{id}")
    void update(FreeTime freeTime);
    @Update("UPDATE freetime SET Tstatus = #{Tstatus} WHERE id = #{id}")
    void updateStatus(@Param("id") int id, @Param("Tstatus") boolean Tstatus);

    @Select("SELECT * from freetime where id = #{id} ORDER BY date ASC, timeSlot ASC")
    FreeTime findById(int id);
}
