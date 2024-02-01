package com.blog.common.util;

import com.alibaba.fastjson.JSONObject;
import com.blog.common.valication.annotation.ComapareProp;
import com.blog.common.valication.annotation.ComparePropValidator;
import com.blog.common.valication.group.AddGroup;
import org.springframework.util.CollectionUtils;

import javax.validation.*;
import javax.validation.groups.Default;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @description:
 * @Author: 308501
 * @date 2024/2/1 17:11
 */

public class BeanValidationUtil {

    private static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * 验证T中的属性是否合法
     * @param t
     * @param group
     * @return Map<String,String>
     * @auther 27919
     * @date 2020年4月18日
     */
    public static <T> Map<String,String> validationBean(T t,Class<? extends Object> group) {
        if (null == group) {
            group = Default.class;
        }
        Set<ConstraintViolation<T>>  violations = validator.validate(t,group);
        Map<String,String> errorMap = new HashMap<>();

        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field f:fields){
            if(f.getAnnotation(ComapareProp.class)!=null){
                f.setAccessible(true);
                String compareProps = f.getAnnotation(ComapareProp.class).propName();
                String[] comparePropList = compareProps.split(",");
                List<Object> comparePropValList = new ArrayList<>();
                try {
                    for(String compareProp : comparePropList) {
                        Field compareField = clazz.getDeclaredField(compareProp);
                        compareField.setAccessible(true);
                        Object o =compareField.get(t);
                        comparePropValList.add(o);
                    }
                    if (f.get(t) != null){
                        ConstraintValidatorContext constraintValidatorContext = new ComparePropValidator().valid(f.get(t),comparePropValList);
                        if(constraintValidatorContext != null) {
                            String message = "下限必须小于上限";
                            errorMap.put(f.getName(), message);

                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }


        if (CollectionUtils.isEmpty(violations)) {
            return errorMap;
        }
        for (ConstraintViolation<T> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String value = String.valueOf(violation.getInvalidValue());
            String message = violation.getMessage();
            errorMap.put(violation.getPropertyPath().toString(), message);

        }
        return errorMap;
    }

//    DeviceRuleVO rule = JSONObject.toJavaObject(JSONObject.parseObject(taskRelIvsVO.getConfig()),
//            DeviceRuleEnum.getRuleImpl(taskRelIvsVO.getAlarmType()));
//    ErrorMsgVO errorMsg = validateIvsRuleInfo(rule);
//            if (errorMsg.getErrorMap() != null) {
//        logger.info("ivsRule valid failed,:"+errorMsg.toString());
//        throw new ValidException(BaseSpringErrorCode.COMMON_ILLEGAL_ARGUMENT);
//    }

//    private static ErrorMsgVO validateIvsRuleInfo(DeviceRuleVO rule) {
//        ErrorMsgVO errorMsg = new ErrorMsgVO();
//        Map<String,String> errorMap = BeanValidationUtil.validationBean(rule, AddGroup.class);
//        errorMsg.setData(rule);
//        if (!CollectionUtils.isEmpty(errorMap)) {
//            errorMsg.setErrorMap(errorMap);
//        }
//        return errorMsg;
//    }
}



