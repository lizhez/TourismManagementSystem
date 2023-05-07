package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Img;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ImgMapper extends BaseMapper<Img> {

    @Select("select * from img where status = 1")
    public List<Img> getUseImg();

    @Update("update img set status=0 where id=#{id}")
    public boolean logDel(int id);


    @Update("update img set url=#{url} where id=#{id}")
    public boolean chaURL(@Param("url")String url,@Param("id") int id);


}