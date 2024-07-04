package com.example.service;

import com.example.mapper.AppointmentMapper;
import com.example.mapper.FreeTimeMapper;
import com.example.mapper.StudentMapper;
import com.example.mapper.TeacherMapper;
import com.example.pojo.Appointment;
import com.example.service.Impl.AppointmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AppointmentServiceImpl implements AppointmentService {
        @Autowired
        private AppointmentMapper appointmentMapper;
        @Autowired
        private StudentMapper studentMapper;
        @Autowired
        private FreeTimeMapper freeTimeMapper;
        @Autowired
        private TeacherMapper teacherMapper;
        public List<Appointment> findAll(){
         List<Appointment> appointmentList=appointmentMapper.findAll();
         return appointmentList;
        }

        public List<Appointment> findByTeacherEmail(String teacherEmail){
                List<Appointment> appointmentList=appointmentMapper.findByTeacherEmail(teacherEmail);
                for (Appointment appointment : appointmentList){
                        appointment.setStudent_name(studentMapper.findStudentByEmail(appointment.getStudentEmail()).getName());
                        appointment.setDate(freeTimeMapper.findById(appointment.getId()).getDate());
                }
                return appointmentList;
        }

        public List<Appointment> findByStudentEmail(String studentEmail){
                List<Appointment> appointmentList=appointmentMapper.findByStudentEmail(studentEmail);
                return appointmentList;
        }

        @Override
        public void updateAStatusTo(int Astatus, int id,String studentEmail) {
                appointmentMapper.updateAStatusTo(Astatus,id,studentEmail);
        }

        public void deleteByTeacherAndStudentEmailAndTimeSlot(String teacherEmail, String studentEmail, String timeSlot){
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
        public void deleteid(int id) {
                appointmentMapper.deleteByid(id);
        }

        @Override
        public void add(String teacherEmail, String studentEmail, String timeSlot,int id,int Astatus,String reason) {
                appointmentMapper.add(teacherEmail,studentEmail,timeSlot,id,Astatus,reason);
        }

        @Override
        public PageInfo<Appointment> getlistbystuEmail(int pageNum, int pageSize, String email) {
                PageHelper.startPage(pageNum, pageSize);
                List<Appointment> appointments=appointmentMapper.findByStudentEmail(email);
                for (Appointment appointment : appointments){
                        appointment.setTeacher_name(teacherMapper.findByEmail(appointment.getTeacherEmail()).getName());
                        appointment.setDate(freeTimeMapper.findById(appointment.getId()).getDate());
                }
                return PageInfo.of(appointments);
        }

        @Override
        public List<Appointment> findByID(int id) {
                return appointmentMapper.findByID(id);
        }

        @Override
        public Appointment findBykey(int id, String studentEmail) {
                return appointmentMapper.findBykey(id,studentEmail);
        }

}
