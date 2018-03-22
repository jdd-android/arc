package com.jdd.sample.wb.module.home;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.jdd.sample.wb.api.WeiboApiClient;
import com.jdd.sample.wb.api.bean.PostItem;
import com.jdd.sample.wb.api.bean.PostList;
import com.jdd.sample.wb.common.Error;
import com.jdd.sample.wb.common.IError;
import com.jdd.sample.wb.common.NonThrowError;
import com.jdd.sample.wb.common.Result;
import com.jdd.sample.wb.common.SimpleCallback;
import com.jdd.sample.wb.module.user.AuthManager;
import com.jdd.sample.wb.module.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lc. 2018-03-20 18:46
 * @since 1.0.0
 *
 * 数据中心，请求数据，缓存数据，数据变化通知
 */

class HomeRepository {

    /** 当前加载页 */
    private int mCurrentPage = 1;

    private Context mContext;

    private MutableLiveData<Boolean> mObservableIsRefreshing;
    private MutableLiveData<Boolean> mObservableIsLoadingMore;
    private MutableLiveData<User> mObservableUser;
    private MutableLiveData<List<PostItem>> mObservableWeiboList;
    private MutableLiveData<IError> mObservableError;

    HomeRepository(Context context) {
        mContext = context;

        mObservableIsRefreshing = new MutableLiveData<>();
        mObservableIsLoadingMore = new MutableLiveData<>();
        mObservableUser = AuthManager.getInstance(context).observableUser();
        mObservableWeiboList = new MutableLiveData<>();
        mObservableError = new MutableLiveData<>();

        mObservableIsRefreshing.setValue(false);
        mObservableIsLoadingMore.setValue(false);
    }

    MutableLiveData<List<PostItem>> observableWeiboList() {
        return mObservableWeiboList;
    }

    MutableLiveData<IError> observableError() {
        return mObservableError;
    }

    MutableLiveData<Boolean> observableIsRefreshing() {
        return mObservableIsRefreshing;
    }

    MutableLiveData<Boolean> observableIsLoadingMore() {
        return mObservableIsLoadingMore;
    }

    MutableLiveData<User> observableUser() {
        return mObservableUser;
    }

    /**
     * 刷新数据（下拉刷新触发）
     */
    void refreshData() {
        AuthManager authManager = AuthManager.getInstance(mContext);
        if (!authManager.isAuthSuccess()) {
            mObservableError.setValue(new NonThrowError(Error.ERR_AUTH_FAILED, "未登录"));
            return;
        }

        // 设置正在刷新状态
        mObservableIsRefreshing.setValue(true);

        User user = authManager.currentUser();
        String accessToken = user.getAccessToken();
        long sinceId = WeiboApiClient.PARAM_NOT_SET;
        long maxId = WeiboApiClient.PARAM_NOT_SET;
        long count = 10;
        long page = 1;
        long feature = WeiboApiClient.PARAM_NOT_SET;
        long trimUser = WeiboApiClient.PARAM_NOT_SET;
        // 向接口请求数据
        WeiboApiClient.instance().requestFriendsTimeLine(accessToken, sinceId, maxId, count, page, feature, trimUser, new SimpleCallback<Result<PostList, IError>>() {
            @Override
            public void onReturn(Result<PostList, IError> result) {
                if (result.isOk()) {
                    mCurrentPage = 1;
                    // 数据请求成功，设置最新数据
                    mObservableWeiboList.setValue(result.data().getList());
                } else {
                    // 数据请求失败，设置错误信息
                    mObservableError.setValue(result.error());
                }
                // 设置刷新结束
                mObservableIsRefreshing.setValue(false);
            }
        });
    }

    /**
     * 请求下一页数据
     */
    void requestNextPage() {
        AuthManager authManager = AuthManager.getInstance(mContext);
        if (!authManager.isAuthSuccess()) {
            mObservableError.setValue(new NonThrowError(Error.ERR_AUTH_FAILED, "未登录"));
            return;
        }

        // 设置正在上拉加载状态
        mObservableIsLoadingMore.setValue(true);

        User user = authManager.currentUser();
        String accessToken = user.getAccessToken();
        long sinceId = WeiboApiClient.PARAM_NOT_SET;
        long maxId = WeiboApiClient.PARAM_NOT_SET;
        long count = 10;
        long page = mCurrentPage + 1;
        long feature = WeiboApiClient.PARAM_NOT_SET;
        long trimUser = WeiboApiClient.PARAM_NOT_SET;
        // 向接口请求数据
        WeiboApiClient.instance().requestFriendsTimeLine(accessToken, sinceId, maxId, count, page, feature, trimUser, new SimpleCallback<Result<PostList, IError>>() {

            @Override
            public void onReturn(Result<PostList, IError> result) {
                mObservableIsLoadingMore.setValue(false);
                if (result.isOk()) {
                    // 没有下一页
                    if (result.data().getList().isEmpty()) {
                        mObservableError.setValue(new NonThrowError(Error.ERR_COMMON, "没有更多数据"));
                        return;
                    }

                    mCurrentPage++;
                    List<PostItem> weiboList = mObservableWeiboList.getValue();
                    if (weiboList == null) {
                        weiboList = new ArrayList<>();
                    }
                    weiboList.addAll(result.data().getList());
                    // 设置最新数据
                    mObservableWeiboList.setValue(weiboList);
                } else {
                    // 数据请求失败，设置错误信息
                    mObservableError.setValue(result.error());
                }
            }
        });
    }
}
