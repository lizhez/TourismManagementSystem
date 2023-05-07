package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.VO.Conments;
import com.example.demo.mapper.ConmentsMapper;
import com.example.demo.service.IConmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ConmentsServiceImpl extends ServiceImpl<ConmentsMapper, Conments> implements IConmentsService {

    @Autowired
    private ConmentsMapper conmentsMapper;

    //存放迭代找出的所有子代的集合
    private List<Conments> tempReplys = new ArrayList<>();

    @Override
    public List<Conments> listCommentByBlogId(int blogId) {
        //查询出父节点
        List<Conments> comments = conmentsMapper.commentsList(blogId, -1);
        for(Conments comment : comments){
            int id = comment.getId();
            String parentNickname1 = comment.getName();
            List<Conments> childComments = conmentsMapper.commentsList(blogId,id);
            //查询出子评论
            combineChildren(blogId, childComments, parentNickname1);
            comment.setReplyConments(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return comments;
    }

    private void combineChildren(int blogId, List<Conments> childComments, String parentNickname1) {
        //判断是否有一级子评论
        if(childComments.size() > 0){
            //循环找出子评论的id
            for(Conments childComment : childComments){
                String parentNickname = childComment.getName();
                childComment.setParentname(parentNickname1);
                tempReplys.add(childComment);
                int childId = childComment.getId();
                //查询出子二级评论
                recursively(blogId, childId, parentNickname);
            }
        }
    }

    private void recursively(int blogId, int childId, String parentNickname1) {
        //根据子一级评论的id找到子二级评论
        List<Conments> replayComments = conmentsMapper.commentsList(blogId,childId);

        if(replayComments.size() > 0){
            for(Conments replayComment : replayComments){
                String parentNickname = replayComment.getName();
                replayComment.setParentname(parentNickname1);
                int replayId = replayComment.getId();
                tempReplys.add(replayComment);
                recursively(blogId,replayId,parentNickname);
            }
        }
    }

    @Override
    public Page<Conments> findPageVo(Page<Conments> iPage, String search) {
        QueryWrapper<Conments> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(search),"name",search);
        wrapper.apply("conment.uid = user.id");
        return conmentsMapper.findPageVo(iPage,  wrapper);
    }

    @Override
    public Page<Conments> findUser(Page<Conments> iPage, String search,int id) {
        QueryWrapper<Conments> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(search),"context",search);
        wrapper.apply("conment.uid = user.id and conment.uid="+id);
        return conmentsMapper.findPageVo(iPage,  wrapper);
    }

    @Override
    public boolean del(int id) {return conmentsMapper.del(id);}

    @Override
    public boolean rem(int id) {
        return conmentsMapper.rem(id);
    }


}