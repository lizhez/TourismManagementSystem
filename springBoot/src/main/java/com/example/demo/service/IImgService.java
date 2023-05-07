package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Img;
import java.util.List;
public interface IImgService extends IService<Img> {

    List<Img> getUse();

    boolean logDel(int id);

    boolean chaImg(Img oldImg,Img newImg);
}