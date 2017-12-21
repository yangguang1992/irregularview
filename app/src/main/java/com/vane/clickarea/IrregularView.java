package com.vane.clickarea;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * 不规则的自定义view,相应点击事件
 * Created by ljb on 2017/5/4.
 */

public class IrregularView extends ImageView {

    int width = -1;
    int height = -1;
    Bitmap bitmap;


    public IrregularView(Context context) {
        this(context, null);
    }

    public IrregularView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IrregularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (width == -1 || height == -1) {
                    BitmapDrawable current = (BitmapDrawable) getBackground().getCurrent();
                    bitmap = current.getBitmap();
                    width = getWidth();
                    height = getHeight();

                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)//判断是否为下按
            return super.onTouchEvent(event);

        int x = (int) event.getX();
        int y = (int) event.getY();

        //判断触摸事件是否在屏幕内
        if (bitmap == null || x < 0 || y < 0 || x > width || y > height) {
            return false;
        }

        //判断是否点击位置是否为透明区域
        int pixel = bitmap.getPixel(x, y);
        return pixel!=Color.TRANSPARENT && super.onTouchEvent(event);
    }
}
