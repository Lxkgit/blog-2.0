package com.blog.file.aop;

/**
 * @description:
 * @Author: 308501
 * @date 2024/1/12 14:04
 */

import com.blog.common.exception.ValidException;
import com.google.common.collect.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 默认参数校验子类
 *
 * 使用者可以根据实际业务场景定义新的参数校验子类,必须继承ParamValidate类
 *
 * @author 253693
 * @date 2022年4月14日
 *
 */
@Component
public class DefaultParamValidate extends ParamValidate {

    @Override
    public void validate(Object validateObj) throws ValidException {
        super.validate(validateObj);
    }
}
