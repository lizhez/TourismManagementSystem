package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Pay;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

@Mapper
public interface PayMapper extends BaseMapper<Pay> {

    @Select("SELECT" +
            " pay.id as id,pay.payid, pay.status, pay.createtime, pay.paytime, pay.finishtime,pay.total,pay.num," +
            " f.id as fid, f.name as fname, f.avator as favator, f.phone as fphone, f.address as faddress," +
            " t.id as toid, t.name as toname, t.avator as toavator, t.phone as tophone, t.address as toaddress," +
            " travel.id as tid, travel.pay, travel.title, travel.first_img"+
            " FROM pay,user f,user t,travel" +
            " ${ew.customSqlSegment} ")
    Page<Pay> findPageVo(Page<Pay> page, @Param("ew") QueryWrapper wrapper);

    @Select("SELECT" +
            " pay.id as id,pay.payid, pay.status, pay.createtime, pay.paytime, pay.finishtime,pay.total,pay.num," +
            " f.id as fid, f.name as fname, f.avator as favator, f.phone as fphone, f.address as faddress," +
            " t.id as toid, t.name as toname, t.avator as toavator, t.phone as tophone, t.address as toaddress," +
            " travel.id as tid, travel.pay, travel.title, travel.first_img"+
            " FROM pay,user f,user t,travel" +
            " ${ew.customSqlSegment} ")
    Pay findByID( @Param("ew") QueryWrapper wrapper);

    @Update("update pay set status=-1,finishtime=#{finishtime} where id=#{id}")
    public boolean finish(@Param("id") int id, @Param("finishtime") LocalDateTime finishtime);

    @Update("update pay set status=1,paytime=#{paytime} where id=#{id}")
    public boolean payRMB(@Param("id") int id, @Param("paytime") LocalDateTime paytime);

    @Update("update pay set status=0 where id=#{id}")
    public boolean payRom(int id);
    @Update("update pay set status=2 where id=#{id}")
    public boolean payDel(int id);

    @Update("update pay set status=-2 where id=#{id}")
    public boolean del(int id);

    @Insert("INSERT INTO pay  ( payid, fid, toid, tid, total,num)  VALUES( #{payid},#{fid},#{toid},#{tid},#{total},#{num} )")
    public boolean createPay( @Param("payid") int payid, @Param("fid")int fid, @Param("toid")int toid,
                              @Param("tid")int tid, @Param("total")double total,@Param("num")int num);

    @Select("select * from pay where payid=#{payid}")
    Pay findpayid(int payid);

    @Select("select count(*) from pay group by status")
    int[] typeCount();

    @Select("select tid from pay where id=#{id}")
    int getTid(int id);
}
