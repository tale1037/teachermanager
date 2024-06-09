package com.example.mapper;

import com.example.pojo.Student;
import com.example.pojo.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {

    // 查询所有教师信息
    @Select("SELECT * FROM teacher")
    List<Teacher> findAll();
    // 根据教师邮箱查询教师信息
    @Select("SELECT * FROM teacher WHERE email = #{email}")
    Teacher findByEmail(String email);
    @Select("SELECT * FROM teacher WHERE name = #{name}")
    List<Teacher> findByName(String name);
    // 新增教师信息
    @Insert("INSERT INTO teacher(name, department, title, email, researchArea, officeAddress) " +
            "VALUES (#{name}, #{department}, #{title}, #{email}, #{researchArea}, #{officeAddress})")
    void insert(Teacher teacher);
    // 根据教师部门查询教师信息
    @Select("SELECT * FROM teacher WHERE department = #{department}")
    List<Teacher> findByDepartment(String department);

    // 更新教师信息
    @Update("UPDATE teacher SET name = #{name}, department = #{department}, title = #{title}, " +
            "researchArea = #{researchArea}, officeAddress = #{officeAddress}, score = #{score} WHERE email = #{email}")
    void update(Teacher teacher);
    // 根据教师邮箱删除教师信息
    @Delete("DELETE FROM teacher WHERE email = #{email}")
    void deleteByEmail(String email);
    // 查询所有教师的部门
    @Select("SELECT DISTINCT department FROM teacher")
    List<String> findAllDepartments();
}

