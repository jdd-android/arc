package com.jdd.sample.wb.module.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.jdd.sample.wb.api.bean.PostItem;
import com.jdd.sample.wb.common.IError;
import com.jdd.sample.wb.module.user.User;

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
        mRepository = new HomeRepository(application);
        mObservableUser = mRepository.observableUser();
        mObservableIsRefreshing = mRepository.observableIsRefreshing();
        mObservableIsLoadingMore = mRepository.observableIsLoadingMore();
        mObservableWeiboList = mRepository.observableWeiboList();
        mObservableError = mRepository.observableError();
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
        mRepository.refreshData();
    }

    void loadMore(){
        mRepository.requestNextPage();
    }
}
