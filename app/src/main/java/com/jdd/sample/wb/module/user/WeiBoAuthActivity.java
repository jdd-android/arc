package com.jdd.sample.wb.module.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jdd.sample.wb.common.utils.CommonUtils;
import com.jdd.sample.wb.eventbus.EventAuthReturn;
import com.jdd.sample.wb.module.base.BaseActivity;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import org.greenrobot.eventbus.EventBus;

/**
 * @author lc. 2018-03-16 16:09
 * @since 1.0.0
 */

public class WeiBoAuthActivity extends BaseActivity implements WbAuthListener {

    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSsoHandler = new SsoHandler(this);
        mSsoHandler.authorizeWeb(this);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 微博登录结果回调
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
        AuthManager.getInstance(this).updateAuthInfo(oauth2AccessToken);
        EventBus.getDefault().postSticky(new EventAuthReturn(EventAuthReturn.STATE_SUCCESS));

        // 请求用户信息
        AuthManager.getInstance(this).refreshUserInfo(
                CommonUtils.str2long(oauth2AccessToken.getUid()), oauth2AccessToken.getToken());

        finish();
    }

    @Override
    public void cancel() {
        EventBus.getDefault().post(new EventAuthReturn(EventAuthReturn.STATE_CANCEL));

        finish();
    }

    @Override
    public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
        EventAuthReturn eventAuthReturn = new EventAuthReturn(EventAuthReturn.STATE_CANCEL);
        eventAuthReturn.setFailError(wbConnectErrorMessage);
        EventBus.getDefault().post(eventAuthReturn);

        finish();
    }
}
