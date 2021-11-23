package cn.xqs.blog.controller;

import cn.xqs.blog.pojo.Blog;
import cn.xqs.blog.pojo.Comment;
import cn.xqs.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CommentsShowController {

    @Autowired
    private CommentService commentService;
    @Value("${comment.avatar}")
    private String avatar;      //用户的头像是不变的
    @Value("${comment.adminAvatar}")
    private String adminAvatar;     //管理员头像

    @RequestMapping("/comments")
    public String comments(Comment comment,
                           Model model) {
        List<Comment> list = commentService.getCommentList(comment.getBlog().getId());
        model.addAttribute("list", list);
        //完成对评论部分的局部刷新
        return "blog :: commentList";

    }


    @RequestMapping("/comments/save")
    public String commentsSave(Comment comment,
                               RedirectAttributes attributes,
                               HttpSession session) {
        //1.设置评论时间
        comment.setCreateTime(new Date());
        //2.设置parentId
        if (comment.getParentComment().getId() == -1) {
            //说明是父级评论
            comment.setParentId(null);
        } else {
            comment.setParentId(comment.getParentComment().getId());
        }
        //3.设置blog_id,非空字段，必须赋值
        comment.setBlogId(comment.getBlog().getId());
        //4.设置当前评论是属于游客评论还是管理员评论
        if (session.getAttribute("user") != null) {
            //session中有管理员信息，说明是管理员评论
            comment.setType("博主");
            //2.设置博主头像
            comment.setHeadPicture(adminAvatar);
        } else {
            //否则为游客评论
            comment.setType("访客");
            //2.设置访客头像
            comment.setHeadPicture(avatar);
        }
        //5.保存评论
        commentService.saveComment(comment);

        //6.重定向
        attributes.addAttribute("blog.id", comment.getBlog().getId());
        return "redirect:/comments";
    }
}
