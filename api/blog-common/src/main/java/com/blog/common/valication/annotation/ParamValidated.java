package com.blog.common.valication.annotation;

import java.lang.annotation.*;

/**
 * 参数校验注解
 * 
 * @author 253693
 * @date 2022年3月14日
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamValidated {

    /**
     * 类型
     * 
     * 当有多个REST接口的待校验参数对象类型相同，且校验逻辑不同时，需要定义type值进行区分
     * @return
     */
    Class<?> type() default Object.class;
}
