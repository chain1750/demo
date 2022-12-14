package com.chaincat.demo.common.security;

/**
 * 经过Token解析后保存的用户信息
 * @author Chain
 */
public class AccessContext {

    /**
     * 未做鉴权部分，先默认定义ID为123456
     */
    private static final ThreadLocal<Long> INSTANCE = ThreadLocal.withInitial(() -> 123456L);

    public static Long get() {
        return INSTANCE.get();
    }

    public static void set(Long userId) {
        INSTANCE.set(userId);
    }

    public static void clear() {
        INSTANCE.remove();
    }
}
