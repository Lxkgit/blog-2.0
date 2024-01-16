package com.blog.file.exception;

import com.blog.common.exception.ValidException;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;

/**
 * @description:
 * @Author: 308501
 * @date 2024/1/12 14:20
 */

public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

//    @ResponseBody
//    @ExceptionHandler({BusinessException.class})
//    public Result businessExceptionHandle(HttpServletRequest request, BusinessException e) {
//        Result restResult = this.createResultByException(e);
//        this.externalHandler(restResult, e);
//        logger.error(MessageFormat.format("Global exception information {0}", e.getMessage()), e);
//        return restResult;
//    }

//    @ResponseBody
//    @ExceptionHandler({SystemException.class})
//    public RestResult systemExceptionHandle(HttpServletRequest request, SystemException e) {
//        RestResult restResult = this.createResultByException(e);
//        this.externalHandler(restResult, e);
//        logger.error(MessageFormat.format("Global exception information {0}", e.getMessage()), e);
//        return restResult;
//    }

    @ResponseBody
    @ExceptionHandler({ValidException.class})
    public Result validExceptionHandle(HttpServletRequest request, ValidException e) {
        Result restResult = this.createResultByException(e);
        this.externalHandler(restResult, e);
        logger.error(MessageFormat.format("Global exception information {0}", e.getMessage()), e);
        return restResult;
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Result BeanPropertyBindingResult(HttpServletRequest request, BindException e) {
        Result restResult = this.createResultByException(e);
        this.externalHandler(restResult, e);
        logger.error(MessageFormat.format("Global exception information {0}", e.getMessage()), e);
        return restResult;
    }

    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result methodArgumentNotValidExceptionHandle(HttpServletRequest request, MethodArgumentNotValidException e) {
        Result restResult = ResultFactory.buildFailResult("1");
//        List<ObjectError> list = e.getBindingResult().getAllErrors();
//        if (!CollectionUtils.isEmpty(list)) {
//            String defaultMessage = ((ObjectError)list.get(list.size() - 1)).getDefaultMessage();
//            if (StringUtils.isNumeric(defaultMessage)) {
//                restResult.setCode(((ObjectError)list.get(list.size() - 1)).getDefaultMessage());
//            } else {
//                restResult.setErrMsg(((ObjectError)list.get(list.size() - 1)).getDefaultMessage());
//            }
//        }

        this.externalHandler(restResult, e);
        logger.error(MessageFormat.format("Global exception information {0}", e.getMessage()), e);
        return restResult;
    }

//    @ResponseBody
//    @ExceptionHandler({UndeclaredThrowableException.class})
//    public void undeclaredThrowableExceptionHandle(HttpServletRequest request, UndeclaredThrowableException e) throws Throwable {
//        if (e != null && e.getUndeclaredThrowable() != null) {
//            Throwable throwable = e.getUndeclaredThrowable();
//            if (throwable instanceof BusinessException) {
//                this.businessExceptionHandle(request, (BusinessException)throwable);
//            } else if (throwable instanceof ValidException) {
//                this.validExceptionHandle(request, (ValidException)throwable);
//            } else if (throwable instanceof SystemException) {
//                this.systemExceptionHandle(request, (SystemException)throwable);
//            }
//        }
//
//    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Result defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        return this.errorHandler(request, e);
    }

    public Result errorHandler(HttpServletRequest request, Exception e) throws Exception {
        String code = "200";
        String errorMsg = "unknown exception";
//        IErrorCode globalErrorCode = null;
//        if (e instanceof DataAccessException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_DB_OPT_ERROR;
//        } else if (e instanceof NullPointerException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_NULL_POINT;
//        } else if (e instanceof IOException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_IO_ERROR;
//        } else if (e instanceof ArithmeticException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_ARITHMETIC_ERROR;
//        } else if (e instanceof ArrayIndexOutOfBoundsException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_OUT_OF_BOUNDS;
//        } else if (e instanceof IllegalArgumentException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_ILLEGAL_ARGUMENT;
//        } else if (e instanceof ClassCastException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_CLASS_CAST_ERROR;
//        } else if (e instanceof SecurityException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_ILLEGAL_SECURITY;
//        } else if (e instanceof SQLException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_SQL_ERROR;
//        } else if (e instanceof MissingServletRequestParameterException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_TYPE_MISS_PARAMETER;
//        } else if (e instanceof TypeMismatchException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_TYPE_MISS_MATCH;
//        } else if (e instanceof HttpMessageNotReadableException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_TYPE_MISS_MATCH;
//        } else if (e instanceof HttpMediaTypeNotSupportedException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_CONTENT_TYPE_NOT_SUPPORTED;
//        } else if (e instanceof HttpClientErrorException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_HTTP_CLIENT_ERROR;
//        } else if (e instanceof RuntimeException) {
//            globalErrorCode = BaseSpringErrorCode.COMMON_UNKNOWN_RUNTIME_ERROR;
//        } else {
//            globalErrorCode = BaseSpringErrorCode.COMMON_UNKNOWN_ERROR;
//        }

//        if (globalErrorCode != null) {
//            code = globalErrorCode.getCode();
//            errorMsg = globalErrorCode.getI18nMessage(new Object[0]);
//        }

        Result restResult = ResultFactory.buildFailResult("");
        restResult.setCode(code);
        this.externalHandler(restResult, e);
        logger.error(MessageFormat.format("Global exception information {0}", e.getMessage()), e);
        return restResult;
    }

    public void externalHandler(Result restResult, Exception e) {
    }

    private Result createResultByException(Exception e) {
        Result restResult = null;
        if (e.getMessage() != null) {
            restResult = ResultFactory.buildFailResult(e.getMessage());
        } else {
            restResult = ResultFactory.buildFailResult(e.getMessage());
        }

        return restResult;
    }
}
