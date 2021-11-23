package cn.xqs.blog.service.impl;

import cn.xqs.blog.dao.MessageDao;
import cn.xqs.blog.pojo.Comment;
import cn.xqs.blog.pojo.Message;
import cn.xqs.blog.service.MessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public List<Message> getRootMessage() {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.isNull("parentId");
        wrapper.orderByDesc("createTime");
        return messageDao.selectList(wrapper);
    }

    @Override
    public List<Message> getAllMessage(List<Message> rootComments) {
        for (Message rootComment : rootComments) {
            //2.1根据根评论Id获取第一代评论
            List<Message> firstComments = messageDao.getMessagesByParentId(rootComment.getId());
            //2.2将第一代评论存放
            List<Message> comments = new ArrayList<>();
            rootComment.setReplyMessages(comments);
            rootComment.getReplyMessages().addAll(firstComments);
            //2.3将第一代评论递归分解，获取所有评论
            if (firstComments.size() != 0) {
                //定义一个临时的List，用来存放当前第一代评论的所有子评论
                //必须使用临时List，不能一边遍历一边往firstComments添加元素，否则会导致ConcurrentModificationException
                List<Message> tempList = new ArrayList<>();
                for (Message firstComment : firstComments) {
                    //在递归前，还需设置第一代的父评论nickName
                    Message comment = new Message();
                    comment.setNickName(rootComment.getNickName());
                    firstComment.setParentMessage(comment);
                    //调用递归方法，获取第一代评论的所有子评论
                    getAllCommentsByParentId(tempList, firstComment);
                }
                rootComment.getReplyMessages().addAll(tempList);
                rootComment.getReplyMessages().sort(new Comparator<Message>() {
                    @Override
                    public int compare(Message o1, Message o2) {
                        return -o1.getCreateTime().compareTo(o2.getCreateTime());
                    }
                });
            }
        }
        //3.循环结束，返回List
        return rootComments;
    }

    @Override
    public void saveComment(Message comment) {
        messageDao.insert(comment);
    }

    @Override
    public Integer getCount() {
        return messageDao.selectCount(null);
    }


    //该方法是实现评论列表展示的核心方法
    public void getAllCommentsByParentId(List<Message> messages, Message parentMessage) {
        List<Message> nextMessages = messageDao.getMessagesByParentId(parentMessage.getId());    //根据当前parentId,获取其第一代评论
        if (nextMessages.size() == 0) {
            return;           //如果没有子评论，结束递归
        }
        //将查询出来的所有子评论，将其父评论的nickName赋值，跳转至博客详情页面要用,另外,还需将他们存入comments中
        for (Message nextMessage : nextMessages) {
            Message tempMessage = new Message();
            tempMessage.setNickName(parentMessage.getNickName());
            nextMessage.setParentMessage(tempMessage);
        }

        messages.addAll(nextMessages);
        //第一代评论也有可能有子评论,还需递归调用
        for (Message nextMessage : nextMessages) {
            getAllCommentsByParentId(messages, nextMessage);
        }
    }
}
