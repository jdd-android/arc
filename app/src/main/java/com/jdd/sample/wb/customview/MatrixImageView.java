package com.jdd.sample.wb.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author lc. 2018-03-30 09:56
 * @since 1.0.0
 */

public class MatrixImageView extends AppCompatImageView {

    /** 两个手指之间的距离 */
    private float mFingerDistance = 0F;

    /** 矩阵值 */
    private float[] mMatrixValue = new float[9];

    /** 手指开始落下的位置 */
    private PointF mStartPoint = new PointF();

    /** 矩阵 */
    private Matrix mMatrix = new Matrix();

    public MatrixImageView(Context context) {
        this(context, null);
    }

    public MatrixImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setBackgroundColor(Color.BLACK);
        setScaleType(ScaleType.FIT_CENTER);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();
        int actionIndex = event.getActionIndex();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartPoint.set(event.getX(), event.getY());
                if (getScaleType() != ScaleType.MATRIX) {
                    setScaleType(ScaleType.MATRIX);
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                mFingerDistance = calculateFingerDistance(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int pointerUpId = event.getPointerId(actionIndex);
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int pointerId = event.getPointerId(i);
                    if (pointerId != pointerUpId) {
                        mStartPoint.set(event.getX(i), event.getY(i));
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                if (pointerCount == 1) {
                    drag(event);
                }
                if (pointerCount == 2) {
                    zoom(event);
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // do nothing
                break;

            default:
                break;
        }

        return true;
    }

    /** 计算两个手指之间距离 */
    private float calculateFingerDistance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /** 处理缩放 */
    private void zoom(MotionEvent event) {
        float endDistance = calculateFingerDistance(event);
        float scale = endDistance / mFingerDistance;
        mFingerDistance = endDistance;

        mMatrix.set(getImageMatrix());
        mMatrix.getValues(mMatrixValue);
        mMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
        setImageMatrix(mMatrix);
    }

    /** 处理拖拽 */
    private void drag(MotionEvent event) {
        float dragX = event.getX() - mStartPoint.x;
        float dragY = event.getY() - mStartPoint.y;
        mStartPoint.set(event.getX(), event.getY());

        mMatrix.set(getImageMatrix());
        mMatrix.getValues(mMatrixValue);
        mMatrix.postTranslate(dragX, dragY);
        setImageMatrix(mMatrix);
    }

}
