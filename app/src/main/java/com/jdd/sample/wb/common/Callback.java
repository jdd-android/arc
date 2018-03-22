package com.jdd.sample.wb.common;

/**
 * @author lc. 2018-03-16 18:39
 * @since 1.0.0
 */

public interface Callback<TData, TError> {
    /**
     * 成功回调
     */
    void success(TData data);

    /**
     * 失败回调
     */
    void failure(TError error);
}
