package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.VO.Conments;

import java.util.List;

public interface IConmentsService extends IService<Conments> {

//    Page<ArticleUser> findPageVo(Page<ArticleUser> iPage, String search,String req);
    Page<Conments> findPageVo(Page<Conments> iPage, String search);
    Page<Conments> findUser(Page<Conments> iPage, String search,int id);

    boolean del(int id);
    boolean rem(int id);

    List<Conments> listCommentByBlogId(int blogId);

    }