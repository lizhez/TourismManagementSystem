package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@TableName("user")
@Data
public class User {
//    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String password;
    private String sex;
    private String avator;
    private String phone;
    private String email;
    private String address;
    private int level;
    private int ban;
    private int status;
    private int type;
    private int istop;

    @TableField(exist = false)
    private List<Travel> travelList;

    @TableField(exist = false)
    private List<Article> articleList;
}
