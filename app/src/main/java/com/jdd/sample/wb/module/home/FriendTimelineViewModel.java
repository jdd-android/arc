package com.jdd.sample.wb.module.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.jdd.sample.wb.api.bean.PostItem;
import com.jdd.sample.wb.common.Callback;
import com.jdd.sample.wb.common.Error;
import com.jdd.sample.wb.common.IError;
import com.jdd.sample.wb.common.NonThrowError;
import com.jdd.sample.wb.module.user.AuthManager;
import com.jdd.sample.wb.module.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lc. 2018-03-20 17:23
 * @since 1.0.0
 *
 * 朋友圈微博 ViewModel
 */

public class FriendTimelineViewModel extends AndroidViewModel {

    /** 数据中心，数据请求的实际执行者 */
    private HomeRepository mRepository;

    private MutableLiveData<Boolean> mObservableIsRefreshing;
    private MutableLiveData<Boolean> mObservableIsLoadingMore;
    private MutableLiveData<User> mObservableUser;
    private MutableLiveData<List<PostItem>> mObservableWeiboList;
    private MutableLiveData<IError> mObservableError;

    public FriendTimelineViewModel(Application application) {
        super(application);
        mRepository = new HomeRepository();
        mObservableIsRefreshing = new MutableLiveData<>();
        mObservableIsLoadingMore = new MutableLiveData<>();
        mObservableUser = AuthManager.getInstance(application).observableUser();
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

    void refresh() {
        AuthManager authManager = AuthManager.getInstance(getApplication());
        if (!authManager.isAuthSuccess()) {
            mObservableError.setValue(new NonThrowError(Error.ERR_AUTH_FAILED, "未登录"));
            mObservableIsRefreshing.setValue(false);
            return;
        }

        // 设置正在刷新状态
        mObservableIsRefreshing.setValue(true);

        // 请求数据
        String accessToken = authManager.currentUser().getAccessToken();
        mRepository.refreshData(accessToken, new Callback<List<PostItem>, IError>() {
            @Override
            public void success(List<PostItem> postItems) {
                mObservableWeiboList.setValue(postItems);
                mObservableIsRefreshing.setValue(false);
            }

            @Override
            public void failure(IError error) {
                mObservableError.setValue(error);
                mObservableIsRefreshing.setValue(false);
            }
        });
    }

    void loadMore() {
        AuthManager authManager = AuthManager.getInstance(getApplication());
        if (!authManager.isAuthSuccess()) {
            mObservableError.setValue(new NonThrowError(Error.ERR_AUTH_FAILED, "未登录"));
            mObservableIsLoadingMore.setValue(false);
            return;
        }

        // 设置正在上拉加载状态
        mObservableIsLoadingMore.setValue(true);

        String accessToken = authManager.currentUser().getAccessToken();
        mRepository.requestNextPage(accessToken, new Callback<List<PostItem>, IError>() {
            @Override
            public void success(List<PostItem> postItems) {
                mObservableIsLoadingMore.setValue(false);
                List<PostItem> weiboList = mObservableWeiboList.getValue();
                if (weiboList == null) {
                    weiboList = new ArrayList<>();
                }
                weiboList.addAll(postItems);
                // 设置最新数据
                mObservableWeiboList.setValue(weiboList);
            }

            @Override
            public void failure(IError error) {
                mObservableIsLoadingMore.setValue(false);
                mObservableError.setValue(error);
            }
        });
    }
}
