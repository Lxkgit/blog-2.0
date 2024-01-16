package com.blog.file.exception;

import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.user.SysRole;
import com.blog.common.exception.ValidException;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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
		Result restResult;
		if (e.getData() != null) {
			restResult = ResultFactory.buildFailResult(e.getCode(), e.getMessage(), e.getData());
		} else {
			restResult = ResultFactory.buildFailResult(e.getCode(), e.getMessage());
		}

    	logger.error("Http url={} valid params error {}",request.getRequestURI(), e.getMessage(), e);
    	return restResult;
    }

	@ResponseBody
	@ExceptionHandler(BindException.class)
	@Override
	public Result BeanPropertyBindingResult(HttpServletRequest request, BindException e) {
		Result restResult;
		List<String> errorMsgList =  e.getBindingResult().getAllErrors().parallelStream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
		restResult = ResultFactory.buildFailResult(ErrorMessage.PARAMETER_VERIFICATION_ERROR.getCode(), errorMsgList.toString());
		logger.error("Http url={} valid params error {}",request.getRequestURI(), errorMsgList.toString(), e);
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
