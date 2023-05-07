package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;

public interface IUserService extends IService<User> {

    boolean changPass(String pass,int id);
    User hasName(String name);
    User login(String name,String password);

    int[] menCount();
    int[] womenCount();
    int[] noneCount();
}