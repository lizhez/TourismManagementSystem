package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("pay")
@Data
public class Pay {
    private int id;
    private int payid;
    private int fid;
    private String fname;
    private String favator;
    private String fphone;
    private String faddress;
    private int toid;
    private String toname;
    private String toavator;
    private String tophone;
    private String toaddress;
    private int tid;
    private int num;
    private double pay;
    private double total;
    private String title;
    private String firstImg;
    private int status;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    private LocalDateTime createtime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    private LocalDateTime paytime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    private LocalDateTime finishtime;
}
