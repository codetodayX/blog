package cn.xqs.blog.handler;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 可以拦截所有的异常
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //定义异常处理器，拦截所有的异常
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) {
        //打印发生错误的URL和异常信息
        logger.error("RequestURL {}, Exception {}", request.getRequestURL(), e);

        ModelAndView mv = new ModelAndView();
        mv.addObject("URL", request.getRequestURL());
        mv.addObject("exception", e);

        //设置跳转路径，跳转到错误页面
        mv.setViewName("error/error");

        return mv;
    }
}
