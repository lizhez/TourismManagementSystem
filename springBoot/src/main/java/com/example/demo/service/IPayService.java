package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Pay;

import java.time.LocalDateTime;

public interface IPayService extends IService<Pay> {
    Page<Pay> findPageVo(Page<Pay> iPage, String search,String req);
    Page<Pay> findUsersPay(Page<Pay> iPage,String search,String req, int id);
    Page<Pay> findTravelsPay(Page<Pay> iPage,String search,String req, int id);
    Page<Pay> findPageFi(Page<Pay> iPage, String search);
    Pay findById(int id);

    boolean finish(int id,LocalDateTime time);
    boolean payRMB(int id,LocalDateTime time);
    boolean payRom(int id);
    boolean payDel(int id);
    boolean del(int id);

    boolean createPay(Pay pay);

    Pay findPayid(int payid);

    int[] typeCount();

    int getTid(int id);

}