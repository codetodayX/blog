package cn.xqs.blog.controller;

import cn.xqs.blog.pojo.Blog;
import cn.xqs.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class ArchivesShowController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/archives")
    public String archives(Model model) {
        Map<String, List<Blog>> blogs = blogService.getArchiveBlogs();
        Integer blogCount = 0; //获取博客总数
        for (String s : blogs.keySet()) {
            for (Blog blog : blogs.get(s)) {
                blogCount++;
            }
        }
        //因为存放数据Map是HashMap，这样会导致key没有顺序，可以使用TreeMap对其进行排序
        Map<String, List<Blog>> treeMap = new TreeMap<>(new Comparator<String>() {
            //使用定制排序，从大到小排
            @Override
            public int compare(String o1, String o2) {
                return -o1.compareTo(o2);
            }
        });
        treeMap.putAll(blogs);
        model.addAttribute("archivesMap", treeMap);
        model.addAttribute("blogCount", blogCount);
        return "archives";
    }
}
