package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Travel;
import com.example.demo.entity.VO.ArticleUser;
import com.example.demo.entity.VO.TavelUser;
import com.example.demo.mapper.TravelMapper;
import com.example.demo.service.ITravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Wrapper;
import java.util.List;


@Service
public class TravelServiceImpl extends ServiceImpl<TravelMapper, Travel> implements ITravelService {

    @Autowired
    private TravelMapper travelMapper;

    @Override
    public List<TavelUser> getTop() {
        return travelMapper.getTop();
    }
    @Override
    public List<TavelUser> getAll() {
        return travelMapper.getAll();
    }

    @Override
    public Page<TavelUser> findPageVo(Page<TavelUser> iPage, String search,String req) {
        QueryWrapper<TavelUser> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(search),"title",search);
        if(req.equals(""))
            wrapper.apply("travel.uid = user.id and travel.active=1");
        else
            wrapper.apply("travel.uid = user.id and travel.active=0");
        return travelMapper.findPageVo(iPage,  wrapper);
    }

    @Override
    public Page<TavelUser> findUser(Page<TavelUser> iPage, String search,String req, int id) {
        QueryWrapper<TavelUser> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(search),"title",search);
        if(req.equals(""))
            wrapper.apply("travel.uid = user.id and travel.active=1 and travel.uid="+id);
        else if(req.equals("0"))
            wrapper.apply("travel.uid = user.id and travel.active=0 and travel.uid="+id);
        else
            wrapper.apply("travel.uid = user.id and travel.active=-1 and travel.uid="+id);
        return travelMapper.findPageVo(iPage,  wrapper);
    }

    @Override
    public boolean logDel(int id) {
        return travelMapper.logDel(id);
    }


    @Override
    public boolean toTop(int id) {
        return travelMapper.toTop(id);
    }

    @Override
    public boolean delTop(int id) {
        return travelMapper.delTop(id);
    }

    @Override
    public TavelUser findById(int id){
        Travel travel = travelMapper.selectById(id);
        travel.setView(travel.getView()+1);
        travelMapper.updateById(travel);
        return travelMapper.findByID(id);
    }
}