package com.jdd.sample.wb.common;

/**
 * @author lc. 2018-03-19 09:59
 * @since 1.0.0
 */

public interface IError {
    /**
     * 返回错误码
     *
     * @return 返回错误码
     */
    int code();

    /**
     * 返回错误信息
     *
     * @return 返回错误信息
     */
    String msg();
}
