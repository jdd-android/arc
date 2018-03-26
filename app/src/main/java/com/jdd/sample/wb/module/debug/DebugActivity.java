package com.jdd.sample.wb.module.debug;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.Handler;

import com.jdd.sample.wb.R;
import com.jdd.sample.wb.databinding.ActivityDebugBinding;
import com.jdd.sample.wb.module.base.BaseActivity;

/**
 * @author lc
 */
public class DebugActivity extends BaseActivity {

    private int mCounter = 0;
    private ObservableField<String> mObservableTime;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mCounter++;
            mObservableTime.set(String.valueOf(mCounter));
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDebugBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_debug);

        mObservableTime = new ObservableField<>(String.valueOf(mCounter));
        binding.setTime(mObservableTime);
        mHandler.post(mRunnable);
    }
}
