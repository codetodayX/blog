package cn.xqs.blog.controller;

import cn.xqs.blog.pojo.Blog;
import cn.xqs.blog.service.BlogService;
import cn.xqs.blog.service.TagService;
import cn.xqs.blog.service.TypeService;
import cn.xqs.blog.vo.TagTop;
import cn.xqs.blog.vo.TypeTop;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;


    //跳转至首页
    @RequestMapping(path = "/")
    public String index(@RequestParam(value = "curPage", defaultValue = "1") Integer curPage,
                        Model model) {
        //1.获取首页博客列表数据
        PageHelper.startPage(curPage, 5);
        List<Blog> blogs = blogService.getIndexBlogs(curPage);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo", pageInfo);
        //2.获取右侧分类Top
        List<TypeTop> types = typeService.getTopType();
        model.addAttribute("types", types);
        //3.获取右侧标签Top
        List<TagTop> tags = tagService.getTopTags();
        model.addAttribute("tags", tags);
        //4.获取右侧最新推荐Top
        List<Blog> recommends = blogService.getRecommends();
        model.addAttribute("recommends", recommends);
        return "index";
    }


    //全局搜索
    @RequestMapping(path = "/search")
    public String search(@RequestParam(defaultValue = "") String query,
                         @RequestParam(defaultValue = "1") Integer curPage,
                         Model model) {
        PageHelper.startPage(curPage, 8);
        List<Blog> blogs = blogService.search(query);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        for (Blog blog : pageInfo.getList()) {
            System.out.println(blog);
        }
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);

        return "search";
    }


    //博客详情页面展示
    @RequestMapping(path = "/blog")
    public String blog(@RequestParam Integer id,
                       Model model) {
        Blog blog = blogService.getAndConvert(id);
        model.addAttribute("blog", blog);
        return "blog";
    }
}
