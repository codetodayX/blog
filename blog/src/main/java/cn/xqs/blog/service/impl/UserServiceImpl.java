package cn.xqs.blog.service.impl;

import cn.xqs.blog.dao.UserDao;
import cn.xqs.blog.pojo.User;
import cn.xqs.blog.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        User user = userDao.selectOne(queryWrapper);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userDao.selectById(id);
    }
}
