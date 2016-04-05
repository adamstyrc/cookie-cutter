package com.adamstyrc.biscuit;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Map;

/**
 * Created by adamstyrc on 04/04/16.
 */
public class BiscuitParams {

    private Circle circle;
    private float maxZoom = 4;
    private BiscuitShape shape = BiscuitShape.HOLE;

    private int width;
    private int height;

    private Hole hole;

    public Circle getCircle() {
        return circle;
    }

    public float getMaxZoom() {
        return maxZoom;
    }

    public void setMaxZoom(float maxZoom) {
        this.maxZoom = maxZoom;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void setShape(BiscuitShape shape) {
        this.shape = shape;
    }

    public BiscuitShape getShape() {
        return shape;
    }

    void updateWithView(int width, int height, int circleRadius) {
        this.width = width;
        this.height = height;

        circle = new Circle(width / 2, height / 2, circleRadius);
        hole = new Hole();
    }

    public Hole getHole() {
        return hole;
    }

    public class Hole {

        private Path path;
        private Paint paint;

        public Hole() {
            setPath();

            paint = new Paint();
            paint.setColor(Color.parseColor("#AA000000"));
        }

        Path getPath() {
            return path;
        }

        Paint getPaint() {
            return paint;
        }

        private void setPath() {
            path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.addRect(0, 0, width, height, Path.Direction.CW);
            Circle circle = getCircle();
            path.addCircle(circle.getCx(), circle.getCy(), circle.getRadius(), Path.Direction.CW);
        }

    }
}
