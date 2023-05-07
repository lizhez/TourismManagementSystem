package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Article;
import com.example.demo.entity.VO.ArticleUser;
import com.example.demo.entity.VO.Conments;
import com.example.demo.service.IArticleService;
import com.example.demo.service.IConmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class ConmentsController {

    @Autowired
    private IConmentsService iConmentsService;

    //查询评论列表
    @GetMapping("{id}")
    public Result<?> comments(@PathVariable int id) {
        return Result.success(iConmentsService.listCommentByBlogId(id));
    }


    @PostMapping("/add")
    public Result<?> save(@RequestBody Conments conments){
        iConmentsService.save(conments);
        return Result.success(iConmentsService.listCommentByBlogId(conments.getBelongid()));
    }

    @PutMapping
    public Result<?> upData(@RequestBody Conments conments){
        return Result.success(iConmentsService.updateById(conments));
    }

    @DeleteMapping("/del/{id}")
    public Result<?> realDel(@PathVariable Long id){
        return new Result<>(iConmentsService.removeById(id));
    }

    @DeleteMapping("{id}")
    public Result<?> del(@PathVariable int id){
        return new Result<>(iConmentsService.del(id));
    }
    @PostMapping("{id}")
    public Result<?> rem(@PathVariable int id){
        return new Result<>(iConmentsService.rem(id));
    }

    @GetMapping
    public Result<?> getReqPage
            (@RequestParam(defaultValue = "1") Integer pageNum,
             @RequestParam(defaultValue = "3") Integer pageSize,
             @RequestParam(defaultValue = "") String search){
        Page<Conments> userPage = iConmentsService.findPageVo(new Page<>(pageNum,pageSize),search);
        return Result.success(userPage);
    }

    @GetMapping("/user")
    public Result<?> getReqPage
            (@RequestParam(defaultValue = "1") Integer pageNum,
             @RequestParam(defaultValue = "3") Integer pageSize,
             @RequestParam(defaultValue = "") String search,
             @RequestParam(defaultValue = "") int id){
        Page<Conments> userPage = iConmentsService.findUser(new Page<>(pageNum,pageSize),search,id);
        return Result.success(userPage);
    }

}
