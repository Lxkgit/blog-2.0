package com.blog.common.valication.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target(ElementType.FIELD) // 注解作用范围
@Retention(RetentionPolicy.RUNTIME) // 元注解 指明自定义注解的生命周期
@Constraint(validatedBy= EqualImpl.class)  // 将该注解标记为一个自定义约束注解  注解添加验证器
public @interface Equal {

    String message() default "value illegal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    String value() default "" ;


    /**
     * 是否不能为空，true：校验为null时验证不通过；false：校验为null时验证通过。
     * 默认 不校验空
     */
    boolean checkNull() default false;

    /**
     * @author Emmanuel Bernard
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @interface List {
        Equal[] value();
    }
}
