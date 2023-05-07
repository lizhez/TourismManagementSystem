package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService iuserService;

    /*@GetMapping
    public Result<?> getAll(){
        return Result.success(iuserService.list());
    }*/
    @GetMapping("/count/men")
    public Result<?> getMen(){
        return Result.success(iuserService.menCount());
    }
    @GetMapping("/count/women")
    public Result<?> getWomen(){
        return Result.success(iuserService.womenCount());
    }
    @GetMapping("/count/none")
    public Result<?> getNone(){
        return Result.success(iuserService.noneCount());
    }

    @PostMapping
    public Result<?> save(@RequestBody User user){
        User u = iuserService.hasName(user.getName());
        if(u!=null)
            return Result.error();
        else
            return Result.success(iuserService.save(user));
    }

    @PutMapping
    public Result<?> upData(@RequestBody User user){
        return Result.success(iuserService.updateById(user));
    }

    @PutMapping("/{id}/{pass}")
    public Result<?> changPass(@PathVariable String pass,@PathVariable int id){
        return Result.success(iuserService.changPass(pass,id));
    }

    /*@DeleteMapping("{id}")
    public Result<?> del(@PathVariable Long id){
        return new Result<>(iuserService.removeById(id));
    }*/
    @DeleteMapping("{id}")
    public Result<?> del(@PathVariable Long id){
        User user = iuserService.getById(id);
        user.setStatus(-1);
        user.setName("用户已注销");
        user.setAddress("无");
        user.setPhone("无");
        user.setAvator("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        return new Result<>(iuserService.updateById(user));
    }

    @GetMapping("{id}")
    public Result<?> getById(@PathVariable Integer id){
        return Result.success(iuserService.getById(id));
    }

    @GetMapping("{name}/{password}")
    public Result<?> login(@PathVariable String name, @PathVariable String password){
        User u = iuserService.login(name,password);
        if(u==null)
            return Result.error();
        else
            return Result.success(u);
    }

    @GetMapping
    public Result<?> getPage
            (@RequestParam(defaultValue = "1") Integer pageNum,
             @RequestParam(defaultValue = "10") Integer pageSize,
             @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(User::getName,search);
        }
        wrapper.apply("status>-1");
        Page<User> userPage = iuserService.page(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(userPage);
    }
}
