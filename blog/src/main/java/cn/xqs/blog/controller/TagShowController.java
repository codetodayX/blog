package cn.xqs.blog.controller;

import cn.xqs.blog.pojo.Blog;
import cn.xqs.blog.pojo.Tag;
import cn.xqs.blog.service.BlogService;
import cn.xqs.blog.service.TagService;
import cn.xqs.blog.vo.TagTop;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @RequestMapping("/tags")
    public String tags(Tag tag,
                       Model model,
                       @RequestParam(defaultValue = "1") Integer curPage) {
        //1.获取所有标签
        List<TagTop> tags = tagService.getTags();

        //2.根据标签名，获取对应博客列表
        if (tag.getId() == null) {
            tag.setId(tags.get(0).getId());
        }
        if (tag.getName() == null) {
            tag.setName(tags.get(0).getName());
        }
        //2.因为PageHelper不支持嵌套SQL，因此我们分两次查询
        //2.1首先根据标签名，查询对应的所有博客ID
        List<Integer> ids = blogService.getIdsByTagName(tag.getName());
        //2.2然后根据博客ID，查询博客列表
        PageHelper.startPage(curPage, 100);
        List<Blog> blogs = blogService.getTagBlogs(ids);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        //3.存入数据
        model.addAttribute("tags", tags);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("activeId", tag.getId());
        model.addAttribute("activeName", tag.getName());
        //4.跳转
        return "tags";
    }
}
