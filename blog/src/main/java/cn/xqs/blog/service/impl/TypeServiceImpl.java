package cn.xqs.blog.service.impl;

import cn.xqs.blog.dao.TypeDao;
import cn.xqs.blog.pojo.Blog;
import cn.xqs.blog.pojo.Type;
import cn.xqs.blog.service.BlogService;
import cn.xqs.blog.service.TypeService;
import cn.xqs.blog.vo.TypeTop;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeDao typeDao;
    @Autowired
    BlogService blogService;

    public boolean saveType(Type type) {
        //1.定义wrapper
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("name", type.getName());
        wrapper.select("name");
        Type type1 = typeDao.selectOne(wrapper);

        //对查询的结果进行判断
        if (type1 == null) {
            typeDao.insert(type);
            return true;
        } else {
            return false;
        }
    }


    public Page<Type> getList(int curPage) {
        //1.创建Page对象，指定当前页码
        Page<Type> page = new Page<>(curPage, 5);

        //使用wrapper，告诉mybatis-plus,blogs字段不要查
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.select("name", "id");
        //2.指定方法，拿到分页数据
        Page<Type> selectPage = typeDao.selectPage(page, wrapper);

        //3.返回
        return selectPage;

    }

    @Override
    public void remove(Integer id) {
        //0.要删除分类，必须先删除博客，但是要删除博客，必须先删除关系表中的数据和评论表中的数据
        //1.根据分类ID，获取对应的所有博客
        List<Blog> blogs = blogService.getAllBlogByTypeId(id);
        //2.根据博客ID，删除博客内容
        for (Blog blog : blogs) {
            blogService.removeBlog(blog.getId());
        }
        //3.根据分类ID，删除分类
        typeDao.deleteById(id);
    }


    @Override
    public void update(Type type) {
        UpdateWrapper<Type> wrapper = new UpdateWrapper<>();
        wrapper.set("name", type.getName());
        wrapper.eq("id", type.getId());
        typeDao.update(type, wrapper);
    }

    @Override
    public List<Type> getAll() {
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name");
        return typeDao.selectList(queryWrapper);
    }

    @Override
    public Type getTypeById(Integer id) {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.select("id", "name");
        return typeDao.selectOne(wrapper);
    }


    public List<TypeTop> getTopType() {
        return typeDao.getTopType();
    }

    @Override
    public List<TypeTop> getTypes() {
        return typeDao.getTypes();
    }
}
