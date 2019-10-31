package com.funsonli.bootan.common.aop;

import com.funsonli.bootan.common.annotation.BootanLog;
import com.funsonli.bootan.common.util.CommonUtil;
import com.funsonli.bootan.common.util.IpUtil;
import com.funsonli.bootan.module.base.entity.Log;
import com.funsonli.bootan.module.base.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 日志aop
 *
 * @author Funson
 * @date 2018-06-11
 */
@Slf4j
@Aspect
@Component
public class BootanLogAspect {

    @Autowired
    LogService modelService;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Pointcut("@annotation(com.funsonli.bootan.common.annotation.BootanLog)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) {
        Object result = null;
        long beginTime = System.currentTimeMillis();

        try {
            result = point.proceed();
            long endTime = System.currentTimeMillis();

            insertLog(point, endTime - beginTime);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    private void insertLog(ProceedingJoinPoint point, long time) {
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        Log model = new Log();

        String username = "";
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        username = user.getUsername();
        model.setUsername(username);

        model.setCostTime((int)(time));

        BootanLog userAction = method.getAnnotation(BootanLog.class);
        if (userAction != null) {
            // 注解上的描述
            model.setName(userAction.value());
            model.setType(userAction.type());
        }

        Map<String, String[]> params = request.getParameterMap();
        model.setRequestUrl(request.getRequestURI());
        model.setRequestType(request.getMethod());
        model.setRequestParam(CommonUtil.map2JsonString(params));

        model.setIp(IpUtil.getUserIP(request));
        model.setIpInfo(IpUtil.getCityInfo(IpUtil.getUserIP(request)));

        modelService.save(model);
    }
}
