package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Travel;
import com.example.demo.entity.VO.ArticleUser;
import com.example.demo.entity.VO.TavelUser;
import com.example.demo.mapper.TravelMapper;
import com.example.demo.service.ITravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;

@RestController
@RequestMapping("/travels")
public class TravelController {

    @Autowired
    private ITravelService itravelService;
    @Autowired
    private TravelMapper travelMapper;

    @PostMapping
    public Result<?> save(@RequestBody Travel travel){
        return new Result<>(itravelService.save(travel));
    }

    @PutMapping
    public Result<?> upData(@RequestBody Travel travel){
        if(travel.getActive()!=1)
            travel.setActive(0);
        return Result.success(itravelService.updateById(travel));
    }

    @DeleteMapping("/reaL/{id}")
    public Result<?> RealDel(@PathVariable Long id){
        return new Result<>(itravelService.removeById(id));
    }

    @DeleteMapping("{id}")
    public Result<?> del(@PathVariable Long id){
        Travel travel = itravelService.getById(id);
        travel.setActive(-1);
        return new Result<>(itravelService.updateById(travel));
    }
    @PostMapping("{id}")
    public Result<?> logAdd(@PathVariable int id){
        Travel travel = itravelService.getById(id);
        travel.setActive(1);
        return new Result<>(itravelService.updateById(travel));
    }

    @PutMapping("{id}")
    public Result<?> logDel(@PathVariable int id){
        return new Result<>(itravelService.logDel(id));
    }

    @GetMapping("{id}")
    public Result<?> getById(@PathVariable Integer id){
        return Result.success(itravelService.findById(id));
    }

    @GetMapping("/top")
    public Result<?> getTop(){
        return Result.success(itravelService.getTop());
    }
    @GetMapping("/all")
    public Result<?> getAll(){
        return Result.success(itravelService.getAll());
    }


    @DeleteMapping("/top/{id}")
    public Result<?> delTop(@PathVariable int id){
        return new Result<>((itravelService.delTop(id)));
    }
    @PutMapping("/top/{id}")
    public Result<?> toTop(@PathVariable int id){
        return new Result<>(itravelService.toTop(id));
    }

    @GetMapping
    public Result<?> getReqPage
            (@RequestParam(defaultValue = "1") Integer pageNum,
             @RequestParam(defaultValue = "3") Integer pageSize,
             @RequestParam(defaultValue = "") String search,
             @RequestParam(defaultValue = "") String req){
        Page<TavelUser> userPage = itravelService.findPageVo(new Page<>(pageNum,pageSize), search,req);
        return Result.success(userPage);
    }

    @GetMapping("/user")
    public Result<?> getUser
            (@RequestParam(defaultValue = "1") Integer pageNum,
             @RequestParam(defaultValue = "3") Integer pageSize,
             @RequestParam(defaultValue = "") String search,
             @RequestParam(defaultValue = "") String req,
             @RequestParam(defaultValue = "") int id){
        Page<TavelUser> userPage = itravelService.findUser(new Page<>(pageNum,pageSize), search,req,id);
        return Result.success(userPage);
    }
}
