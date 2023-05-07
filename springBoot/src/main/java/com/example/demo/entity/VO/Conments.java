package com.example.demo.entity.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@TableName("conment")
@Data
public class Conments {
    private int id;
    private int uid;
    private String name;
    private String avator;
    private int pid;//父评论id
    private int belongid;
    private String context;
    private int active;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    private LocalDateTime time;

    //回复评论
    @TableField(exist = false)
    private List<Conments> replyConments = new ArrayList<>();
    @TableField(exist = false)
    private Conments parentConment;
    @TableField(exist = false)
    private String parentname;
}
