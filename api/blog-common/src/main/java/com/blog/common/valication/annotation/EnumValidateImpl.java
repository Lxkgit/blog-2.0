package com.blog.common.valication.annotation;

import com.blog.common.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * @description:
 * @Author: 308501
 * @date 2024/1/15 16:54
 */

public class EnumValidateImpl implements ConstraintValidator<EnumValidate, Object> {

    private Class<?> enumClass;

    private String methodName;
    private String split;
    private boolean acceptEmpty;

    @Override
    public void initialize(EnumValidate enumValidate) {
        enumClass = enumValidate.enumClass();
        methodName = enumValidate.methodName();
        split = enumValidate.split();
        acceptEmpty = enumValidate.acceptEmpty();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isValid = false;
        if (null == enumClass || StringUtils.isEmpty(methodName)) {
            return isValid;
        }
        if (null == value || StringUtils.isEmpty(String.valueOf(value))) {
            return acceptEmpty;
        }
        if (!enumClass.isEnum()) {
            return isValid;
        }
        try {
            String [] enumValus = String.valueOf(value).split(split);
            Method method = enumClass.getMethod(methodName);
            Object[] objects =  enumClass.getEnumConstants();
            boolean isFindFromEnums = false;
            for (String filedValue : enumValus) {
                isFindFromEnums = false;
                for (Object object : objects) {
                    Object objValue = method.invoke(object);
                    if (null == objValue) {
                        continue;
                    }
                    if (filedValue.equals(String.valueOf(objValue))) {
                        isFindFromEnums = true;
                        break;
                    }
                }
                if (isFindFromEnums) {
                    continue;
                } else {
                    isValid = false;
                    break;
                }
            }
            if (isFindFromEnums) {
                isValid = true;
            }
        } catch (Exception e) {
            isValid = false;
            e.printStackTrace();
        }
        return isValid;
    }

}
