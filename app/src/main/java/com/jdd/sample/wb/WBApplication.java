package com.jdd.sample.wb;

import android.app.Application;

import com.jdd.sample.wb.common.Constants;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;

/**
 * @author lc. 2018-03-16 14:17
 * @since 1.0.0
 */

public class WBApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化微博 SDK
        AuthInfo authInfo = new AuthInfo(this, Constants.WB_APP_KEY, Constants.WB_REDIRECT_URL, Constants.WB_SCOPE);
        WbSdk.install(this, authInfo);
    }
}
