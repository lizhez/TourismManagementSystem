package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.VO.Talking;
import com.example.demo.service.ITalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/talks")
public class TalkController {

    @Autowired
    private ITalkService iTalkService;

    @PostMapping
    public Result<?> save(@RequestBody Talking pay){
        return new Result<>(iTalkService.createTalk(pay));
    }

    @GetMapping("/{fid}/{toid}")
    public Result<?> getTalk(@PathVariable int fid,@PathVariable int toid){
        return new Result<>(iTalkService.findTalk(fid,toid));
    }


}
