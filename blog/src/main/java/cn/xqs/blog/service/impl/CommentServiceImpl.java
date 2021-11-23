package cn.xqs.blog.service.impl;

import cn.xqs.blog.dao.CommentDao;
import cn.xqs.blog.pojo.Comment;
import cn.xqs.blog.service.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentDao commentDao;

    @Override
    public List<Comment> getCommentsByBlogId(Integer blogId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper();
        wrapper.select("id", "nickName", "email", "headPicture", "parentId", "blog_id", "info", "createTime");
        wrapper.eq("blog_id", blogId);
        return commentDao.selectList(wrapper);
    }

    @Override
    public void saveComment(Comment comment) {
        commentDao.insert(comment);
    }

    //查询所有根评论
    @Override
    public List<Comment> getRootCommentByBlogId(Integer blogId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.isNull("parentId");
        wrapper.eq("blog_id", blogId);
        wrapper.orderByDesc("createTime");
        return commentDao.selectList(wrapper);
    }


    //获取根评论的所有子评论
    @Override
    public List<Comment> getCommentList(Integer blogId) {
        //1.获取根评论
        List<Comment> rootComments = getRootCommentByBlogId(blogId);
        for (Comment rootComment : rootComments) {
            //2.1根据根评论Id获取第一代评论
            List<Comment> firstComments = commentDao.getCommentsByParentId(rootComment.getId());
            //2.2将第一代评论存放
            List<Comment> comments = new ArrayList<>();
            rootComment.setReplyComments(comments);
            rootComment.getReplyComments().addAll(firstComments);
            //2.3将第一代评论递归分解，获取所有评论
            if (firstComments.size() != 0) {
                //定义一个临时的List，用来存放当前第一代评论的所有子评论
                //必须使用临时List，不能一边遍历一边往firstComments添加元素，否则会导致ConcurrentModificationException
                List<Comment> tempList = new ArrayList<>();
                for (Comment firstComment : firstComments) {
                    //在递归前，还需设置第一代的父评论nickName
                    Comment comment = new Comment();
                    comment.setNickName(rootComment.getNickName());
                    firstComment.setParentComment(comment);
                    //调用递归方法，获取第一代评论的所有子评论
                    getAllCommentsByParentId(tempList, firstComment);
                }
                rootComment.getReplyComments().addAll(tempList);
                rootComment.getReplyComments().sort(new Comparator<Comment>() {
                    @Override
                    public int compare(Comment o1, Comment o2) {
                        return -o1.getCreateTime().compareTo(o2.getCreateTime());
                    }
                });
            }
        }
        //3.循环结束，返回Map
        return rootComments;
    }


    //该方法是实现评论列表展示的核心方法
    public void getAllCommentsByParentId(List<Comment> comments, Comment parentComment) {
        List<Comment> nextComments = commentDao.getCommentsByParentId(parentComment.getId());    //根据当前parentId,获取其第一代评论
        if (nextComments.size() == 0) {
            return;           //如果没有子评论，结束递归
        }
        //将查询出来的所有子评论，将其父评论的nickName赋值，跳转至博客详情页面要用,另外,还需将他们存入comments中
        for (Comment nextComment : nextComments) {
            Comment tempComment = new Comment();
            tempComment.setNickName(parentComment.getNickName());
            nextComment.setParentComment(tempComment);
        }
        //否则将第一代评论存入全局Map中(根据传入的comments)
        comments.addAll(nextComments);
        //第一代评论也有可能有子评论,还需递归调用
        for (Comment nextComment : nextComments) {
            getAllCommentsByParentId(comments, nextComment);
        }
    }
}
