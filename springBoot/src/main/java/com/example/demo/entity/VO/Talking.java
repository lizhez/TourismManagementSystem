package com.example.demo.entity.VO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("talking")
@Data
public class Talking {
    private int id;
    private String content;

    private int fid;
    private String favator;

    private int toid;
    private String toavator;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    private LocalDateTime time;
}
