package com.jdd.sample.wb.module.user;

import java.util.Date;

/**
 * @author lc. 2018-03-16 15:53
 * @since 1.0.0
 */

public class User {

    private String userId;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String avatar;
    private String accessToken;
    private String refreshToken;
    private long expiresTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("user[")
                .append("userId=").append(userId).append("\n")
                .append("name=").append(name).append("\n")
                .append("nickName=").append(nickName).append("\n")
                .append("phoneNumber=").append(phoneNumber).append("\n")
                .append("avatar=").append(avatar).append("\n")
                .append("accessToken=").append(accessToken).append("\n")
                .append("refreshToken=").append(refreshToken).append("\n")
                .append("expiresTime=").append(new Date(expiresTime).toLocaleString())
                .append("]");
        return sb.toString();
    }
}
