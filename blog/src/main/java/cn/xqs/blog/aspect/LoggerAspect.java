package cn.xqs.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 该切面类用来作日志处理，处理的内容包括:
 * 1.请求的URL
 * 2.请求的参数
 * 3.请求的控制器方法名称
 * 4.返回值
 */
@Aspect
@Component
public class LoggerAspect {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //定义切入点表达式，切面是controller层的所有方法
    @Pointcut("execution(* cn.xqs.blog.controller.*.*(..))")
    public void point() {
    }


    @Before("point()")
    public void before(JoinPoint pjp) {
        //1.获取请求URL和IP
        //对ServletRequestAttributes的解释:
        //ServletRequestAttributes实现了RequestAttributes接口，它保存了每次请求的HttpServletRequest对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        StringBuffer url = request.getRequestURL();
        String ip = request.getRemoteAddr();

        //2.获取请求参数和控制器方法名称
        Object[] params = pjp.getArgs();
        String methodTypeName = pjp.getSignature().getDeclaringTypeName();
        String name = pjp.getSignature().getName();
        String methodName = methodTypeName + "." + name;

        //3.使用日志处理器输出日志
        logger.info("url : {}", url);
        logger.info("ip : {}", ip);
        logger.info("methodName : {}", methodName);
        logger.info("params : {}", params);
    }


    @AfterReturning(returning = "resultValue", pointcut = ("point()"))
    public void afterReturning(Object[] resultValue) {
        logger.info("resultValue : {}", resultValue);
    }

}
