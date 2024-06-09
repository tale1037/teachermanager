package com.example.mapper;

import com.example.pojo.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    // 查询所有学生信息
    @Select("SELECT * FROM student")
    List<Student>findAllStudents();
    // 根据邮箱查询学生信息
    @Select("SELECT * FROM student WHERE email = #{email}")
    Student findStudentByEmail(String email);
    // 根据邮箱查询学生信息
    @Select("SELECT * FROM student WHERE name = #{name}")
    List<Student> findStudentByName(String name);
    // 新增学生信息
    @Insert("INSERT INTO student(name, studentId, email, major) " +
            "VALUES (#{name}, #{studentId}, #{email}, #{major})")
    void addStudent(Student student);
    // 根据邮箱删除学生信息
    @Delete("DELETE FROM student WHERE email = #{email}")
    void deleteStudentByEmail(String email);
    // 更新学生信息
    @Update("UPDATE student SET name = #{name}, studentId = #{studentId}, major = #{major} WHERE email = #{email}")
    void updateStudent(Student student);
}
