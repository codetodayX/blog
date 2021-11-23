package cn.xqs.blog.controller.admin;

import cn.xqs.blog.pojo.Blog;
import cn.xqs.blog.pojo.Tag;
import cn.xqs.blog.pojo.Type;
import cn.xqs.blog.pojo.User;
import cn.xqs.blog.service.BlogService;
import cn.xqs.blog.service.TagService;
import cn.xqs.blog.service.TypeService;
import cn.xqs.blog.service.UserService;
import cn.xqs.blog.vo.BlogQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/admin")
public class BlogsController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    //blogs页面第一次请求时，会执行该控制器方法
    @RequestMapping(path = "/blogs")
    public String blogs(BlogQuery blogQuery,
                        Model model,
                        @RequestParam(value = "blogMsg", defaultValue = "null") String blogMsg) {
        PageInfo<Blog> pageInfo = blogService.getAllBlog(blogQuery);

        List<Type> list = typeService.getAll();
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("types", list);       //拿到博客分页数据的同时，将分类数据也一并全部查出
        if (blogMsg.equals("null")) {
            model.addAttribute("blogMsg", null);
        } else {
            model.addAttribute("blogMsg", blogMsg);
        }
        return "admin/blogs";
    }


    //thymeleaf与ajax相结合,因为条件查询栏不能刷新，只允许刷新列表展示,所以必须使用ajax异步交互
    @RequestMapping(path = "/blogs/search")
    public String blogsSearch(BlogQuery blogQuery,
                              Model model) {
        PageInfo<Blog> pageInfo = blogService.getAllBlog(blogQuery);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/blogs :: blogList";
    }


    //跳转到新增博客页面
    @RequestMapping(path = "/blogs/input")
    public String blogsInput(Model model) {
        List<Type> types = typeService.getAll();
        List<Tag> tags = tagService.getAll();
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        return "admin/blogs-input";
    }


    //博客新增逻辑
    @RequestMapping(path = "/blogs/inputInvoke")
    public String blogsInputInvoke(Blog blog,
                                   HttpSession session,
                                   RedirectAttributes attributes) {
        //1.设置User和userId
        blog.setUser((User) session.getAttribute("user"));
        blog.setUserId(blog.getUser().getId());
        //2.设置type和typeId
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        blog.setTypeId(blog.getType().getId());
        blogService.saveBlog(blog);
        attributes.addAttribute("blogMsg", "发布成功!");
        return "redirect:/admin/blogs";
    }


    //后台编辑页面展示
    @RequestMapping(path = "/blogs/editor")
    public String blogsEditor(@RequestParam Integer id,
                              Model model) {
        Blog blog = blogService.getEditorBlog(id);
        //将tagIds完善，跳转页面之后要用
        StringBuilder stringBuilder = new StringBuilder();
        List<Tag> tags = blog.getTags();
        for (int i = 0; i < tags.size(); i++) {
            stringBuilder.append(tags.get(i).getId() + ",");
        }
        //去掉最后的字符串","
        String s = stringBuilder.toString();
        String substring = s.substring(0, s.length() - 1);
        blog.setTagIds(substring);
        //获取分类和标签数据
        List<Type> types = typeService.getAll();
        List<Tag> tags2 = tagService.getAll();
        model.addAttribute("types", types);
        model.addAttribute("tags", tags2);
        model.addAttribute("blog", blog);
        return "admin/blogs-editor";
    }


    //后台编辑逻辑
    @RequestMapping(path = "/blogs/editorInvoke")
    public String blogsEditorInvoke(Blog blog,
                                    RedirectAttributes attributes) {
        blogService.editorBlog(blog);
        attributes.addAttribute("blogMsg", "恭喜,修改成功!");
        return "redirect:/admin/blogs";
    }


    //后台博客删除逻辑
    @RequestMapping(path = "/blogs/remove")
    public String blogsRemove(@RequestParam Integer id,
                              RedirectAttributes attributes) {
        blogService.removeBlog(id);
        attributes.addAttribute("blogMsg", "恭喜,删除成功!");
        return "redirect:/admin/blogs";
    }
}
