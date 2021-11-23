package cn.xqs.blog.service.impl;

import cn.xqs.blog.dao.BlogAndTagDao;
import cn.xqs.blog.dao.TagDao;
import cn.xqs.blog.pojo.BlogAndTag;
import cn.xqs.blog.pojo.Tag;
import cn.xqs.blog.service.TagService;
import cn.xqs.blog.vo.TagTop;
import cn.xqs.blog.vo.TypeTop;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagDao tagDao;
    @Autowired
    BlogAndTagDao blogAndTagDao;


    @Override
    public Page<Tag> getList(Long curPage) {
        Page<Tag> page = new Page<>(curPage, 5);

        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name");    //告诉Mybatis-plus，只查询id和name字段
        return tagDao.selectPage(page, wrapper);
    }

    @Override
    public boolean saveTag(Tag tag) {
        //1.插入前，查看数据库中是否已存在该标签名
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name");
        wrapper.eq("name", tag.getName());
        Tag tag1 = tagDao.selectOne(wrapper);
        if (tag1 != null) {
            return false;
        } else {
            tagDao.insert(tag);
            System.out.println(tag.getId());
            return true;
        }

    }

    @Override
    public void remove(Long id) {
        //1.先根据标签ID，删除关系表中的数据
        QueryWrapper<BlogAndTag> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_id", id);
        blogAndTagDao.delete(wrapper);
        //2.然后再删除标签
        tagDao.deleteById(id);
    }

    @Override
    public void update(Tag tag) {
        UpdateWrapper<Tag> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", tag.getId());
        wrapper.set("name", tag.getName());

        tagDao.update(tag, wrapper);
    }

    @Override
    public Tag getTagById(Integer id) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name");
        wrapper.eq("id", id);
        return tagDao.selectOne(wrapper);
    }

    @Override
    public List<Tag> getAll() {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name");
        return tagDao.selectList(wrapper);
    }

    @Override
    public List<TagTop> getTopTags() {
        return tagDao.getTopTags();
    }

    @Override
    public List<TagTop> getTags() {
        return tagDao.getTags();
    }
}
