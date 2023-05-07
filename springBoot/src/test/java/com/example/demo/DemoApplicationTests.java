package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserService iUserService;

    /*@Test
    void contextLoads() {
    }*/

    @Test
    void testGetByID(){
        System.out.println("ghjcj"+userMapper.hasName("冲浪"));
    }

    @Test
    void testSave(){
        User user = new User();
        user.setName("aaa");
        userMapper.insert(user);
    }

    @Test
    void testUpdata(){
        User user = new User();
        user.setId(3);
        user.setName("bbbbb");
        userMapper.updateById(user);
    }

    @Test
    void testDel(){
        userMapper.deleteById(4);
    }

    @Test
    void testSelectAll(){
        System.out.print(userMapper.selectList(null));;
    }

    @Test
    void testPage(){
        Page<User> page = userMapper.selectPage(new Page<>(1,2),null);
        System.out.println(page.getCurrent());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.getRecords());
        System.out.println(page.getPages());
    }

    @Test
    void testGetBy(){
        QueryWrapper<User> qw=new QueryWrapper<>();
        qw.like("name","b");
        System.out.println(userMapper.selectList(qw));;
    }

    @Test
    void testGetByL(){
        String name = "b";
        LambdaQueryWrapper<User> lqw=new LambdaQueryWrapper<>();
        lqw.like(name!=null,User::getName,name);
        System.out.println(userMapper.selectList(lqw));;
    }
}
