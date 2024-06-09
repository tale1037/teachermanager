package com.example.service;

import com.example.mapper.AppointmentMapper;
import com.example.pojo.Appointment;
import com.example.service.Impl.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AppointmentServiceImpl implements AppointmentService {
        @Autowired
        private AppointmentMapper appointmentMapper;

        public List<Appointment> findAll(){
         List<Appointment> appointmentList=appointmentMapper.findAll();
         return appointmentList;
        }

        public List<Appointment> findByTeacherEmail(String teacherEmail){
                List<Appointment> appointmentList=appointmentMapper.findByTeacherEmail(teacherEmail);
                return appointmentList;
        }

        public List<Appointment> findByStudentEmail(String studentEmail){
                List<Appointment> appointmentList=appointmentMapper.findByStudentEmail(studentEmail);
                return appointmentList;
        }

        public void deleteByTeacherAndStudentEmailAndTimeSlot(String teacherEmail, String studentEmail, int timeSlot){
                appointmentMapper.deleteByTeacherAndStudentEmailAndTimeSlot(teacherEmail,studentEmail,timeSlot);
        }

        @Override
        public void deleteByTeacherEmail(String teacherEmail) {
                appointmentMapper.deleteByTeacherEmail(teacherEmail);
        }

        @Override
        public void deleteByStudentEmail(String studentEmail) {
                appointmentMapper.deleteByStudentEmail(studentEmail);
        }

        @Override
        public void add(String teacherEmail, String studentEmail, int timeSlot) {
                appointmentMapper.add(teacherEmail,studentEmail,timeSlot);
        }
}
