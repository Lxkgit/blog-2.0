package com.blog.common.valication.annotation;

import com.blog.common.util.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.HashSet;
import java.util.Set;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证：bean的属性值是否在枚举类中
 */

@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=StringValidate.Validate.class)
public @interface StringValidate {

    String message() default "value illegal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    /**
     * 字符串允许的字符范围，不能为空
     * @return
     */
    String acceptChar() default "";

    /**
     * 字符串必须要有的字符，可以为空
     * @return
     */
    String existChar() default "";

    /**
     * 不能只有的字符，可以为空
     * @return
     */
    String notJustChar() default "";

    /**
     * true: 可以为 null false: 不能为null
     * 默认 可以为 null
     */
    boolean checkNull() default true;

    /**
     * true: 可以为空字符串 false: 不可以为空字符串
     * 默认可以为空字符串
     */
    boolean checkEmpty() default true;

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        StringValidate[] value();
    }

    class Validate implements ConstraintValidator<StringValidate, Object> {

        private String acceptChar;

        private String existChar;

        private String notJustChar;

        private Boolean checkNull;

        private Boolean checkEmpty;



        @Override
        public void initialize(StringValidate stringValidate) {
            acceptChar = stringValidate.acceptChar();
            existChar = stringValidate.existChar();
            notJustChar = stringValidate.notJustChar();
            checkNull = stringValidate.checkNull();
            checkEmpty = stringValidate.checkEmpty();
        }

        @Override
        public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

            // 校验的字符不能为空
            if (StringUtils.isEmpty(acceptChar)) {
                return false;
            }

            // 传入参数是否可以为null
            if (o == null) {
                return checkNull;
            }

            // 传入参数是否可以为空字符串
            if (o.equals("")) {
                return checkEmpty;
            }

            // 输入字符集合
            String inputString = String.valueOf(o);
            Set<Character> inputSet = StringUtils.stringToSet(inputString);

            // 接收字符集合
            Set<Character> acceptCharSet = StringUtils.stringToSet(acceptChar);

            // 必须要有的字符集合
            Set<Character> existCharSet = StringUtils.stringToSet(existChar);

            // 不能只有的字符集合
            Set<Character> notJustCharSet = StringUtils.stringToSet(notJustChar);

            // 输入的字符串是否包含全部必须存在的字符
            if (!inputSet.containsAll(existCharSet)) {
                return false;
            }

            // 不能只有的字符串包含了输入的字符串 返回false
            if (notJustCharSet.containsAll(inputSet)) {
                return false;
            }

            // 输入字符集合中移除接收字符集合的元素
            inputSet.removeAll(acceptCharSet);

            // 如果输入字符集合为空则通过校验
            return inputSet.isEmpty();
        }

    }
}
