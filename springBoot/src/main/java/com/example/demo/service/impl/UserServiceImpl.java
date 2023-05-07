package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean changPass(String pass,int id){
        return userMapper.changePass(pass,id);
    }

    @Override
    public User hasName(String name){
        return userMapper.hasName(name);
    }

    @Override
    public User login(String name, String password){
        return userMapper.login(name,password);
    }

    @Override
    public int[] menCount(){
        return userMapper.menCount();
    }
    @Override
    public int[] womenCount(){
        return userMapper.womenCount();
    }
    @Override
    public int[] noneCount(){
        return userMapper.noneCount();
    }
}