package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.VO.Talking;

import java.util.List;

public interface ITalkService extends IService<Talking> {

    boolean createTalk(Talking talking);
    List<Talking> findTalk(int fid,int toid);
}