package com.blog.file.exception;

import com.blog.common.exception.ValidException;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 类名称：CustomExceptionHandler.java 类描述：全局异常处理类,包括但不限于：校验contoller参数，业务运行异常
 * @author 27919
 * @date   2020年4月20日
 */
@ControllerAdvice
public class CustomExceptionHandler extends GlobalExceptionHandler {

	private final static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @Override
    public void externalHandler(Result restResult, Exception e) {
        super.externalHandler(restResult, e);
    }

	@ResponseBody
    @ExceptionHandler(ValidException.class)
	@Override
    public Result validExceptionHandle(HttpServletRequest request, ValidException e) {
		Result restResult = null;
    	// 这里将异常参数放到 RestResult 中的 data 属性中有一个优先级：e.data > e.args
    	if (e.getData() != null) {
    		restResult = ResultFactory.buildFailResult(e.getMessage());
		} else {
			restResult = ResultFactory.buildFailResult(e.getMessage());
		}
    	logger.error("Http url={} valid params error {}",request.getRequestURI(), e.getMessage(), e);
    	return restResult;
    }

	@ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
	@Override
    public Result methodArgumentNotValidExceptionHandle(HttpServletRequest request, MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
		Result restResult = ResultFactory.buildFailResult(allErrors.get(0).getDefaultMessage());
		logger.error("Http url={} valid params error {}",request.getRequestURI(), e.getMessage(), e);
    	return restResult;
    }


}
