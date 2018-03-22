package com.jdd.sample.wb.common;

/**
 * @author lc. 2018-03-16 18:41
 * @since 1.0.0
 */

public interface SimpleCallback<T> {
    /**
     * 回调
     */
    void onReturn(T t);
}
