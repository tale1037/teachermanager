package com.example.service;

import com.example.mapper.StudentMapper;
import com.example.pojo.Student;
import com.example.service.Impl.AppointmentService;
import com.example.service.Impl.RatingService;
import com.example.service.Impl.StudentService;
import com.example.service.Impl.UserPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private  StudentMapper studentMapper;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserPasswordService userPasswordService;
    @Override
    public List<Student> findAllStudents() {
        List<Student> studentList=studentMapper.findAllStudents();
        return studentList;
    }

    @Override
    public Student findStudentByEmail(String email) {
       Student student=studentMapper.findStudentByEmail(email);
       return student;
    }

    @Override
    public List<Student> findStudentByName(String name) {
        return studentMapper.findStudentByName(name);
    }
    @Override
    public void addStudent(Student student) {
        studentMapper.addStudent(student);
    }

    @Override
    public void deleteStudentByEmail(String email) {
       studentMapper.deleteStudentByEmail(email);
        appointmentService.findByStudentEmail(email);
        ratingService.deleteRatingBystudentEmail(email);
        userPasswordService.deleteUserPasswordByEmail(email);
    }
    public void updateStudent(Student student){
        studentMapper.updateStudent(student);
    }
}
