package cn.xqs.blog.controller.admin;

import cn.xqs.blog.pojo.User;
import cn.xqs.blog.service.UserService;
import cn.xqs.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    UserService userService;

    //跳转到登录页面
    @GetMapping(path = "/")
    public String loginPage() {
        return "admin/login";
    }


    //用户登录
    @RequestMapping(path = "/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String checkCode,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        //1.首先检验验证码
        String realCheckCode = (String) session.getAttribute("CHECKCODE_SERVER");
        System.out.println(realCheckCode);
        //1.2拿到验证码后立刻销毁session中的数据
        session.removeAttribute("CHECKCODE_SERVER");
        if (!realCheckCode.equalsIgnoreCase(checkCode)) {
            redirectAttributes.addFlashAttribute("errorMsg", "验证码错误!");
            return "redirect:/admin/";
        }
        //2.然后判断用户名密码是否正确
        User user = userService.getUserByUsernameAndPassword(username, MD5Utils.code(password));
        if (user != null) {
            //将密码置为null，因为user需要存入session域中,删除密码防止引发安全问题
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "用户名或密码错误!");
            return "redirect:/admin/";
        }
    }


    //用户注销
    @RequestMapping(path = "/loginOut")
    public String loginOut(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin/";
    }


    //获取验证码
    @RequestMapping("/checkCode")
    public void checkCode(HttpServletResponse response,
                          HttpSession session) throws IOException {

        //服务器通知浏览器不要缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");

        //在内存中创建一个长80，宽30的图片，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 35;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0, 0, width, height);

        //产生4个随机验证码，12Ey
        String checkCode = getCheckCode();
        System.out.println(checkCode);

        //将验证码放入HttpSession中
        session.setAttribute("CHECKCODE_SERVER", checkCode);

        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体", Font.BOLD, 24));
        //向图片上写入验证码
        g.drawString(checkCode, 15, 25);

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        ImageIO.write(image, "PNG", response.getOutputStream());
    }


    /**
     * 产生4位随机字符串
     */
    private String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 4; i++) {
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }
}
