package com.jdd.sample.wb.eventbus;

import com.sina.weibo.sdk.auth.WbConnectErrorMessage;

/**
 * @author lc. 2018-03-16 16:42
 * @since 1.0.0
 *
 * 事件 微博登录返回
 */

public class EventAuthReturn {

    public static final int STATE_SUCCESS = 1;
    public static final int STATE_CANCEL = 2;
    public static final int STATE_FAIL = 3;

    private final int mState;

    private WbConnectErrorMessage mFailError;

    public EventAuthReturn(int state){
        mState = state;
    }

    public int getState() {
        return mState;
    }

    public WbConnectErrorMessage getFailError() {
        return mFailError;
    }

    public void setFailError(WbConnectErrorMessage error) {
        this.mFailError = error;
    }

    public boolean isSuccess(){
        return mState == STATE_SUCCESS;
    }

    public boolean isCancel(){
        return mState == STATE_CANCEL;
    }

    public boolean isFail(){
        return mState == STATE_FAIL;
    }
}
