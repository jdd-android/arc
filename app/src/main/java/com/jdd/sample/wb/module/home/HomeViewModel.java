package com.jdd.sample.wb.module.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.jdd.sample.wb.module.user.User;

/**
 * @author lc. 2018-03-21 14:10
 * @since 1.0.0
 */

class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<User> mobservableUser;

    HomeViewModel(Application application) {
        super(application);
    }
}
