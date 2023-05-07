package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Img;
import com.example.demo.mapper.ImgMapper;
import com.example.demo.service.IImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ImgServiceImpl extends ServiceImpl<ImgMapper, Img> implements IImgService {
    @Autowired
    private ImgMapper imgMapper;

    @Override
    public List<Img> getUse() {
        return imgMapper.getUseImg();
    }

    @Override
    public boolean logDel(int id) {
        return imgMapper.logDel(id);
    }

    @Override
    public boolean chaImg(Img oldImg,Img newImg){
        int oldId = oldImg.getId();
        String oldUrl = oldImg.getUrl();
        int newId = newImg.getId();
        String newUrl = newImg.getUrl();
        imgMapper.chaURL(newUrl,oldId);
        return imgMapper.chaURL(oldUrl,newId);
    }

}