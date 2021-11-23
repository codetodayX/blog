package cn.xqs.blog.dao;

import cn.xqs.blog.pojo.Comment;
import cn.xqs.blog.pojo.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageDao extends BaseMapper<Message> {
    public List<Message> getMessagesByParentId(Integer parentId);       //根据parentId获取子评论
}
