package com.blog.file.aop;

import com.blog.common.exception.ValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 参数校验父类
 * 
 * @author 253693
 * @date 2022年4月14日
 *
 */
public class ParamValidate {
    private static final Logger logger = LoggerFactory.getLogger(ParamValidateManager.class);
    
    // 校验类型
    protected ThreadLocal<Class<?>> type = new ThreadLocal<Class<?>>();
    
    public void setType(Class<?> type) {
        this.type.set(type);
    }
    
    public Class<?> getType() {
        return type.get();
    }
    
    public void removeType() {
        type.remove();
    }
    
    
    /**
     * 参数校验
     * 所有子类应重载该方法
     * @param validateObj
     * @throws ValidException
     * 
     */
    public void validate(Object validateObj) throws ValidException {
      
    }

    /**
     * 输出校验不通过错误信息
     * 
     * @param msg
     */
    protected void printErrorMsg(String msg) throws ValidException {
        logger.error("参数校验不通过:{}", msg);
        throw new ValidException("1", msg);
    }
    
//    protected void printErrorMsg(IsmsErrorCode errorCode) throws ValidException {
//        throw new ValidException(errorCode);
//    }
}
