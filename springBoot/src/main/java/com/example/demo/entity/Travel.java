package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("travel")
@Data
public class Travel {
    private int id;
    private int uid;
    private int leaves;
    private int finishes;
    private double pay;
    private String title;
    private String meg;
    private String firstImg;
    private String context;
    private int gban;
    private int active;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    private LocalDateTime time;
    private String paths;
    private int istop;
    private int love;
    private int view;
}
