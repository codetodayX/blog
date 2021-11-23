package cn.xqs.blog.controller;

import cn.xqs.blog.pojo.Comment;
import cn.xqs.blog.pojo.Message;
import cn.xqs.blog.service.MessageService;
import cn.xqs.blog.vo.MyPageInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {

    @Value("${comment.avatar}")
    private String avatar;      //用户的头像是不变的
    @Value("${comment.adminAvatar}")
    private String adminAvatar;     //管理员头像

    @Autowired
    private MessageService messageService;

    @RequestMapping("/message")
    public String message(@RequestParam(defaultValue = "1") Integer curPage,
                          Model model) {
        model.addAttribute("curPage", curPage);
        return "message";
    }


    @RequestMapping("/message/messageList")
    public String messageList(@RequestParam(defaultValue = "1") Integer curPage,
                              Model model) {
        //1.开始分页
        PageHelper.startPage(curPage, 5);
        //2.执行service方法，获取根评论
        List<Message> rootMessages = messageService.getRootMessage();
        //3.将rootMessages放入PageInfo对象中
        PageInfo<Message> pageInfo = new PageInfo<>(rootMessages);
        //5.调用service获取当前页面的所有评论,只需调用即可更改PageInfo的list，因为存放的是对象
        messageService.getAllMessage(pageInfo.getList());
        //6.PageInfo存入Model中
        System.out.println(pageInfo.getPageNum() + "....");
        //当当前页不属于正常范围时，PageInfo会有BUG，需要手动设置
        if (curPage > pageInfo.getPages()) {
            pageInfo.setList(null);
            pageInfo.setPageNum(curPage);
        }
        if (curPage < 1) {
            pageInfo.setList(null);
            pageInfo.setPageNum(curPage);
        }
        model.addAttribute("list", pageInfo.getList());
        model.addAttribute("pageNum", pageInfo.getPageNum());
        model.addAttribute("total", messageService.getCount());
        model.addAttribute("hasPrevious", pageInfo.isHasPreviousPage());
        model.addAttribute("hasNext", pageInfo.isHasNextPage());
        model.addAttribute("pages", pageInfo.getPages());
        //7.跳转(这里使用thymeleaf和Ajax结合的方式)
        return "message :: commentList";
    }


    @RequestMapping("/message/save")
    public String messageSave(Message message,
                              @RequestParam(defaultValue = "1") int curPage,
                              RedirectAttributes attributes,
                              HttpSession session) {
        System.out.println("....");
        //1.设置评论时间
        message.setCreateTime(new Date());
        //3.设置parentId
        if (message.getParentMessage().getId() == -1) {
            //说明是父级评论
            message.setParentId(null);
        } else {
            message.setParentId(message.getParentMessage().getId());
        }
        //5.设置当前评论是属于游客评论还是管理员评论
        if (session.getAttribute("user") != null) {
            //session中有管理员信息，说明是管理员评论
            message.setType("博主");
            message.setHeadPicture(adminAvatar);
        } else {
            //否则为游客评论
            message.setType("访客");
            message.setHeadPicture(avatar);
        }
        //6.保存评论
        messageService.saveComment(message);

        //7.重定向
        attributes.addAttribute("curPage", curPage);
        return "redirect:/message";
    }
}
