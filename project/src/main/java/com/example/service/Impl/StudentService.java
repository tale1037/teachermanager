package com.example.service.Impl;

import com.example.pojo.Student;

import java.util.List;

public interface StudentService {

    List<Student> findAllStudents();

    Student findStudentByEmail(String email);
    List<Student> findStudentByName(String name);
    void addStudent(Student student);

    void deleteStudentByEmail(String email);
    void updateStudent(Student student);
}
