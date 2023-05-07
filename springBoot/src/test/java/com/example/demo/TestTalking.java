package com.example.demo;

import com.example.demo.entity.VO.Talking;
import com.example.demo.service.ITalkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestTalking {

    @Autowired
    private ITalkService iTalkService;

    @Test
    public void add(){
        Talking t = new Talking();
        t.setFid(1);
        t.setToid(3);
        t.setContent("好的");
        iTalkService.createTalk(t);
    }

    @Test
    public void select(){
        System.out.println(iTalkService.findTalk(1,3));
    }

}
