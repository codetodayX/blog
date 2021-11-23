package cn.xqs.blog.service;

import cn.xqs.blog.pojo.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    public List<Comment> getCommentsByBlogId(Integer blogId);       //根据博客ID，获取对应博客的所有评论

    public void saveComment(Comment comment);                       //保存用户评论

    public List<Comment> getRootCommentByBlogId(Integer blogId);   //查询指定博客页面的根评论

    public List<Comment> getCommentList(Integer blogId);               //获取博客列表
}
