package com.adamstyrc.zingerlib;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by adamstyrc on 31/03/16.
 */
public class ZingerImageView extends ImageView {

    private Paint circlePaint;
    private int circleRadius;

    public ZingerImageView(Context context) {
        super(context);
        init();
    }

    public ZingerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ZingerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @TargetApi(value = 21)
    public ZingerImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    public void init() {
        setOnTouchListener(new ZingerTouchListener());
        setScaleType(ScaleType.MATRIX);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStrokeWidth(10);
        circlePaint.setStyle(Paint.Style.STROKE);

        circleRadius = 400;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;


        canvas.drawCircle(cx, cy, circleRadius, circlePaint);
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
    }
}
