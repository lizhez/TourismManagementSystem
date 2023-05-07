package com.example.demo;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Pay;
import com.example.demo.entity.User;
import com.example.demo.entity.VO.ArticleUser;
import com.example.demo.mapper.PayMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IArticleService;
import com.example.demo.service.IConmentsService;
import com.example.demo.service.IPayService;
import com.example.demo.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class TestService {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IPayService iPayService;

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private IArticleService iArticleService;

    @Autowired
    private IConmentsService iConmentsService;

    @Test
    void test(){
        System.out.println(payMapper.payRMB(23, LocalDateTime.now()));
    }

    @Test
    void pay(){
        Pay p = new Pay();
        p.setPayid(123456);
        p.setFid(1);
        p.setToid(5);
        p.setTid(1);
        iPayService.createPay(p);
    }

    @Test
    void payRom(){
        Pay p = new Pay();
        p.setFid(2);
        p.setToid(4);
        p.setTid(2);
        iPayService.createPay(p);
    }

    @Test
    void testTop(){
        System.out.println(iArticleService.getTop());
    }

    @Test
    void testGetByID(){
        System.out.println(iUserService.getById(2));
    }

    @Test
    void testlogin(){
        String s1 = "冲浪达人";
        String s2 = "987654321";
        String s3 = "123";
        System.out.println(iUserService.login(s1,s2));
        System.out.println("-------------------");
        System.out.println(iUserService.login(s1,s3));
    }

    @Test
    void testSave(){
        User user = new User();
        user.setName("aaa");
        iUserService.save(user);
    }

    @Test
    void testUpdata(){
        User user = new User();
        user.setId(5);
        user.setName("bbbbb");
        iUserService.updateById(user);
    }

    @Test
    void testDel(){
        iUserService.removeById(5);
    }

    @Test
    void testSelectAll(){
        System.out.println(iUserService.list());
    }

    @Test
    void testPage(){
        Page<User> page = iUserService.page(new Page<>(2,2),null);
        System.out.println(page.getCurrent());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.getRecords());
        System.out.println(page.getPages());
    }

    @Test
    void payPage(){
        System.out.println(iPayService.findPageVo(new Page<>(1,5),"","").getRecords());
    }

    @Test
    void comment(){
        System.out.println(iConmentsService.listCommentByBlogId(1));
    }
}
