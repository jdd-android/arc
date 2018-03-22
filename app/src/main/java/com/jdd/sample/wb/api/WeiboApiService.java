package com.jdd.sample.wb.api;

import com.jdd.sample.wb.api.bean.UserInfo;
import com.jdd.sample.wb.api.bean.PostList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author lc. 2018-03-16 18:38
 * @since 1.0.0
 */

public interface WeiboApiService {

    /**
     * 查询用户信息
     *
     * @param uid         用户 ID
     * @param accessToken TOKEN
     */
    @GET("users/show.json")
    Call<UserInfo> requestUserInfo(
            @Query("uid") long uid,
            @Query("access_token") String accessToken);

    @GET("statuses/public_timeline.json")
    Call<PostList> requestPublicTimeLine(
            @Query("access_token") String accessToken,
            @Query("count") long count,
            @Query("page") long page);

    @GET("statuses/friends_timeline.json")
    Call<PostList> requestFriendTimeLine(
            @Query("access_token") String accessToken, @QueryMap Map<String, Long> params);
}
