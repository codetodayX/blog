package cn.xqs.blog.service;

import cn.xqs.blog.pojo.Type;
import cn.xqs.blog.vo.TypeTop;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface TypeService {
    public boolean saveType(Type type);       //保存分类

    public Page<Type> getList(int curPage);        //查询分页数据

    public void remove(Integer id);             //根据ID删除对应分类

    public void update(Type type);          //修改分类名称

    public List<Type> getAll();            //查询所有分类

    public Type getTypeById(Integer id);      //根据ID查询对应分类

    public List<TypeTop> getTopType();          //获取右侧分类Top

    public List<TypeTop> getTypes();            //获取所有分类及其对应的博客数目
}
