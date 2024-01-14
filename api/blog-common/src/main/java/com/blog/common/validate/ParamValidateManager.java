//package com.blog.common.validate;
//
//
//import com.blog.common.exception.ValidException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * 参数校验管理器
// *
// * @author 253693
// * @date 2022年4月14日
// *
// */
//@Slf4j
//@Component
//public class ParamValidateManager {
//
//
//    public <T> void validate(T validateObj, Class<?> type) throws ValidException {
//        /**
//         * 获取ParamValidate类的所有子类，遍历子类的所有validate方法（方法名相同，入参不同），
//         * 当方法入参和validateObj类型相同，则执行该方法
//         */
//        Map<String, ParamValidate> beansOfParamValidate = AppContext.getContext().getBeansOfType(ParamValidate.class);
//
//        /**
//         * 遍历每个bean对象，匹配到方法则执行该方法，然后退出循环
//         */
//        for (ParamValidate paramValidate : beansOfParamValidate.values()) {
//            try {
//                Method method = paramValidate.getClass().getMethod("validate", validateObj.getClass());
//                if (Objects.nonNull(method)) {
//                    if (Objects.nonNull(type)) {
//                        paramValidate.setType(type);
//                    }
//                    method.invoke(paramValidate, validateObj);
//                    break;
//                }
//            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e1) {
//
//            } catch (InvocationTargetException e2) {
//                Throwable cause = e2.getCause();
//                if (cause instanceof ValidException) {
//                    ValidException rename = (ValidException) cause;
//                    throw new ValidException(rename.getCode(), rename.getErrMsg());
//                }
//                log.error("反射异常", e2);
//            } finally {
//                if (Objects.nonNull(paramValidate.getType())) {
//                    paramValidate.removeType();
//                }
//            }
//        }
//    }
//}
