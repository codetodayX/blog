package cn.xqs.blog.dao;

import cn.xqs.blog.pojo.Tag;
import cn.xqs.blog.vo.TagTop;
import cn.xqs.blog.vo.TypeTop;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagDao extends BaseMapper<Tag> {
    public List<TagTop> getTopTags();

    public List<TagTop> getTags();            //获取所有标签及其对应的博客数目
}
