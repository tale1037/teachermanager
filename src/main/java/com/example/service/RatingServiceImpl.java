package com.example.service;

import com.example.mapper.RatingMapper;
import com.example.mapper.UserPasswordMapper;
import com.example.service.Impl.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

        @Autowired
        private RatingMapper ratingMapper;

        @Override
        public void addRating(String teacherEmail, String studentEmail, float rating) {
                ratingMapper.addRating(teacherEmail,studentEmail,rating);
        }

        @Override
        public Float findRating(String teacherEmail, String studentEmail) {
                Float rating=ratingMapper.findRating(teacherEmail,studentEmail);
                return rating;
        }

        @Override
        public void updateRating(String teacherEmail, String studentEmail, float rating) {
                ratingMapper.updateRating(teacherEmail,studentEmail,rating);
        }

        @Override
        public void deleteRating(String teacherEmail, String studentEmail) {
              ratingMapper.deleteRating(teacherEmail,studentEmail);
        }

        @Override
        public void deleteRatingByteacherEmail(String teacherEmail) {
                ratingMapper.deleteRatingByteacherEmail(teacherEmail);
        }

        @Override
        public void deleteRatingBystudentEmail(String studentEmail) {
                ratingMapper.deleteRatingBystudentEmail(studentEmail);
        }
}
