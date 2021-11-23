package cn.xqs.blog.service;

import cn.xqs.blog.pojo.Comment;
import cn.xqs.blog.pojo.Message;

import java.util.List;

public interface MessageService {
    public List<Message> getRootMessage();      //获取所有根评论

    public List<Message> getAllMessage(List<Message> rootMessage);       //获取所有评论

    public void saveComment(Message comment);                       //保存用户评论

    public Integer getCount();                  //获取所有评论
}
