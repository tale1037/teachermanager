package com.example.mapper;


import com.example.pojo.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsMapper {


    @Select("SELECT * from news where email = #{email}")
    List<News> findByEmail(String email);


    @Update("UPDATE news SET isread = true where id = #{id}")
    void setreadByID(int id);


    @Delete("DELETE FROM news where email = #{email}")
    void deleteByEmail(String email);

    @Insert("INSERT into news (title,content,email,isread,date,sender) values (#{title},#{content},#{email},#{isread},#{date},#{sender})")
    void insert(News news);

    @Delete("DELETE FROM news where date < #{today}")
    void cleanSchedule(String today);


    @Delete("DELETE FROM news where email = #{email} and isread = 1")
    void deleteByEmailandisread(String email);
}
