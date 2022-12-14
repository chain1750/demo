package com.chaincat.demo.common.data;

import com.chaincat.demo.common.exception.IError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * 返回结果
 *
 * @author Chain
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /**
     * 结果码
     */
    private Integer code;

    /**
     * 结果信息
     */
    private String msg;

    /**
     * 结果数据
     */
    private T data;

    public static <T> Result<T> ok(T data) {
        return new Result<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    public static Result<Void> ok() {
        return new Result<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
    }

    public static Result<Void> fail(IError error) {
        return new Result<>(error.getCode(), error.getMsg(), null);
    }
}
