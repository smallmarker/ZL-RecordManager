package com.zl.recordmanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaolong
 * @ClassName WaveView
 * @Date 2019/4/25
 **/
public class WaveView extends View {

    private Paint mPaint;

    private List<String> mColorTransparencys = new ArrayList<>();

    private Bitmap mBitmap;

    private int count;

    private boolean isChanges;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);

        mColorTransparencys.add("#4D11d2ce");
        mColorTransparencys.add("#3311d2ce");
        mColorTransparencys.add("#1A11d2ce");

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.record);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isChanges) {
            count--;
        } else {
            count++;
        }
        int x = getWidth()/2;
        int y = getHeight()/2;
        int radius = mBitmap.getWidth()/2;
        mPaint.setColor(Color.parseColor("#11d2ce"));
        canvas.drawBitmap(mBitmap,x - radius,y - radius,mPaint);
        for (int i = 0; i < count; i++) {
            mPaint.setColor(Color.parseColor(mColorTransparencys.get(i)));
            if (i == 0) {
                radius+=10;
            } else {
                radius+=20;
            }
            canvas.drawCircle(x,y,radius,mPaint);
        }
        if (count == 3) {
            isChanges = true;
        }
        if (count == 0) {
            isChanges = false;
        }
        postInvalidateDelayed(300);
    }
}
