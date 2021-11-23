package cn.xqs.blog.dao;

import cn.xqs.blog.pojo.Blog;
import cn.xqs.blog.vo.BlogQuery;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlogDao extends BaseMapper<Blog> {
    public List<Blog> getAllBlog(BlogQuery blogQuery);                  //后台博客列表展示

    public Blog getEditorBlog(Integer id);                              //博客编辑页面展示

    public List<Blog> getIndexBlogs();                                  //首页博客列表展示

    public List<Blog> getAllBlogByTypeId(BlogQuery blogQuery);         //根据分类ID获取博客列表

    public List<Blog> getAllBlogByTypeId(Integer typeId);         //根据分类ID获取博客列表

    public List<Blog> getTagBlogs(List<Integer> ids);                   //获取包含指定标签名的博客列表

    public List<Integer> getIdsByTagName(String tagName);               //查询包含指定标签名的博客列表ID

    public List<Blog> search(String query);                             //首页全局搜索

    public List<String> getYears();                                     //查询博客列表所有的年份

    public List<Blog> getBlogsByYear(String year);                      //根据年份查询博客列表

    public Blog getBlogById(Integer id);                                //根据博客ID查询博客内容

    public void updateViews(Integer blogId);                            //根据博客ID和浏览次数更新浏览次数

    public Integer getViewsByBlogId(Integer blogId);                    //根据博客ID获取浏览次数

}
