package cn.xqs.blog.dao;

import cn.xqs.blog.pojo.User;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
