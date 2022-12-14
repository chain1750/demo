package com.chaincat.demo.common.exception;

/**
 * 错误信息接口
 * @author Chain
 */
public interface IError {

    /**
     * 获取错误码
     * @return 错误码
     */
    Integer getCode();

    /**
     * 获取错误信息
     * @return 错误信息
     */
    String getMsg();
}
