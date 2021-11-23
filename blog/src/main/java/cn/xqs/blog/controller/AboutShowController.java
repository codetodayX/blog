package cn.xqs.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutShowController {

    @RequestMapping(path = "/about")
    public String about() {
        return "about";
    }
}
