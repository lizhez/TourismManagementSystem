package com.example.demo;

import com.example.demo.entity.VO.Talking;
import com.example.demo.mapper.ArticleMapper;
import com.example.demo.mapper.PayMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.ITalkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCount {

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void paytype(){
        //0：1已支付；   1：2已取消； 2：0未支付； 3：-1已完成
        for(int i=0;i<4;i++)
            System.out.println(i+":"+payMapper.typeCount()[i]);
    }

    @Test
    public void artNum(){
        System.out.println(articleMapper.count());
    }

    @Test
    public void usertype(){
        //1:旅行社；0：游客
        System.out.println('男');
        for(int i=0;i<2;i++)
            System.out.println(i+":"+userMapper.menCount()[i]);

        //1:旅行社；0：游客
        System.out.println('女');
        for(int i=0;i<2;i++)
            System.out.println(i+":"+userMapper.womenCount()[i]);

        //1:旅行社；0：游客
        System.out.println("未知");
        for(int i=0;i<2;i++)
            System.out.println(i+":"+userMapper.noneCount()[i]);
    }
}
