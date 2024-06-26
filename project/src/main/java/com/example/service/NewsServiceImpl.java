package com.example.service;


import com.example.mapper.NewsMapper;
import com.example.pojo.News;
import com.example.service.Impl.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {


    @Autowired
    NewsMapper newsMapper;
    @Override
    public List<News> findByEmail(String email) {
        return newsMapper.findByEmail(email);
    }

    @Override
    public void setreadByID(int id) {
        newsMapper.setreadByID(id);
    }

    @Override
    public void deleteByEmail(String email) {
        newsMapper.deleteByEmail(email);
    }

    @Override
    public void inserrtNews(News news) {
        newsMapper.insert(news);
    }

    @Override
    public void cleanSchedule(String today) {
        newsMapper.cleanSchedule(today);
    }

    @Override
    public void deleteByEmailandisread(String email) {
        newsMapper.deleteByEmailandisread(email);
    }

}
