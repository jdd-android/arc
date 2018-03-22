package com.jdd.sample.wb.module.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jdd.sample.wb.R;

/**
 * @author lc. 2018-03-16 14:17
 * @since 1.0.0
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        getToolbar();
    }

    public Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        }
        return mToolbar;
    }

//    protected void setToolbarAsBack(View.OnClickListener clickListener) {
//        getToolbar();
//
//        if (mToolbar == null) {
//            return;
//        }
//
////        mToolbar.setNavigationIcon(R.drawable.ic_back);
////        mToolbar.setNavigationOnClickListener(clickListener);
//    }
//
//    protected void setToolbarAsClose(View.OnClickListener clickListener) {
////        getToolbar();
////
////        if (mToolbar == null) {
////            return;
////        }
////
////        mToolbar.setNavigationIcon(R.drawable.ic_close);
////        mToolbar.setNavigationOnClickListener(clickListener);
//    }
//
//
//    protected int statusBarColor() {
//        return R.color.colorPrimary;
//    }

}
