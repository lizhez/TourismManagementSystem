package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.VO.Talking;
import com.example.demo.mapper.TalkMapper;
import com.example.demo.service.ITalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talking> implements ITalkService {

    @Autowired
    private TalkMapper talkMapper;
    @Override
    public List<Talking> findTalk(int fid, int toid){
        return talkMapper.findTalk(fid,toid);
    }

    @Override
    public boolean createTalk(Talking talking){
        return talkMapper.createPay(talking.getFid(), talking.getToid(), talking.getContent());
    }

}
