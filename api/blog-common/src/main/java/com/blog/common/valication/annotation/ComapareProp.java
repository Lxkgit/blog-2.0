package com.blog.common.valication.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 被标注的属性和注解中的属性比较大小，被标注属性值必须小于注解的属性值
 * propName可以是多个以逗号分隔
 * @author 50864
 * @param
 * @return
 */
@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=ComparePropValidator.class)
public @interface ComapareProp {
    String message() default "value illegal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String propName() default "" ;

    /**
     * @author Emmanuel Bernard
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ComapareProp[] value();
    }
}
