package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Article;
import com.example.demo.entity.VO.ArticleUser;

import java.util.List;

public interface IArticleService extends IService<Article> {
    List<ArticleUser> getTop();

    Page<ArticleUser> findUser(Page<ArticleUser> iPage, String search,String req,int id);

    Page<ArticleUser> findPageVo(Page<ArticleUser> iPage, String search);


    boolean logDel(int id);
    boolean delPub(int id);
    boolean logAdd(int id);
    boolean unPub(int id);

    boolean toTop(int id);
    boolean delTop(int id);

    ArticleUser findById(int id);

    List<Article> artCount();

}