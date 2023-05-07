package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Travel;
import com.example.demo.entity.VO.ArticleUser;
import com.example.demo.entity.VO.TavelUser;
import com.example.demo.mapper.TravelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Wrapper;
import java.util.List;

public interface ITravelService extends IService<Travel> {
    List<TavelUser> getTop();
    List<TavelUser> getAll();
    Page<TavelUser> findPageVo(Page<TavelUser> iPage, String search,String req);
    Page<TavelUser> findUser(Page<TavelUser> iPage, String search,String req, int id);


    boolean logDel(int id);

    boolean toTop(int id);
    boolean delTop(int id);

    TavelUser findById(int id);
}