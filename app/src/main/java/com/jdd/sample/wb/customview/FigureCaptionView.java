package com.jdd.sample.wb.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author lc. 2018-03-20 09:10
 * @since 1.0.0
 */

public class FigureCaptionView extends FrameLayout {

    public FigureCaptionView(Context context) {
        this(context, null);
    }

    public FigureCaptionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FigureCaptionView(@NonNull Context context, @Nullable AttributeSet attrs,
                             @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public FigureCaptionView(@NonNull Context context, @Nullable AttributeSet attrs,
                             @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
