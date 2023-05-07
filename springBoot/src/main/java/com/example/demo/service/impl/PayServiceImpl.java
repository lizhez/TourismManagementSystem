package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Pay;
import com.example.demo.entity.VO.ArticleUser;
import com.example.demo.mapper.PayMapper;
import com.example.demo.service.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PayServiceImpl extends ServiceImpl<PayMapper, Pay> implements IPayService {

    @Autowired
    private PayMapper payMapper;

    @Override
    public Page<Pay> findPageVo(Page<Pay> iPage, String search,String req) {
        QueryWrapper<Pay> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(search),"pay.payid",search);
        wrapper.like(StrUtil.isNotBlank(req),"pay.status",req);
        wrapper.apply("pay.fid=f.id and pay.toid=t.id and pay.tid=travel.id and pay.status>-1");
        return payMapper.findPageVo(iPage,  wrapper);
    }

    @Override
    public Page<Pay> findPageFi(Page<Pay> iPage, String search) {
        QueryWrapper<Pay> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(search),"pay.payid",search);
        wrapper.apply("pay.fid=f.id and pay.toid=t.id and pay.tid=travel.id and pay.status=-1");
        return payMapper.findPageVo(iPage,  wrapper);
    }

    @Override
    public Pay findById(int id) {
        QueryWrapper<Pay> wrapper = new QueryWrapper<>();
        wrapper.apply("pay.fid=f.id and pay.toid=t.id and pay.tid=travel.id and pay.id="+id);
        return payMapper.findByID(wrapper);
    }

    @Override
    public Page<Pay> findUsersPay(Page<Pay> iPage, String search,String req,int id) {
        QueryWrapper<Pay> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(search),"pay.payid",search);
        wrapper.eq(StrUtil.isNotBlank(req),"pay.status",req);
        wrapper.apply("pay.fid=f.id and pay.toid=t.id and pay.tid=travel.id and pay.status>-1 and pay.toid="+id);
        return payMapper.findPageVo(iPage,  wrapper);
    }

    @Override
    public Page<Pay> findTravelsPay(Page<Pay> iPage, String search,String req,int id) {
        QueryWrapper<Pay> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(search),"pay.payid",search);
        wrapper.eq(StrUtil.isNotBlank(req),"pay.status",req);
        wrapper.apply("pay.fid=f.id and pay.toid=t.id and pay.tid=travel.id and pay.status<2 and pay.status>-2 and pay.fid="+id);
        return payMapper.findPageVo(iPage,  wrapper);
    }

    @Override
    public Pay findPayid(int payid){
        return payMapper.findpayid(payid);
    }

    @Override
    public boolean finish(int id,LocalDateTime time){
        return payMapper.finish(id,time);
    }

    @Override
    public boolean payRMB(int id,LocalDateTime time){
        System.out.println("time1:"+time);
        return payMapper.payRMB(id,time);
    }

    @Override
    public boolean payRom(int id){
        return payMapper.payRom(id);
    }

    @Override
    public boolean payDel(int id){
        return payMapper.payDel(id);
    }

    public boolean del(int id){
        return payMapper.del(id);
    }

    @Override
    public boolean createPay(Pay pay){
        System.out.println(pay);
        int i= pay.getPayid();
        return payMapper.createPay(i, pay.getFid(), pay.getToid(), pay.getTid(),pay.getTotal(),pay.getNum());
    }

    @Override
    public int[] typeCount(){
        return payMapper.typeCount();
    }

    @Override
    public int getTid(int id){
        return payMapper.getTid(id);
    }
}
