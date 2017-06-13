package com.adamstyrc.cookiecutter;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by adamstyrc on 04/04/16.
 */
public class CookieCutterParams {

    private int circleRadius = 400;
    private Circle circle;
    private float maxZoom = 4;
    private int minImageSize = 200;
    private CookieCutterShape shape = CookieCutterShape.HOLE;

    private int width;
    private int height;

    private HoleParams holeParams;
    private CircleParams circleParams;
    private SquareParams squareParams;


    Circle getCircle() {
        return circle;
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public float getMaxZoom() {
        return maxZoom;
    }

    public void setMaxZoom(float maxZoom) {
        this.maxZoom = maxZoom;
    }

    public int getMinImageSize() {
        return minImageSize;
    }

    public void setMinImageSize(int minImageSize) {
        this.minImageSize = minImageSize;
    }

    public void setShape(CookieCutterShape shape) {
        this.shape = shape;
    }

    public CookieCutterShape getShape() {
        return shape;
    }

    void updateWithView(int width, int height) {
        this.width = width;
        this.height = height;

        circle = new Circle(width / 2, height / 2, circleRadius);

        holeParams = new HoleParams();
        circleParams = new CircleParams();
        squareParams = new SquareParams();
    }

    public HoleParams getHoleParams() {
        return holeParams;
    }

    public CircleParams getCircleParams() {
        return circleParams;
    }

    public SquareParams getSquareParams() {
        return squareParams;
    }

    public class HoleParams {
        Path path;
        Paint paint;

        public HoleParams() {
            setPath();

            paint = new Paint();
            paint.setColor(Color.parseColor("#AA000000"));
        }

        public void setColor(int color) {
            paint.setColor(color);
        }

        private void setPath() {
            path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.addRect(0, 0, width, height, Path.Direction.CW);
            path.addCircle(circle.getCx(), circle.getCy(), circle.getRadius(), Path.Direction.CW);
        }
    }

    public class CircleParams {
        Paint paint;

        public CircleParams() {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
        }

        public void setStrokeWidth(float strokeWidth) {
            paint.setStrokeWidth(strokeWidth);
        }

        public void setColor(int color) {
            paint.setColor(color);
        }
    }

    public class SquareParams {
        Paint paint;

        public SquareParams() {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
        }

        public void setStrokeWidth(float strokeWidth) {
            paint.setStrokeWidth(strokeWidth);
        }

        public void setColor(int color) {
            paint.setColor(color);
        }
    }
}
