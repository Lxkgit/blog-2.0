package com.blog.common.valication.annotation;

import com.blog.common.util.MyStringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @description: @Equal 注解验证器
 * @Author: 308501
 * @date 2024/1/12 10:16
 */

public class EqualImpl implements ConstraintValidator<Equal, Object> {

    private String value;

    private boolean checkNull;

    @Override
    public void initialize(Equal constraintAnnotation) {
        value = constraintAnnotation.value();
        checkNull = constraintAnnotation.checkNull();
    }

    @Override
    public boolean isValid(Object beanValue, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = false;
        if (MyStringUtils.isEmpty(value)) {
            return isValid;
        }
        if (null == beanValue) {
            if (!checkNull) {
                isValid = true;
            }
            return isValid;
        }
        String beanValueStr = String.valueOf(beanValue);
        if (value.equals(beanValueStr)) {
            isValid = true;
        }
        if (isValid) {
            return isValid;
        }
        String []  beanValueArray = value.split(",");
        for (String  fileValue : beanValueArray) {
            if (beanValue.toString().equals(fileValue)) {
                isValid = true;
            }
        }
        return isValid;
    }
}
