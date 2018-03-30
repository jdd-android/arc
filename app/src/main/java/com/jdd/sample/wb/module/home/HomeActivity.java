package com.jdd.sample.wb.module.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jdd.sample.wb.R;
import com.jdd.sample.wb.common.utils.CommonUtils;
import com.jdd.sample.wb.databinding.ActivityMainBinding;
import com.jdd.sample.wb.eventbus.EventAuthReturn;
import com.jdd.sample.wb.eventbus.EventUserInfoRefresh;
import com.jdd.sample.wb.module.base.BaseActivity;
import com.jdd.sample.wb.module.common.ImageDisplayActivity;
import com.jdd.sample.wb.module.user.AuthManager;
import com.jdd.sample.wb.module.user.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author lc. 2018-03-16 15:24
 * @since 1.0.0
 */

public class HomeActivity extends BaseActivity {

    private ActivityMainBinding mMainBinding;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setTitle("");

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.fragment_container, FriendTimelineFragment.newInstance()).commit();

        EventBus.getDefault().register(this);

        if (!AuthManager.getInstance(this).isAuthSuccess()) {
            AuthManager.getInstance(this).startAuth(this);
            return;
        }

        AuthManager authManager = AuthManager.getInstance(this);
        User user = authManager.currentUser();
        AuthManager.getInstance(this).refreshUserInfo(
                CommonUtils.str2long(user.getUserId()), user.getAccessToken());

        mMainBinding.setUser(user);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_debug:
                startActivity(new Intent(this, ImageDisplayActivity.class));
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    ///////////////////////////////////////////////////////////////////////////
    // EventBus 回调
    ///////////////////////////////////////////////////////////////////////////

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventAuthCallback(EventAuthReturn event) {
        if (event.isSuccess()) {

        }
        if (event.isCancel()) {
            Toast.makeText(this, "auth cancel", Toast.LENGTH_SHORT).show();
        }
        if (event.isFail()) {
            Toast.makeText(this, "auth fail=" + event.getFailError().getErrorCode() + "  " + event.getFailError().getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventUserInfoRefresh(EventUserInfoRefresh event) {
        if (event.isRefreshSuccess()) {
            mMainBinding.setUser(AuthManager.getInstance(this).currentUser());
        } else {
            Toast.makeText(this, "refresh user failed", Toast.LENGTH_SHORT).show();
        }
    }

}
