package com.chaincat.demo.enums.error;

import com.chaincat.demo.common.exception.IError;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 钱包业务异常枚举
 * @author Chain
 */
@Getter
@AllArgsConstructor
public enum WalletError implements IError {

    /**
     * 钱包不存在
     */
    WALLET_NOT_FOUND(100001, "钱包不存在"),
    BALANCE_NOT_ENOUGH(100002, "余额不足"),
    ;

    private final Integer code;

    private final String msg;
}
