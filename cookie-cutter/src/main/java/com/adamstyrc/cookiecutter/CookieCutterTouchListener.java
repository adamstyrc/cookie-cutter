package com.adamstyrc.cookiecutter;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by adamstyrc on 31/03/16.
 */
public class CookieCutterTouchListener implements View.OnTouchListener {

    private final Circle circle;
    private Mode mode = Mode.NONE;

    private CookieCutterParams cookieCutterParams;

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    PointF firstTouchPoint = new PointF();
    PointF scaleCenterPoint = new PointF();
    float fingersDistance = 1f;

    public CookieCutterTouchListener(CookieCutterParams cookieCutterParams, Matrix matrix) {
        this.cookieCutterParams = cookieCutterParams;
        circle = cookieCutterParams.getCircle();

        this.matrix.set(matrix);
        this.savedMatrix.set(matrix);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        dumpEvent(event);

        ImageView view = (ImageView) v;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                firstTouchPoint.set(event.getX(), event.getY());
                mode = Mode.DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                fingersDistance = spacing(event);
                if (fingersDistance > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(scaleCenterPoint, event);
                    mode = Mode.ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = Mode.NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == Mode.DRAG) {
                    onImageDragged(event, view);
                } else if (mode == Mode.ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        onImageScaled(view, newDist);
                    }
                }
                break;
        }

        Logger.log(matrix.toString());
        view.setImageMatrix(matrix);
        return true; // indicate event was handled
    }

    private void onImageScaled(ImageView view, float newDist) {
        float scale = newDist / fingersDistance;
        Logger.log("Scale: " + scale);

        MatrixParams matrixParams = MatrixParams.fromMatrix(savedMatrix);
        float imageWidth = view.getDrawable().getIntrinsicWidth() * matrixParams.getScaleWidth();
        float imageHeight = view.getDrawable().getIntrinsicHeight() * matrixParams.getScaleHeight();

        float savedDistanceLeft = scaleCenterPoint.x - matrixParams.getX();
        float savedDistanceRight = matrixParams.getX() + imageWidth - scaleCenterPoint.x;
        float savedDistanceTop = scaleCenterPoint.y - matrixParams.getY();
        float savedDistanceBottom = matrixParams.getY() + imageHeight - scaleCenterPoint.y;

        float imageLeft = scaleCenterPoint.x - savedDistanceLeft * scale;
        if (imageLeft > circle.getLeftBound()) {
            scale = (scaleCenterPoint.x - circle.getLeftBound()) / savedDistanceLeft;
            Logger.log("Scaling exceeded: left " + scale);
        }

        float imageRight = scaleCenterPoint.x + savedDistanceRight * scale;
        if (imageRight < circle.getRightBound()) {
            scale = (circle.getRightBound() - scaleCenterPoint.x) / savedDistanceRight;
            Logger.log("Scaling exceeded: right " + scale);
        }

        float imageTop = scaleCenterPoint.y - savedDistanceTop * scale;
        if (imageTop > circle.getTopBound()) {
            scale = (scaleCenterPoint.y - circle.getTopBound()) / savedDistanceTop;
            Logger.log("Scaling exceeded: top " + scale);
        }

        float imageBottom = scaleCenterPoint.y + savedDistanceBottom * scale;
        if (imageBottom < circle.getBottomBound()) {
            scale = (circle.getBottomBound() - scaleCenterPoint.y) / savedDistanceBottom;
            Logger.log("Scaling exceeded: bottom " + scale);
        }

        float currentScale = scale * matrixParams.getScaleWidth();
        if (currentScale > cookieCutterParams.getMaxZoom()) {
            scale = cookieCutterParams.getMaxZoom() / matrixParams.getScaleWidth();
        }

        float croppedImageSize = circle.getDiameter() / currentScale;
        Logger.log("croppedImageSize: " + croppedImageSize);
        if (croppedImageSize < cookieCutterParams.getMinImageSize()) {
            scale = circle.getDiameter() / cookieCutterParams.getMinImageSize() / matrixParams.getScaleWidth();
        }

        matrix.set(savedMatrix);
        matrix.postScale(scale, scale, scaleCenterPoint.x, scaleCenterPoint.y);
    }

    private void onImageDragged(MotionEvent event, ImageView view) {
        matrix.set(savedMatrix);
        float translationX = event.getX() - firstTouchPoint.x;
        float translationY = event.getY() - firstTouchPoint.y;

        float[] matrixValues = new float[9];
        savedMatrix.getValues(matrixValues);

        float scaleWidth = matrixValues[0];
        float scaleHeight = matrixValues[4];
        float x = matrixValues[2];
        float y = matrixValues[5];

        int circleCenterX = view.getWidth() / 2;
        int circleCenterY = view.getHeight() / 2;
        int circleRadius = cookieCutterParams.getCircleRadius();

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

    /** Calculate the scaleCenterPoint point of the first two fingers */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    private enum Mode {
        NONE,
        DRAG,
        ZOOM;
    }
}
