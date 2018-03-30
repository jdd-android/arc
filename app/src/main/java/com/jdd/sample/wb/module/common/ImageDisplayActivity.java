package com.jdd.sample.wb.module.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jdd.sample.wb.R;

/**
 * @author lc
 */
public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_display);

        ImageView imageView = findViewById(R.id.iv_zoom);
        Glide.with(this)
                .load("http://7xju00.com1.z0.glb.clouddn.com/longimage.png")
                .apply(RequestOptions.noTransformation())
                .into(imageView);
    }
}
