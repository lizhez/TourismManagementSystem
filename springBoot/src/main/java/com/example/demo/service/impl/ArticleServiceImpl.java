package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Article;
import com.example.demo.entity.VO.ArticleUser;
import com.example.demo.mapper.ArticleMapper;
import com.example.demo.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<ArticleUser> getTop() {
        return articleMapper.getTop();
    }

    @Override
    public Page<ArticleUser> findUser(Page<ArticleUser> iPage, String search,String req,int id) {
        QueryWrapper<ArticleUser> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(search),"name",search);
        if(req.equals(""))
            wrapper.apply("article.uid = user.id and article.active=1 and article.uid="+id);
        else if(req.equals("0"))
            wrapper.apply("article.uid = user.id and article.active=0 and article.uid="+id);
        else if(req.equals("-2"))
            wrapper.apply("article.uid = user.id and article.active=-2 and article.uid="+id);
        return articleMapper.findPageVo(iPage,  wrapper);
    }

    @Override
    public Page<ArticleUser> findPageVo(Page<ArticleUser> iPage, String search) {
        QueryWrapper<ArticleUser> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(search),"title",search);
        wrapper.apply("article.uid = user.id and article.active>-1");
        return articleMapper.findPageVo(iPage,  wrapper);
    }

    @Override
    public boolean logDel(int id) {
        return articleMapper.logDel(id);
    }

    @Override
    public boolean delPub(int id) {
        return articleMapper.delPub(id);
    }

    @Override
    public boolean logAdd(int id) {return articleMapper.logAdd(id);}

    @Override
    public boolean unPub(int id) {
        return articleMapper.unpublic(id);
    }

    @Override
    public boolean toTop(int id) {
        return articleMapper.toTop(id);
    }

    @Override
    public boolean delTop(int id) {
        return articleMapper.delTop(id);
    }

    @Override
    public ArticleUser findById(int id){
        Article article = articleMapper.selectById(id);
        article.setView(article.getView()+1);
        articleMapper.updateById(article);
        return articleMapper.findByID(id);
    }

    @Override
    public List<Article> artCount() {
        return articleMapper.count();
    }
}