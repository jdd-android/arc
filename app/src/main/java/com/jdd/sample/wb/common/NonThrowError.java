package com.jdd.sample.wb.common;

/**
 * @author lc. 2018-03-19 10:00
 * @since 1.0.0
 */

public class NonThrowError implements IError {
    private int code;
    private String msg;

    public NonThrowError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String msg() {
        return this.msg;
    }

    @Override
    public String toString() {
        return "[code=" + code + " msg=" + msg + "]";
    }
}
