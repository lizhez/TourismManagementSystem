package com.example.demo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Img;
import com.example.demo.entity.Travel;
import com.example.demo.entity.User;
import com.example.demo.service.IImgService;
import com.example.demo.service.ITravelService;
import com.example.demo.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestImg {

    @Autowired
    private IImgService iImgService;
    @Autowired
    private ITravelService t;


    @Test
    void testSelectAll(){
        System.out.println(iImgService.getUse());
    }

    @Test
    void testUpdata(){
        Img img = new Img();
        img.setId(1);
        img.setUrl("02.jpg");
        iImgService.updateById(img);
    }

    @Test
    void tt(){
        System.out.println(t.getById(5));
        //.logDel(5);
        System.out.println(t.getById(5));
    }

    @Test
    void chan(){
        String url="12.jpg";
        String nurl="01.jpg";
        Integer id = 1;
//        iImgService.chaImg(id,id);
        System.out.println(iImgService.list());
    }

}
