package com.jdd.sample.wb.persistence.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.jdd.sample.wb.module.user.User;

/**
 * @author lc. 2018-03-16 15:48
 * @since 1.0.0
 *
 * 用户信息存储类
 */

public class UserPreference {

    private static final String FILE_NAME = "user-preference";

    private static final String PRE_NAME = "name";
    private static final String PRE_NICK_NAME = "nick_name";
    private static final String PRE_AVATAR = "avatar";
    private static final String PRE_ID = "user_id";
    private static final String PRE_PHONE_NUM = "user_phone_number";
    private static final String PRE_ACCESS_TOKEN = "access_token";
    private static final String PRE_REFRESH_TOKEN = "refresh_token";
    private static final String PRE_TOKEN_EXPIRES_TIME = "token_expires_time";

    public static void save(Context context, User user) {
        getPreference(context).edit()
                .putString(PRE_NAME, user.getName())
                .putString(PRE_NICK_NAME, user.getNickName())
                .putString(PRE_AVATAR, user.getAvatar())
                .putString(PRE_ACCESS_TOKEN, user.getAccessToken())
                .putString(PRE_REFRESH_TOKEN, user.getRefreshToken())
                .putString(PRE_PHONE_NUM, user.getPhoneNumber())
                .putString(PRE_ID, user.getUserId())
                .putLong(PRE_TOKEN_EXPIRES_TIME, user.getExpiresTime())
                .apply();
    }

    public static User retrieve(Context context) {
        User user = new User();
        restore(context, user);
        return user;
    }

    public static void restore(Context context, User user) {
        SharedPreferences preference = getPreference(context);
        user.setUserId(preference.getString(PRE_ID, ""));
        user.setName(preference.getString(PRE_NAME, ""));
        user.setNickName(preference.getString(PRE_NICK_NAME, ""));
        user.setNickName(preference.getString(PRE_PHONE_NUM, ""));
        user.setAvatar(preference.getString(PRE_AVATAR, ""));
        user.setAccessToken(preference.getString(PRE_ACCESS_TOKEN, ""));
        user.setRefreshToken(preference.getString(PRE_REFRESH_TOKEN, ""));
        user.setExpiresTime(preference.getLong(PRE_TOKEN_EXPIRES_TIME, 0));
    }

    private static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }
}
