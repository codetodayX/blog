package cn.xqs.blog.service.impl;

import cn.xqs.blog.dao.BlogAndTagDao;
import cn.xqs.blog.dao.BlogDao;
import cn.xqs.blog.dao.CommentDao;
import cn.xqs.blog.pojo.*;
import cn.xqs.blog.service.BlogService;
import cn.xqs.blog.service.TagService;
import cn.xqs.blog.service.TypeService;
import cn.xqs.blog.utils.MarkdownUtils;
import cn.xqs.blog.vo.BlogQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.javassist.NotFoundException;
import org.apache.tomcat.util.buf.StringUtils;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Service

public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements BlogService {

    @Autowired
    private BlogDao blogDao;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogAndTagDao blogAndTagDao;
    @Autowired
    private TypeService typeService;
    @Autowired
    private CommentDao commentDao;

    @Override
    public void saveBlog(Blog blog) {

        //3.设置tags
        List<Integer> tagIds = getList(blog.getTagIds());
        List<Tag> tags = new ArrayList<>();
        for (Integer tagId : tagIds) {
            Tag tag = tagService.getTagById(tagId);
            tags.add(tag);
        }
        blog.setTags(tags);

        //4.设置createTime
        blog.setCreateTime(new Date());
        //5.设置updateTime
        blog.setUpdateTime(new Date());
        //6.设置views
        blog.setViews(0L);
        //7.将数据插入blog表中
        blogDao.insert(blog);
        //8.插入完成之后，还需更新t_blog_tag关系表
        for (Tag tag : tags) {
            //插入博客数据时，id会自动插入blog对象中
            BlogAndTag blogAndTag = new BlogAndTag(tag.getId(), blog.getId());
            blogAndTagDao.insert(blogAndTag);
        }
    }

    @Override
    public PageInfo<Blog> getAllBlog(BlogQuery blogQuery) {

        if (blogQuery.getCurPage() != null) {
            PageHelper.startPage(blogQuery.getCurPage(), 5);
            List<Blog> blogs = blogDao.getAllBlog(blogQuery);       //Sql语句必须紧跟startPage后面
            PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
            return pageInfo;
        } else {
            PageHelper.startPage(1, 5);
            List<Blog> blogs = blogDao.getAllBlog(blogQuery);
            PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
            return pageInfo;
        }

    }

    @Override
    public Blog getEditorBlog(Integer id) {
        return blogDao.getEditorBlog(id);
    }

    @Override
    public void editorBlog(Blog blog) {
        //1.对除标签字段的其它属性进行更新
        UpdateWrapper<Blog> wrapper = new UpdateWrapper<>();
        wrapper.set("flag", blog.getFlag());
        wrapper.set("title", blog.getTitle());
        wrapper.set("info", blog.getInfo());
        wrapper.set("type_id", blog.getType().getId());
        wrapper.set("firstPicture", blog.getFirstPicture());
        wrapper.set("description", blog.getDescription());
        wrapper.set("recommend", blog.getRecommend());
        wrapper.set("shareStatement", blog.getShareStatement());
        wrapper.set("appreciation", blog.getAppreciation());
        wrapper.set("commentEnabled", blog.getCommentEnabled());
        wrapper.set("published", blog.getPublished());
        wrapper.eq("id", blog.getId());
        System.out.println(blog.getPublished() + "......");
        blogDao.update(blog, wrapper);

        //2.完成对标签字段的更新
        //2.1根据博客ID，删除关系表中的数据
        QueryWrapper<BlogAndTag> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("blog_id", blog.getId());
        blogAndTagDao.delete(wrapper2);
        //2.2重新往关系表中插入数据
        List<Integer> list = getList(blog.getTagIds());
        for (Integer tagId : list) {
            BlogAndTag blogAndTag = new BlogAndTag(tagId, blog.getId());
            blogAndTagDao.insert(blogAndTag);
        }
    }

    @Override
    public void removeBlog(Integer id) {
        //1.根据博客ID，删除关系表中的数据
        QueryWrapper<BlogAndTag> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id", id);
        blogAndTagDao.delete(wrapper);
        //2.在根据博客ID，删除t_comment表的数据
        QueryWrapper<Comment> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("blog_id", id);
        commentDao.delete(wrapper2);
        //3.根据博客ID，删除博客内容
        blogDao.deleteById(id);
    }

    @Override
    public List<Blog> getIndexBlogs(Integer curPage) {
        return blogDao.getIndexBlogs();
    }

    @Override
    public List<Blog> getRecommends() {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title");           //只查询博客id，博客标题
        wrapper.eq("recommend", 1);      //只查询博客为推荐的
        wrapper.orderByDesc("updateTime");      //按更新时间降序
        wrapper.last("limit 0,3");              //只查询前3条数据
        //执行Sql
        return blogDao.selectList(wrapper);
    }

    @Override
    public List<Blog> getAllBlogByTypeId(BlogQuery blogQuery) {
        return blogDao.getAllBlogByTypeId(blogQuery);
    }

    @Override
    public List<Blog> getAllBlogByTypeId(Integer typeId) {
        return blogDao.getAllBlogByTypeId(typeId);
    }

    @Override
    public List<Blog> getTagBlogs(List<Integer> ids) {
        return blogDao.getTagBlogs(ids);
    }

    @Override
    public List<Integer> getIdsByTagName(String tagName) {
        return blogDao.getIdsByTagName(tagName);
    }

    @Override
    public List<Blog> search(String query) {
        //1.首先需要使用正则表达式对查询体条件，去除所有空格，并加上%
        String query2 = query.replaceAll("\\s*", "");
        String realQuery = "%" + query2 + "%";
        return blogDao.search(realQuery);
    }

    @Override
    public Map<String, List<Blog>> getArchiveBlogs() {
        //1.获取所有的博客含有的年份
        List<String> years = blogDao.getYears();
        //2.根据年份获取对应的博客列表
        Map<String, List<Blog>> blogs = new HashMap<>();
        for (String year : years) {
            System.out.println(year);
            //2.2根据年份查询对应博客列表
            List<Blog> blogs1 = blogDao.getBlogsByYear(year);
            //2.3将数据存入Map
            blogs.put(year, blogs1);
        }
        //3.返回数据
        return blogs;
    }

    @Override
    public Blog getBlogById(Integer id) {
        return blogDao.getBlogById(id);
    }

    @Override
    public Blog getAndConvert(Integer id) {
        Blog blog = blogDao.getBlogById(id);
        //1.因为直接调用工具类方法，也会对数据库中的数据进行修改，但是我们希望数据库中存储的博客内容依旧保持markdown形式，所以必须复制一个Bean
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getInfo();
        b.setInfo(MarkdownUtils.markdownToHtmlExtensions(content));

        //每次访问都需要加一次访问次数
        UpdateWrapper<Blog> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", blog.getId());
        wrapper.set("views", blog.getViews() + 1);
        blogDao.update(null, wrapper);
        return b;
    }


    //该方法用与将id组成的字符串转换成对应的List集合
    public List<Integer> getList(String tagIds) {
        List<Integer> ids = new ArrayList<>();
        String[] split = tagIds.split(",");
        for (int i = 0; i < split.length; i++) {
            ids.add(Integer.parseInt(split[i]));
        }
        return ids;
    }
}
