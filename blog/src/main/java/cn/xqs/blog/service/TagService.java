package cn.xqs.blog.service;

import cn.xqs.blog.pojo.Tag;
import cn.xqs.blog.pojo.Type;
import cn.xqs.blog.vo.TagTop;
import cn.xqs.blog.vo.TypeTop;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface TagService {
    public Page<Tag> getList(Long curPage);        //查询分页数据

    public boolean saveTag(Tag tag);                //新增标签

    public void remove(Long id);                    //删除

    public void update(Tag tag);                  //修改标签名称

    public Tag getTagById(Integer id);               //根据ID查询标签

    List<Tag> getAll();

    public List<TagTop> getTopTags();               //首页右侧标签Top

    public List<TagTop> getTags();            //获取所有标签及其对应的博客数目
}
