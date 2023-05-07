package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("img")
@Data
public class Img {
    private int id;
    private String url;
    private int status;
}
