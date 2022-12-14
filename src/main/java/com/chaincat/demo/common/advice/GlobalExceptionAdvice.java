package com.chaincat.demo.common.advice;

import com.chaincat.demo.common.data.Result;
import com.chaincat.demo.common.exception.BusinessException;
import com.chaincat.demo.common.exception.IError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理
 * @author Chain
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        List<ObjectError> errorList = e.getAllErrors();
        errorList.forEach(error -> log.warn("处理参数校验异常：{}", error.getDefaultMessage()));
        return Result.fail(new IError() {
            @Override
            public Integer getCode() {
                return HttpStatus.BAD_REQUEST.value();
            }

            @Override
            public String getMsg() {
                return errorList.get(0).getDefaultMessage();
            }
        });
    }

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        IError error = e.getError();
        log.warn("处理业务异常：[{}, {}]", error.getCode(), error.getMsg());
        return Result.fail(error);
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("处理全局异常：{}", e.getMessage(), e);
        return Result.fail(new IError() {
            @Override
            public Integer getCode() {
                return HttpStatus.INTERNAL_SERVER_ERROR.value();
            }

            @Override
            public String getMsg() {
                return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            }
        });
    }
}
