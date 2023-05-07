package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Article;
import com.example.demo.entity.VO.ArticleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    @Select("select * from article a LEFT JOIN user u on u.id=a.uid where a.istop=1")
    List<ArticleUser> getTop();

    @Select("SELECT * " +
            " FROM article,user" +
            " ${ew.customSqlSegment} ")
    Page<ArticleUser> findPageVo(Page<ArticleUser> page, @Param("ew") QueryWrapper wrapper);

    @Select("select * from article a LEFT JOIN user u on u.id=a.uid where a.id=#{id}")
    ArticleUser findByID(int id);

    @Update("update article set active=-1,istop=0 where id=#{id}")
    public boolean logDel(int id);
    @Update("update article set active=1 where id=#{id}")
    public boolean logAdd(int id);
    @Update("update article set active=0,istop=0 where id=#{id}")
    public boolean delPub(int id);
    @Update("update article set active=-2 where id=#{id}")
    public boolean unpublic(int id);

    @Update("update article set istop=1 where id=#{id}")
    public boolean toTop(int id);
    @Update("update article set istop=0 where id=#{id}")
    public boolean delTop(int id);

    @Select("select title,view from article where istop=1")
    List<Article> count();
}
