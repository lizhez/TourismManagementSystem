package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Travel;
import com.example.demo.entity.VO.TavelUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Wrapper;
import java.util.List;

@Mapper
public interface TravelMapper extends BaseMapper<Travel> {
    @Select("select * from travel t LEFT JOIN user u on u.id=t.uid where t.istop=1")
    List<TavelUser> getTop();
    @Select("select * from travel t LEFT JOIN user u on u.id=t.uid")
    List<TavelUser> getAll();

    @Select("SELECT * " +
            " FROM travel,user" +
            " ${ew.customSqlSegment} ")
    Page<TavelUser> findPageVo(Page<TavelUser> page, @Param("ew") QueryWrapper wrapper);

    @Select("select * from travel t LEFT JOIN user u on u.id=t.uid where t.id=#{id}")
    TavelUser findByID(int id);

    @Update("update travel set active=0, istop=0 where id=#{id}")
    public boolean logDel(int id);

    @Update("update travel set istop=1 where id=#{id}")
    public boolean toTop(int id);
    @Update("update travel set istop=0 where id=#{id}")
    public boolean delTop(int id);


}
