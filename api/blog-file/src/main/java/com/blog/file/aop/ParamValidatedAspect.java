package com.blog.file.aop;

import com.blog.common.exception.ValidException;
import com.blog.file.mqtt.SpringUtils;
import com.blog.file.utils.AppContext;
import com.blog.common.valication.annotation.ParamValidated;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @description: 参数校验切面
 * @Author: 308501
 * @date 2024/1/12 11:13
 */

@Aspect
@Component
public class ParamValidatedAspect {
    private final Logger logger = LoggerFactory.getLogger(ParamValidatedAspect.class);

    @Pointcut("execution(* com.blog.file.controller..*.*(..))")
    public void doValidate() {
    }

    @Before("doValidate()")
    public void doBefore(JoinPoint joinPoint) throws ValidException {
        // 获取所有参数值，包括null
        Object[] args = joinPoint.getArgs();
        if (Objects.nonNull(args) && args.length > 0) {
            // 获取方法签名
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            // 获取方法
            Method method = methodSignature.getMethod();
            // 获取方法参数注解数组
            Annotation[][] annotations = method.getParameterAnnotations();
            for (int i = 0; i < args.length; i++) {
                if (Objects.nonNull(args[i])) {
                    // 当参数值非空时，执行
                    for (Annotation annotation : annotations[i]) {
                        if (annotation instanceof ParamValidated) {
                            // 调用校验管理器，执行参数校验
                            ParamValidateManager validateManager = SpringUtils.getBean(ParamValidateManager.class);
                            validateManager.validate(args[i], ((ParamValidated) annotation).type());
                        }
                    }
                }
            }
        }
    }
}
