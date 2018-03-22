package com.jdd.sample.wb.api;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.jdd.sample.wb.api.bean.UserInfo;
import com.jdd.sample.wb.api.bean.PostList;
import com.jdd.sample.wb.common.IError;
import com.jdd.sample.wb.common.NonThrowError;
import com.jdd.sample.wb.common.Result;
import com.jdd.sample.wb.common.SimpleCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * @author lc. 2018-03-19 09:27
 * @since 1.0.0
 */

public class WeiboApiClient {

    public static final long PARAM_NOT_SET = -110;

    private static final String WEIBO_BASE_URL = "https://api.weibo.com/2/";

    private static WeiboApiClient sInstance;

    private WeiboApiService mApiService;

    public WeiboApiClient() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WEIBO_BASE_URL)
                .client(client)
                .addConverterFactory(FastJsonConverterFactory.create().setSerializeConfig(new SerializeConfig()))
                .build();

        mApiService = retrofit.create(WeiboApiService.class);
    }

    public static WeiboApiClient instance() {
        if (sInstance == null) {
            sInstance = new WeiboApiClient();
        }
        return sInstance;
    }

    public void requestUserInfo(long uid, String accessToken, final SimpleCallback<Result<UserInfo, IError>> callback) {
        final Result<UserInfo, IError> result = new Result<>();
        Call<UserInfo> userInfoCall = mApiService.requestUserInfo(uid, accessToken);
        userInfoCall.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    result.setData(response.body());
                } else {
                    IError error = new NonThrowError(response.code(), response.message());
                    result.setError(error);
                }
                callback.onReturn(result);
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                IError error = new NonThrowError(-1, t.getMessage());
                result.setError(error);
                callback.onReturn(result);
            }
        });
    }

    public void requestPublicTimeLine(String accessToken,
                                      long count,
                                      long page,
                                      SimpleCallback<Result<PostList, IError>> callback) {
        Call<PostList> call = mApiService.requestPublicTimeLine(accessToken, count, page);
        executeWeiboListRequest(call, callback);
    }

    public void requestFriendsTimeLine(String accessToken,
                                       long sinceId,
                                       long maxId,
                                       long count,
                                       long page,
                                       long feature,
                                       long trimUser,
                                       SimpleCallback<Result<PostList, IError>> callback) {

        Map<String, Long> params = new HashMap<>();
        if (sinceId != PARAM_NOT_SET) {
            params.put("since_id", sinceId);
        }
        if (maxId != PARAM_NOT_SET) {
            params.put("max_id", maxId);
        }
        if (count != PARAM_NOT_SET) {
            params.put("count", count);
        }
        if (page != PARAM_NOT_SET) {
            params.put("page", page);
        }
        if (feature != PARAM_NOT_SET) {
            params.put("feature", feature);
        }
        if (trimUser != PARAM_NOT_SET) {
            params.put("trim_user", trimUser);
        }
        Call<PostList> call = mApiService.requestFriendTimeLine(accessToken, params);
        executeWeiboListRequest(call, callback);
    }

    private void executeWeiboListRequest(Call<PostList> call, final SimpleCallback<Result<PostList, IError>> callback) {
        final Result<PostList, IError> result = new Result<>();
        call.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                if (response.isSuccessful()) {
                    result.setData(response.body());
                } else {
                    IError error = new NonThrowError(response.code(), response.message());
                    result.setError(error);
                }
                callback.onReturn(result);
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                IError error = new NonThrowError(-1, t.getMessage());
                result.setError(error);
                callback.onReturn(result);
            }
        });
    }

    class LoggingInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            Log.i("OkHttp", "request:" + request.toString());
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            Log.i("OkHttp", String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            if (!TextUtils.isEmpty(content) && content.length() > 2000) {
                for (int i = 0; i < content.length(); i += 2000) {
                    Log.i("OkHttp", "response body:" + content.substring(i, i + 2000 > content.length() ? content.length() : (i + 2000)));
                }
            } else {
                Log.i("OkHttp", "response body:" + content);
            }

            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }

}
