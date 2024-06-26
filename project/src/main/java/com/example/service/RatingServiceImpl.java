package com.example.service;

import com.example.mapper.RatingMapper;
import com.example.pojo.Rating;
import com.example.service.Impl.RatingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

        @Autowired
        private RatingMapper ratingMapper;

        @Override
        public void addRating(String teacherEmail, String studentEmail, float rating) {
                ratingMapper.addRating(teacherEmail,studentEmail,rating);
        }

        @Override
        public Rating findRating(String teacherEmail, String studentEmail) {
                Rating rating=ratingMapper.findRating(teacherEmail,studentEmail);
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

        @Override
        public List<Rating> findteacherRating(String teacherEmail) {
                List<Rating> ratingList=ratingMapper.findteacherRating(teacherEmail);
                return ratingList;
        }
        @Override
        public List<Rating> findstudentRating(String studentEmail) {
                List<Rating> ratingList=ratingMapper.findstudentRating(studentEmail);
                return ratingList;
        }

        @Override
        public PageInfo<Rating> findstudentRating1(int pageNum, int pageSize, String studentEmail) {
                List<Rating> ratingList=ratingMapper.findstudentRating(studentEmail);
                PageHelper.startPage(pageNum, pageSize);
                return PageInfo.of(ratingList);
        }

        @Override
        public List<Rating> findAll() {
                return ratingMapper.findAll();
        }
}
