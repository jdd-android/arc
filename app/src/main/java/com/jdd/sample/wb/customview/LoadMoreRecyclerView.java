package com.jdd.sample.wb.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author lc. 2018-03-21 15:51
 * @since 1.0.0
 */

public class LoadMoreRecyclerView extends RecyclerView {

    private PullActionListener mPullActionListener;

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnScrollListener(new LoadMoreController());
    }

    public void setPullActionListener(PullActionListener listener) {
        mPullActionListener = listener;
    }

    private class LoadMoreController extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                int lastCompletelyVisibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
                if (lastCompletelyVisibleItemPosition == manager.getItemCount() - 1) {
                    if (mPullActionListener != null) {
                        mPullActionListener.onPullUpLoadMore();
                    }
                }
            }
        }
    }

    public interface PullActionListener {
        void onPullUpLoadMore();
    }
}
