package com.jdd.sample.wb.module.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jdd.sample.wb.R;
import com.jdd.sample.wb.api.bean.PostItem;
import com.jdd.sample.wb.common.IError;
import com.jdd.sample.wb.common.PostItemOnClickCallback;
import com.jdd.sample.wb.customview.LoadMoreRecyclerView;
import com.jdd.sample.wb.eventbus.EventAuthReturn;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * @author lc. 2018-03-19 16:46
 * @since 1.0.0
 */

public class FriendTimelineFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener,
        LoadMoreRecyclerView.PullActionListener,
        PostItemOnClickCallback {

    private SwipeRefreshLayout mRefreshLy;
    private LoadMoreRecyclerView mRecyclerView;
    private PostRecyclerAdapter mAdapter;

    private FriendTimelineViewModel mViewModel;

    static FriendTimelineFragment newInstance() {
        return new FriendTimelineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friendtimeline, container, false);

        mRefreshLy = rootView.findViewById(R.id.refresh_layout);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);

        mAdapter = new PostRecyclerAdapter(getActivity());
        mAdapter.setPostOnClickCallback(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setPullActionListener(this);

        mRefreshLy.setOnRefreshListener(this);

        // 创建 ViewModel
        mViewModel = ViewModelProviders.of(this).get(FriendTimelineViewModel.class);
        // 监听 ViewModel 数据变化
        subscribeUi(mViewModel);
        // 刷新数据
        mViewModel.refresh();

        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    ///////////////////////////////////////////////////////////////////////////
    // event bus
    ///////////////////////////////////////////////////////////////////////////

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventAuthCallback(EventAuthReturn event) {
        if (event.isSuccess()) {
            mViewModel.refresh();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // callback
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onRefresh() {
        mViewModel.refresh();
    }

    @Override
    public void onPullUpLoadMore() {
        mViewModel.loadMore();
    }

    @Override
    public void onPostClick(PostItem postItem) {
        Toast.makeText(getContext(), "CLICK " + postItem.getId(), Toast.LENGTH_SHORT).show();
    }

    private void subscribeUi(FriendTimelineViewModel viewModel) {
        // 监听刷新状态变化 更新 SwipeRefreshLayout
        viewModel.observableIsRefreshing().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean refreshing) {
                mRefreshLy.setRefreshing(refreshing != null && refreshing);
            }
        });
        // 监听上拉加载状态发生变化
        viewModel.observableIsLoadingMore().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean loadingMore) {
                mAdapter.setOnLoadingMore(loadingMore != null && loadingMore);
            }
        });
        // 监听列表数据发生变化
        viewModel.observableWeiboList().observe(this, new Observer<List<PostItem>>() {
            @Override
            public void onChanged(@Nullable List<PostItem> data) {
                mAdapter.setDataList(data);
            }
        });
        // 监听加载错误信息
        viewModel.observableError().observe(this, new Observer<IError>() {
            @Override
            public void onChanged(@Nullable IError error) {
                if (error != null) {
                    Toast.makeText(getContext(), error.msg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
