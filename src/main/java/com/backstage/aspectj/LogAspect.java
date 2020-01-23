package com.backstage.aspectj;

import com.alibaba.fastjson.JSONObject;
import com.backstage.aspectj.annotation.Log;
import com.backstage.dao.SysLogMapper;
import com.backstage.entity.SysLog;
import com.backstage.entity.SysUser;
import com.backstage.service.SysLogService;
import com.backstage.unils.*;
import com.backstage.unils.text.Convert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 *
 * @description:
 *
 * @author: wanli3815@163.com
 *
 * @create: 2020-01-19 09:22
 **/
@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private SysLogService sysLogService;
    // 配置织入点
    @Pointcut("@annotation(com.backstage.aspectj.annotation.Log)")
    public void logPointCut()
    {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint)
    {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e);
    }


    protected void handleLog(final JoinPoint joinPoint, final Exception e)
    {
        try {
            log.info("切片开始时间:{}",new Date());
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null)
            {
                return;
            }

            SysUser user = new SysUser();
            SysLog sysLog = new SysLog();
            Object obj = SecurityUtils.getSubject().getPrincipal();
            if (StringUtils.isNotNull(obj)) {
                BeanUtils.copyProperties(obj, user);

            }
            if (StringUtils.isNotNull(user)) {
                sysLog.setUserId(user.getId());
                sysLog.setUsername(user.getUsername());
            }

            //注解上的描述
            sysLog.setOperation(controllerLog.title());
            sysLog.setBusinessType(controllerLog.busionesstype().ordinal());
            sysLog.setCreateTime(new Date());

            String ipAddr = IpUtils.getIpAddr(ServletUtils.getRequest());
            String ip = ipAddr;
            sysLog.setIp(ip);
            sysLog.setId(Convert.toStr(new IdWorker().nextId()));
            sysLog.setUrl(ServletUtils.getRequest().getRequestURI());
            //请求的方法名
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            sysLog.setMethod(className + "." + methodName + "()");

            //请求的参数
            Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
            String params = JSONObject.toJSONString(map);
            sysLog.setParams(StringUtils.substring(params, 0, 5000));

            sysLogService.insertLog(sysLog);
            log.info("切片结束时间:{}",new Date());
        } catch (Exception exp)
        {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
        // sysLogMapper.insertSelective(sysLog);
    }
    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
