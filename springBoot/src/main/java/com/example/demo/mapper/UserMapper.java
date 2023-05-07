package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.entity.VO.TavelUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where name=#{name} and password=#{password} and status>-1")
    User login(@Param("name") String name, @Param("password") String password);

    @Select("select * from user where name=#{name}")
    User hasName(String name);

    @Update("update user set password=#{pass} where id=#{id}")
    public boolean changePass(@Param("pass")String pass, @Param("id") int id);

    @Select("select count(*) from user where sex='男' group by type")
    int[] menCount();
    @Select("select count(*) from user where sex='女' group by type")
    int[] womenCount();
    @Select("select count(*) from user where sex='未知' group by type")
    int[] noneCount();

}
