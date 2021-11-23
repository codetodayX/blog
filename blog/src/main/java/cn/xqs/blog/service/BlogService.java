package cn.xqs.blog.service;

import cn.xqs.blog.pojo.Blog;
import cn.xqs.blog.vo.BlogQuery;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BlogService extends IService<Blog> {
    public void saveBlog(Blog blog);                        //新增博客信息

    public PageInfo<Blog> getAllBlog(BlogQuery blogQuery);  //后台博客列表展示

    public Blog getEditorBlog(Integer id);                  //后台编辑页面展示

    public void editorBlog(Blog blog);                      //编辑博客内容

    public void removeBlog(Integer id);                     //根据博客ID删除对应博客

    public List<Blog> getIndexBlogs(Integer curPage);       //获取首页博客列表

    public List<Blog> getRecommends();                      //获取首页最新推荐

    public List<Blog> getAllBlogByTypeId(BlogQuery blogQuery);                 //根据分类ID获取博客列表

    public List<Blog> getAllBlogByTypeId(Integer typeId);                 //根据分类ID获取博客列表

    public List<Blog> getTagBlogs(List<Integer> ids);              //获取包含指定标签名的博客列表

    public List<Integer> getIdsByTagName(String tagName);       //查询包含指定标签名的博客列表ID

    public List<Blog> search(String query);                     //首页全局搜索

    public Map<String, List<Blog>> getArchiveBlogs();            //归档博客列表展示

    public Blog getBlogById(Integer id);                                //根据博客ID查询博客内容

    public Blog getAndConvert(Integer id);                          //根据博客ID查询博客内容，并且将markdown文本转成html格式
}
