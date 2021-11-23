package cn.xqs.blog.dao;

import cn.xqs.blog.pojo.Type;
import cn.xqs.blog.vo.TypeTop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeDao extends BaseMapper<Type> {
    public List<TypeTop> getTopType();

    public List<TypeTop> getTypes();
}
