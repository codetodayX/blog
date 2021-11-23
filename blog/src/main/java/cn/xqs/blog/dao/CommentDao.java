package cn.xqs.blog.dao;

import cn.xqs.blog.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao extends BaseMapper<Comment> {
    public List<Comment> getCommentsByParentId(Integer parentId);       //根据parentId获取子评论
}
