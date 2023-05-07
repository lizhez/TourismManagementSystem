package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.VO.ArticleUser;
import com.example.demo.entity.VO.Conments;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ConmentsMapper extends BaseMapper<Conments> {

    @Select("select * from " +
            "(select " +
            "conment.id, conment.uid, user.name, user.avator," +
            "conment.pid, conment.belongid, conment.context, conment.active, conment.time" +
            " from conment,user where conment.uid = user.id) as c "+
            "where c.belongid=#{bid} and c.pid=#{pid} order by c.time desc")
    List<Conments> commentsList(@Param("bid") int bid, @Param("pid") int pid);


    @Update("update conment set active=0 where id=#{id}")
    boolean del(int id);
    @Update("update conment set active=1 where id=#{id}")
    boolean rem(int id);

    @Select("SELECT * " +
            " FROM conment,user" +
            " ${ew.customSqlSegment} ")
    Page<Conments> findPageVo(Page<Conments> page, @Param("ew") QueryWrapper wrapper);

}
