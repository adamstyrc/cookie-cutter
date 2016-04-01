package com.adamstyrc.zingerlib;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by adamstyrc on 31/03/16.
 */
public class ZingerTouchListener implements View.OnTouchListener {

    Matrix matrix = new Matrix();
    Matrix lastScaledMatrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    float lastSavedScale;

    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;


    PointF startPoint = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        dumpEvent(event);

        ImageView view = (ImageView) v;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                startPoint.set(event.getX(), event.getY());
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    onImageDragged(event, view);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        Logger.log("Scale: " + scale);
                        matrix.postScale(scale, scale, mid.x, mid.y);

                        float[] matrixValues = new float[9];
                        matrix.getValues(matrixValues);

                        float x = matrixValues[2];
                        float y = matrixValues[5];

                        int circleCenterX = view.getWidth() / 2;
                        int circleCenterY = view.getHeight() / 2;
                        int circleRadius = 400;

                        float width = view.getDrawable().getIntrinsicWidth() * scale;
                        float height = view.getDrawable().getIntrinsicHeight() * scale;

                        if (x > circleCenterX - circleRadius ||
                                x + width < circleCenterX + circleRadius ||
                                y > circleCenterY - circleRadius ||
                                y + height < circleCenterY + circleRadius) {
                            Logger.log("lastScaledMatrix: " + lastScaledMatrix.toString());
                            matrix.set(lastScaledMatrix);
                        } else {
                            lastScaledMatrix.set(matrix);
                        }
                    }
                }
                break;
        }

        Logger.log(matrix.toString());
        view.setImageMatrix(matrix);
        return true; // indicate event was handled
    }

    private void onImageDragged(MotionEvent event, ImageView view) {
        matrix.set(savedMatrix);
        float translationX = event.getX() - startPoint.x;
        float translationY = event.getY() - startPoint.y;

        float[] matrixValues = new float[9];
        savedMatrix.getValues(matrixValues);

        float scaleWidth = matrixValues[0];
        float scaleHeight = matrixValues[4];
        float x = matrixValues[2];
        float y = matrixValues[5];

        int circleCenterX = view.getWidth() / 2;
        int circleCenterY = view.getHeight() / 2;
        int circleRadius = 400;

        float width = view.getDrawable().getIntrinsicWidth() * scaleWidth;
        float height = view.getDrawable().getIntrinsicHeight() * scaleHeight;

        if (translationX + x > circleCenterX - circleRadius) {
            translationX = circleCenterX - circleRadius - x;
        } else if (translationX + x + width < circleCenterX + circleRadius) {
            translationX = circleCenterX + circleRadius - x - width;
        }

        if (translationY + y > circleCenterY - circleRadius) {
            translationY = circleCenterY - circleRadius - y;
        } else if (translationY + y + height < circleCenterY + circleRadius) {
            translationY = circleCenterY + circleRadius - y - height;
        }

        matrix.postTranslate(translationX, translationY);
    }


    private void dumpEvent(MotionEvent event) {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
        sb.append("]");

        Logger.log(sb.toString());
    }

    /** Determine the space between the first two fingers */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt((double) x * x + y * y);
    }

    /** Calculate the mid point of the first two fingers */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
