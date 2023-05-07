package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("article")
@Data
public class Article {
    private int id;
    private int uid;
    private int love;
    private int view;
    private String title;
    private String meg;
    private String firstImg;
    private String context;
    private int active;
    private LocalDateTime time;
    private int istop;

}
