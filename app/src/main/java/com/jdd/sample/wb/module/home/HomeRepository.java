package com.jdd.sample.wb.module.home;

import com.jdd.sample.wb.api.WeiboApiClient;
import com.jdd.sample.wb.api.bean.PostItem;
import com.jdd.sample.wb.api.bean.PostList;
import com.jdd.sample.wb.common.Callback;
import com.jdd.sample.wb.common.Error;
import com.jdd.sample.wb.common.IError;
import com.jdd.sample.wb.common.NonThrowError;
import com.jdd.sample.wb.common.Result;
import com.jdd.sample.wb.common.SimpleCallback;

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

    /**
     * 刷新数据
     */
    void refreshData(String accessToken, final Callback<List<PostItem>, IError> callback) {
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
                    callback.success(result.data().getList());
                } else {
                    callback.failure(result.error());
                }
            }
        });
    }

    /**
     * 请求下一页数据
     */
    void requestNextPage(String accessToken, final Callback<List<PostItem>, IError> callback) {

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

                if (result.isOk()) {
                    // 没有下一页
                    if (result.data().getList().isEmpty()) {
                        callback.failure(new NonThrowError(Error.ERR_COMMON, "没有更多数据"));
                        return;
                    }

                    mCurrentPage++;
                    callback.success(result.data().getList());
                } else {
                    callback.failure(result.error());
                }
            }
        });
    }
}
