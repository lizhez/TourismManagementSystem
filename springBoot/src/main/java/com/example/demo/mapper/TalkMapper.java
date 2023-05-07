package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.VO.Talking;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TalkMapper extends BaseMapper<Talking> {

    @Insert("INSERT INTO talking  ( fid, toid, content)  VALUES( #{fid},#{toid},#{content})")
    public boolean createPay(@Param("fid")int fid, @Param("toid")int toid,@Param("content") String content);

    @Select("SELECT * FROM" +
            " ((SELECT" +
            " talking.id as id,talking.content,talking.time," +
            " f.id as fid, f.avator as favator, " +
            " t.id as toid, t.avator as toavator" +
            " FROM talking,user f,user t" +
            " where talking.fid=f.id and talking.toid=t.id and talking.fid=#{toid} and talking.toid=#{fid})" +
            " UNION" +
            " (SELECT" +
            " talking.id as id,talking.content,talking.time," +
            " f.id as fid, f.avator as favator, " +
            " t.id as toid, t.avator as toavator" +
            " FROM talking,user f,user t" +
            " where talking.fid=f.id and talking.toid=t.id and talking.fid=#{fid} and talking.toid=#{toid})) as temp" +
            " order by temp.time asc")
    List<Talking> findTalk(@Param("fid")int fid,@Param("toid") int toid);
}
