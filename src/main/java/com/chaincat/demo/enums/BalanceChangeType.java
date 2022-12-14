package com.chaincat.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 余额变动类型
 * @author Chain
 */
@Getter
@AllArgsConstructor
public enum BalanceChangeType {

    /**
     * 消费
     */
    CONSUME(1),

    /**
     * 退款
     */
    REFUND(0),
    ;

    private final Integer type;

    public static BalanceChangeType get(Integer type) {
        BalanceChangeType[] types = BalanceChangeType.values();
        for (BalanceChangeType e : types) {
            if (e.getType().equals(type)) {
                return e;
            }
        }
        throw new IllegalArgumentException("余额变动类型不存在");
    }
}
