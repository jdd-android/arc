package com.jdd.sample.wb.module.home;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdd.sample.wb.R;
import com.jdd.sample.wb.api.bean.PostItem;
import com.jdd.sample.wb.common.PostItemOnClickCallback;
import com.jdd.sample.wb.databinding.RecyclerItemWbBinding;
import com.jdd.sample.wb.databinding.RecyclerItemWbRepostBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lc. 2018-03-20 09:20
 * @since 1.0.0
 */

class PostRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ORIGIN = 1;
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_RE_POST = 3;

    private static final PostItem FOOTER = new PostItem();

    private Activity mActivity;
    private LayoutInflater mInflater;

    private PostItemOnClickCallback mPostOnClickCallback;
    private List<PostItem> mDataList = new ArrayList<>();

    PostRecyclerAdapter(Activity activity) {
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
    }

    void setPostOnClickCallback(PostItemOnClickCallback callback) {
        this.mPostOnClickCallback = callback;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        PostItem item = mDataList.get(position);
        if (item == FOOTER) {
            return TYPE_FOOTER;
        }
        if (item.getRePostOrigin() != null) {
            return TYPE_RE_POST;
        }
        return TYPE_ORIGIN;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ORIGIN) {
            RecyclerItemWbBinding binding = DataBindingUtil.inflate(mInflater, R.layout.recycler_item_wb, parent, false);
            binding.setPostClickCallback(mPostOnClickCallback);
            return new OriginPostViewHolder(binding);
        }
        if (viewType == TYPE_RE_POST) {
            RecyclerItemWbRepostBinding binding = DataBindingUtil.inflate(mInflater, R.layout.recycler_item_wb_repost, parent, false);
            binding.setPostClickCallback(mPostOnClickCallback);
            return new RePostViewHolder(binding);
        }

        return new FooterViewHolder(mInflater.inflate(R.layout.recycler_item_footer, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof OriginPostViewHolder) {
            ((OriginPostViewHolder) viewHolder).update(mDataList.get(position));
        }
        if (viewHolder instanceof RePostViewHolder) {
            ((RePostViewHolder) viewHolder).update(mDataList.get(position));
        }
    }

    void setDataList(final List<PostItem> list) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return mDataList.size();
            }

            @Override
            public int getNewListSize() {
                return list.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                PostItem oldItem = mDataList.get(oldItemPosition);
                PostItem newItem = list.get(newItemPosition);
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                PostItem oldItem = mDataList.get(oldItemPosition);
                PostItem newItem = list.get(newItemPosition);
                return oldItem.getId() == newItem.getId() && oldItem.getText().equals(newItem.getText());
            }
        });
        mDataList.clear();
        mDataList.addAll(list);
        result.dispatchUpdatesTo(this);
    }

    void setOnLoadingMore(boolean loadingMore) {
        if (loadingMore) {
            if (!containsFooter()) {
                mDataList.add(FOOTER);
                notifyItemInserted(mDataList.size() - 1);
            }
        } else {
            if (containsFooter()) {
                mDataList.remove(mDataList.size() - 1);
                notifyItemRemoved(mDataList.size());
            }
        }
    }

    private boolean containsFooter() {
        for (int i = mDataList.size() - 1; i >= 0; i--) {
            if (mDataList.get(i) == FOOTER) {
                return true;
            }
        }
        return false;
    }

    class OriginPostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private PostItem mData;

        private RecyclerItemWbBinding mBinding;

        OriginPostViewHolder(RecyclerItemWbBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void update(PostItem item) {
            mData = item;
            mBinding.setItem(mData);
        }

        @Override
        public void onClick(View v) {

        }
    }

    class RePostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private PostItem mData;

        private RecyclerItemWbRepostBinding mBinding;

        RePostViewHolder(RecyclerItemWbRepostBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void update(PostItem item) {
            mData = item;
            mBinding.setItem(mData);
        }

        @Override
        public void onClick(View v) {

        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(View view) {
            super(view);
        }
    }

}
