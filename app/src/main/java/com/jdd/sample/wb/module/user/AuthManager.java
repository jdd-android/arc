package com.jdd.sample.wb.module.user;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.jdd.sample.wb.api.WeiboApiClient;
import com.jdd.sample.wb.api.bean.UserInfo;
import com.jdd.sample.wb.common.IError;
import com.jdd.sample.wb.common.Result;
import com.jdd.sample.wb.common.SimpleCallback;
import com.jdd.sample.wb.eventbus.EventUserInfoRefresh;
import com.jdd.sample.wb.persistence.preference.UserPreference;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.greenrobot.eventbus.EventBus;

/**
 * @author lc. 2018-03-16 16:01
 * @since 1.0.0
 */

public class AuthManager {

    private static volatile AuthManager sInstance;

    private Context mContext;

    private MutableLiveData<User> mObservableUser;
    private User mUser;

    private AuthManager(Context context) {
        mContext = context.getApplicationContext();
        mUser = UserPreference.retrieve(context);
        mObservableUser = new MutableLiveData<>();
        mObservableUser.setValue(mUser);
    }

    public static AuthManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AuthManager.class) {
                if (sInstance == null) {
                    sInstance = new AuthManager(context);
                }
            }
        }
        return sInstance;
    }

    public User currentUser() {
        return mUser;
    }

    public MutableLiveData<User> observableUser() {
        return mObservableUser;
    }

    public boolean isAuthSuccess() {
        return !TextUtils.isEmpty(mUser.getAccessToken());
    }

    public void startAuth(Activity activity) {
        activity.startActivity(new Intent(activity, WeiBoAuthActivity.class));
    }

    void updateAuthInfo(Oauth2AccessToken accessToken) {
        mUser.setUserId(accessToken.getUid());
        mUser.setPhoneNumber(accessToken.getPhoneNum());
        mUser.setAccessToken(accessToken.getToken());
        mUser.setRefreshToken(accessToken.getRefreshToken());
        mUser.setExpiresTime(accessToken.getExpiresTime());

        UserPreference.save(mContext, mUser);
        mObservableUser.setValue(mUser);
    }

    public void refreshUserInfo(final long uid, String accessToken) {
        WeiboApiClient.instance().requestUserInfo(uid, accessToken, new SimpleCallback<Result<UserInfo, IError>>() {
            @Override
            public void onReturn(Result<UserInfo, IError> result) {
                if (result.isOk()) {
                    UserInfo userInfo = result.data();
                    mUser.setUserId(String.valueOf(userInfo.getId()));
                    mUser.setName(userInfo.getName());
                    mUser.setNickName(userInfo.getNickName());
                    mUser.setAvatar(userInfo.getAvatar());

                    mObservableUser.setValue(mUser);

                    EventBus.getDefault().post(new EventUserInfoRefresh(true));
                } else {
                    EventBus.getDefault().post(new EventUserInfoRefresh(false));
                }
            }
        });
    }

}
