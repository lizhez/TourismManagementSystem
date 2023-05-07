package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Article;
import com.example.demo.entity.VO.ArticleUser;
import com.example.demo.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private IArticleService iArticleService;

    @GetMapping("/count")
    public Result<?> getCount(){
        return Result.success(iArticleService.artCount());
    }

    @PostMapping
    public Result<?> save(@RequestBody Article article){
        return new Result<>(iArticleService.save(article));
    }

    @PutMapping
    public Result<?> upData(@RequestBody Article article){
        return Result.success(iArticleService.updateById(article));
    }

    @DeleteMapping("/real/{id}")
    public Result<?> delFull(@PathVariable Long id){
        return new Result<>(iArticleService.removeById(id));
    }

    @GetMapping("{id}")
    public Result<?> getById(@PathVariable Integer id){
        return Result.success(iArticleService.findById(id));
    }
    @GetMapping("/top")
    public Result<?> getTop(){
        return Result.success(iArticleService.getTop());
    }

    @DeleteMapping("{id}")
    public Result<?> del(@PathVariable int id){return new Result<>(iArticleService.unPub(id));}
    @DeleteMapping("/delPub/{id}")
    public Result<?> delPub(@PathVariable int id){
        return new Result<>(iArticleService.delPub(id));
    }
    @PutMapping("{id}")
    public Result<?> logDel(@PathVariable int id){return new Result<>(iArticleService.logDel(id));}
    @PostMapping("{id}")
    public Result<?> logAdd(@PathVariable int id){return new Result<>(iArticleService.logAdd(id));}

    @DeleteMapping("/top/{id}")
    public Result<?> delTop(@PathVariable int id){
        return new Result<>((iArticleService.delTop(id)));
    }
    @PutMapping("/top/{id}")
    public Result<?> toTop(@PathVariable int id){
        return new Result<>(iArticleService.toTop(id));
    }

    @GetMapping("/user")
    public Result<?> getUser
            (@RequestParam(defaultValue = "1") Integer pageNum,
             @RequestParam(defaultValue = "3") Integer pageSize,
             @RequestParam(defaultValue = "") String search,
             @RequestParam(defaultValue = "") String req,
             @RequestParam(defaultValue = "") int id){
        Page<ArticleUser> userPage = iArticleService.findUser(new Page<>(pageNum,pageSize), search,req,id);
        return Result.success(userPage);
    }

    @GetMapping
    public Result<?> getReqPage
            (@RequestParam(defaultValue = "1") Integer pageNum,
             @RequestParam(defaultValue = "3") Integer pageSize,
             @RequestParam(defaultValue = "") String search){
        Page<ArticleUser> userPage = iArticleService.findPageVo(new Page<>(pageNum,pageSize),search);
        return Result.success(userPage);
    }

}
