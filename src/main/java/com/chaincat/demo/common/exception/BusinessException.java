package com.chaincat.demo.common.exception;

import lombok.Getter;

/**
 * 业务异常
 * @author Chain
 */
@Getter
public class BusinessException extends RuntimeException {

    private final IError error;

    public BusinessException(IError error) {
        super(error.getMsg());
        this.error = error;
    }
}
