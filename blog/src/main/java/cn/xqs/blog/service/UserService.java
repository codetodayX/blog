package cn.xqs.blog.service;

import cn.xqs.blog.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService {
    public User getUserByUsernameAndPassword(String username, String password);

    public User getUserById(Long id);          //根据用户ID查询用户信息
}
