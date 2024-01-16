package com.blog.common.valication.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证：bean的属性值是否在枚举类中
 */

@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=EnumValidateImpl.class)
public @interface EnumValidate {

    String message() default "value illegal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> enumClass() default Object.class;

    String methodName() default "" ;

    String split() default ",";

    boolean acceptEmpty() default false;
    /**
     * @author Emmanuel Bernard
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        EnumValidate[] value();
    }

}
