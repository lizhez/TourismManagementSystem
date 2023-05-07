package com.example.demo.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TavelUser implements Serializable {
    public int id;
    public int uid;
    public int leaves;
    public int finishes;
    public double pay;
    public String title;
    public String meg;
    public String firstImg;
    public String context;
    public int gban;
    public int active;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    public LocalDateTime time;
    public String paths;
    private int istop;
    public String name;
    public String avator;
    public String phone;
    public String email;
    public String address;
    public int level;
    private int love;
    private int view;
}
