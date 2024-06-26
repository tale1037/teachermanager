package com.example.mapper;


import com.example.pojo.schedule;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    @Select("SELECT * from schedule where teacher_email = #{email}")
    List<schedule> getbyEmail(String email);

    @Insert("INSERT into schedule(teacher_email,timeslot,date,plan) values (#{teacher_email},#{timeslot},#{date},#{plan})")
    void addschedule(schedule schedule);

    @Delete("DELETE from schedule where id = #{id}")
    void deleteschedule(int id);

    @Delete("DELETE from schedule where date < #{today}")
    void cleanSchedule(String today);
}
