package com.jdd.sample.wb.databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author lc. 2018-03-21 10:49
 * @since 1.0.0
 *
 * 自定义数据绑定属性方法。
 */

public class BindingAdapters {

    @BindingAdapter({"imageUrl"})
    public static void loadRoundImage(ImageView imageView, String imageUrl) {
        RequestOptions options = new RequestOptions()
                .circleCrop();
        Glide.with(imageView)
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }
}
