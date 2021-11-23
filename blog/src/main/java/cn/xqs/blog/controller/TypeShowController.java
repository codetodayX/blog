package cn.xqs.blog.controller;

import cn.xqs.blog.dao.TypeDao;
import cn.xqs.blog.pojo.Blog;
import cn.xqs.blog.service.BlogService;
import cn.xqs.blog.service.TypeService;
import cn.xqs.blog.vo.BlogQuery;
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
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @RequestMapping(path = "/types")
    public String TypeShow(BlogQuery blogQuery,
                           Model model) {
        //1.拿到所有分类List<TypeTop>
        List<TypeTop> types = typeService.getTypes();
        //2.根据分类ID和当前页码，获取分页数据
        if (blogQuery.getCurPage() == null) {
            blogQuery.setCurPage(1);
        }
        if (blogQuery.getTypeId() == null) {
            blogQuery.setTypeId(types.get(0).getId());
        }
        PageHelper.startPage(blogQuery.getCurPage(), 8);
        List<Blog> blogs = blogService.getAllBlogByTypeId(blogQuery);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        //3.将数据存入Model
        model.addAttribute("types", types);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("activeId", blogQuery.getTypeId());                //还需将分类ID传回去，此ID可以帮助完成active样式的设置
        //4.跳转页面
        return "types";
    }
}
