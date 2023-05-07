package com.example.demo.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ArticleUser implements Serializable {
    public int id;
    public int uid;
    public int view;
    public int love;
    public String title;
    public String meg;
    public String firstImg;
    public String context;
    public int active;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    public LocalDateTime time;
    public String name;
    public String avator;
    private int istop;

}
