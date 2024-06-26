package com.example.service.Impl;

import com.example.pojo.News;

import java.util.List;

public interface NewsService {
    List<News> findByEmail(String email);

    void setreadByID(int id);

    void deleteByEmail(String email);

    void inserrtNews(News news);

    void cleanSchedule(String today);

    void deleteByEmailandisread(String email);
}
