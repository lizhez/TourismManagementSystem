package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Pay;
import com.example.demo.entity.Travel;
import com.example.demo.entity.VO.Talking;
import com.example.demo.service.IPayService;
import com.example.demo.service.ITalkService;
import com.example.demo.service.ITravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.util.resources.LocaleData;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/pays")
public class PayController {

    @Autowired
    private IPayService iPayService;

    @Autowired
    private ITravelService iTravelService;

    @GetMapping("/count")
    public Result<?> getCount(){
        return Result.success(iPayService.typeCount());
    }

    @PostMapping
    public Result<?> save(@RequestBody Pay pay){
        return new Result<>(iPayService.createPay(pay));
    }

    @PostMapping("/add")
    public Result<?> newPay(@RequestBody Pay pay){
        int RandomNum = (int)( 100000 + Math.random() * 100000);
        while(iPayService.findPayid(RandomNum)!=null){
            RandomNum = (int)( 100000 + Math.random() * 100000);
        }
        pay.setPayid(RandomNum);
        Travel travel = iTravelService.getById(pay.getTid());
        travel.setLeaves(travel.getLeaves()- pay.getNum());
        travel.setFinishes(travel.getFinishes()+ pay.getNum());
        iTravelService.updateById(travel);
        return new Result<>(iPayService.createPay(pay));
    }

    @PutMapping
    public Result<?> upData(@RequestBody Pay pay){
        return Result.success(iPayService.updateById(pay));
    }

    @PostMapping("{id}")
    public Result<?> upData(@PathVariable int id){
        return Result.success(iPayService.finish(id,LocalDateTime.now()));
    }

    @PostMapping("/pay/{id}")
    public Result<?> payRMB(@PathVariable int id){
        return Result.success(iPayService.payRMB(id,LocalDateTime.now()));
    }

    @PostMapping("/rom")
    public Result<?> payRom(@RequestBody Pay pay){
        System.out.println("pay"+pay);
        int tid= pay.getTid();
        Travel travel = iTravelService.getById(tid);
        travel.setLeaves(travel.getLeaves()- pay.getNum());
        travel.setFinishes(travel.getFinishes()+ pay.getNum());
        iTravelService.updateById(travel);
        return Result.success(iPayService.payRom(pay.getId()));
    }
    @PostMapping("/del")
    public Result<?> payDel(@RequestBody Pay pay){
        int tid= pay.getTid();
        Travel travel = iTravelService.getById(tid);
        travel.setLeaves(travel.getLeaves()+ pay.getNum());
        travel.setFinishes(travel.getFinishes()- pay.getNum());
        iTravelService.updateById(travel);
        return Result.success(iPayService.payDel(pay.getId()));
    }

    @DeleteMapping("{id}")
    public Result<?> del(@PathVariable int id){
        return new Result<>(iPayService.del(id));
    }

    @GetMapping("{id}")
    public Result<?> getById(@PathVariable int id){
        return Result.success(iPayService.findById(id));
    }

    @GetMapping
    public Result<?> findPageVo
            (@RequestParam(defaultValue = "1") Integer pageNum,
             @RequestParam(defaultValue = "3") Integer pageSize,
             @RequestParam(defaultValue = "") String search,
             @RequestParam(defaultValue = "") String req){
        Page<Pay> userPage = iPayService.findPageVo(new Page<>(pageNum,pageSize),search,req);
        return Result.success(userPage);
    }

    @GetMapping("/userPage")
    public Result<?> findUsersPay
            (@RequestParam(defaultValue = "1") Integer pageNum,
             @RequestParam(defaultValue = "3") Integer pageSize,
             @RequestParam(defaultValue = "") String search,
             @RequestParam(defaultValue = "") String req,
             @RequestParam(defaultValue = "") int id){
        Page<Pay> userPage = iPayService.findUsersPay(new Page<>(pageNum,pageSize),search,req,id);
        return Result.success(userPage);
    }

    @GetMapping("/travelPage")
    public Result<?> findTravelsPay
            (@RequestParam(defaultValue = "1") Integer pageNum,
             @RequestParam(defaultValue = "3") Integer pageSize,
             @RequestParam(defaultValue = "") String search,
             @RequestParam(defaultValue = "") String req,
             @RequestParam(defaultValue = "") int id){
        Page<Pay> userPage = iPayService.findTravelsPay(new Page<>(pageNum,pageSize),search,req,id);
        return Result.success(userPage);
    }

    @GetMapping("/finish")
    public Result<?> findPageVo
            (@RequestParam(defaultValue = "1") Integer pageNum,
             @RequestParam(defaultValue = "3") Integer pageSize,
             @RequestParam(defaultValue = "") String search){
        Page<Pay> userPage = iPayService.findPageFi(new Page<>(pageNum,pageSize),search);
        return Result.success(userPage);
    }

}
