package com.blog.common.valication.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.List;


/**
 * 比较大小
 * @author 118905
 * @since 2020-03-04
 */
public class ComparePropValidator implements ConstraintValidator<ComapareProp, Object> {
    private static ConstraintValidatorContext constraintValidatorContext;

    @Override
    public void initialize(ComapareProp constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        constraintValidatorContext = context;
        return true;
    }

    public ConstraintValidatorContext valid(Object value, List<Object> comparePropValList) {
        for(Object comparePropVal : comparePropValList) {
            if (comparePropVal !=null && ((BigDecimal)value).compareTo((BigDecimal) comparePropVal) >=0) {
                return constraintValidatorContext.buildConstraintViolationWithTemplate("该属性的值必须小于要比较的属性").addConstraintViolation();
            }
        }

        return null;
    }

}
