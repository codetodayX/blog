package cn.xqs.blog;

import cn.xqs.blog.pojo.Blog;
import cn.xqs.blog.pojo.Comment;
import cn.xqs.blog.service.BlogService;
import cn.xqs.blog.service.CommentService;
import cn.xqs.blog.service.UserService;
import cn.xqs.blog.utils.MD5Utils;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.DoubleStream;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    CommentService commentService;
    private Date date;

    @Test
    void contextLoads() {
    }

    @Before
    public void test6() {
        date = new Date();
    }

    @Test
    public void test() throws ParseException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(date);
        System.out.println(format + "-----");
        Date date1 = simpleDateFormat.parse(format);
        System.out.println(date1);
    }

    @Test
    //测试复杂嵌套SQl
    public void test2() {
//        List<Blog> blogs = blogService.getTagBlogs("Java");
//        for (Blog blog : blogs) {
//            System.out.println(blog.getTags());
//        }
//    }
    }

    @Test
    //测试去除字符串空格
    public void test3() {
        String s = "    我爱你  ";
        String s1 = s.replaceAll("\\s*", "");
        System.out.println(s1);

    }

    @Test
    //测试TreeMap排序
    public void test4() {
        Map<String, String> map = new HashMap<>();
        map.put("2020", "123");
        map.put("2021", "321");
        map.put("2022", "1234");
        map.put("2023", "1234");
        map.put("2019", "1234");
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.putAll(map);
        for (String s : treeMap.keySet()) {
            System.out.print(s + "----");
            System.out.println(treeMap.get(s));
        }
    }

    @Test
    //测试TreeMap排序时间
    public void test5() {
        TreeMap<Comment, Comment> map = new TreeMap<>(new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                System.out.println(o1);
                System.out.println(o2);
                return o1.getCreateTime().compareTo(o2.getCreateTime());
            }
        });
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        comment1.setCreateTime(new Date());
        comment2.setCreateTime(new Date());
        System.out.println(comment1.getCreateTime());
        System.out.println(comment2.getCreateTime());
        map.put(comment1, comment1);
        map.put(comment2, comment2);
        for (Comment comment : map.keySet()) {
            System.out.print(comment.getCreateTime());
        }
    }


    @Test
    //测试获取博客列表方法
    public void test7() {
    }

    @Test
    public void tst8() {
        String code = MD5Utils.code("123456");
        System.out.println(code);
    }
}
