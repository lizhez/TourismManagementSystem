package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Img;
import com.example.demo.entity.User;
import com.example.demo.service.IImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/imgs")
public class ImgController {

    @Autowired
    private IImgService iimgService;

    @GetMapping("/{str}")
    public Result<?> getAll(@PathVariable String str){
        String s=str;
        return Result.success(iimgService.list());
    }

    @GetMapping
    public Result<?> getUse(){
        return Result.success(iimgService.getUse());
    }

    @PostMapping
    public Result<?> save(@RequestBody Img img){
        return new Result<>(iimgService.save(img));
    }

    @PutMapping
    public Result<?> upData(@RequestBody Img img){
        return Result.success(iimgService.updateById(img));
    }

    @PutMapping("/{oldId}/{newId}")
    public Result<?> chaImg(@PathVariable int oldId,@PathVariable int newId){
        return Result.success(
                iimgService.chaImg(
                        iimgService.getById(oldId),
                        iimgService.getById(newId)
                ));
    }

    @DeleteMapping("{id}")
    public Result<?> del(@PathVariable Long id){
        return new Result<>(iimgService.removeById(id));
    }

    @PutMapping("{id}")
    public Result<?> logDel(@PathVariable int id){
        return new Result<>(iimgService.logDel(id));
    }

}
