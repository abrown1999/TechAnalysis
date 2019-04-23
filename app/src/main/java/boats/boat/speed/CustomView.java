package boats.boat.speed;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View {

    private Paint mPaintCircleB, mPaintCircleR, mPaintCircleG, mPaintCircleY, mPaintCircleDefault;
    private float mcircleRadius = 50f;
    public boolean create, secureB, secureR, secureG, secureY, lineDraw = false;
    private float mcircleX, mcircleY, xHoldB, yHoldB, xHoldR, yHoldR, xHoldG, yHoldG, xHoldY, yHoldY, x, y;
    private float[][] indexes = new float[4][2];

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void setSecureB(Boolean boo) {
        secureB = boo;
        xHoldB = x;
        yHoldB = y;
        indexes[2][0] = x;
        indexes[2][1] = y;
    }

    public void setSecureR(Boolean boo) {
        secureR = boo;
        xHoldR = x;
        yHoldR = y;
        indexes[0][0] = x;
        indexes[0][1] = y;
    }
    public void setSecureG(Boolean boo) {
        secureG = boo;
        xHoldG = x;
        yHoldG = y;
        indexes[1][0] = x;
        indexes[1][1] = y;
    }
    public void setSecureY(Boolean boo) {
        secureY = boo;
        xHoldY = x;
        yHoldY = y;
        indexes[3][0] = x;
        indexes[3][1] = y;
    }

    public float[][] getIndexes() {
        return indexes;
    }

    public void setCreate(Boolean boo) {
        create = boo;
    }

    public void drawLines() {
        lineDraw = true;
        postInvalidate();
    }

    private void init(@Nullable AttributeSet set) {
        mPaintCircleDefault = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircleDefault.setAntiAlias(true);
        mPaintCircleDefault.setColor(Color.BLACK);
        mPaintCircleB = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircleB.setAntiAlias(true);
        mPaintCircleB.setColor(Color.rgb(109, 124, 246));
        mPaintCircleR = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircleR.setAntiAlias(true);
        mPaintCircleR.setColor(Color.rgb(255, 79, 79));
        mPaintCircleG = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircleG.setAntiAlias(true);
        mPaintCircleG.setColor(Color.rgb(54, 204, 47));
        mPaintCircleY = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircleY.setAntiAlias(true);
        mPaintCircleY.setColor(Color.rgb(255, 255, 78));
        if (set == null)
            return;
        TypedArray ta = getContext().obtainStyledAttributes(set, new int[]{2130772129, 2130772130});
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (lineDraw) {
            drawThiccAssLines(canvas);
            System.out.println("indexes in CustomView onDraw");
            for (int i = 0; i < indexes.length; i++) {
                System.out.println(((int) indexes[i][0]) + " " + ((int) indexes[i][1]));
            }
        }
        if (create) {
            canvas.drawCircle(mcircleX, mcircleY, mcircleRadius, mPaintCircleDefault);
        }
        if (xHoldB != 0 && yHoldB != 0) {
            canvas.drawCircle(xHoldB, yHoldB, mcircleRadius, mPaintCircleB);
        }
        if (xHoldR != 0 && yHoldR != 0) {
            canvas.drawCircle(xHoldR, yHoldR, mcircleRadius, mPaintCircleR);
        }
        if (xHoldG != 0 && yHoldG != 0) {
            canvas.drawCircle(xHoldG, yHoldG, mcircleRadius, mPaintCircleG);
        }
        if (xHoldY != 0 && yHoldY != 0) {
            canvas.drawCircle(xHoldY, yHoldY, mcircleRadius, mPaintCircleY);
        }
    }

    private void drawThiccAssLines(Canvas canvas) {
        Paint paintColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintColor.setAntiAlias(true);
        paintColor.setColor(Color.rgb(255, 212, 128));
        for (float i = -mcircleRadius / 2; i < mcircleRadius / 2; i++) {
            canvas.drawLine(indexes[0][0] + i, indexes[0][1] + i, indexes[1][0] + i, indexes[1][1] + i, paintColor);
            canvas.drawLine(indexes[1][0] + i, indexes[1][1] + i, indexes[2][0] + i, indexes[2][1] + i, paintColor);
            canvas.drawLine(indexes[2][0] + i, indexes[2][1] + i, indexes[3][0] + i, indexes[3][1] + i, paintColor);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);
        if (!secureB) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    return true;
                }
                case MotionEvent.ACTION_MOVE: {
                    x = event.getX();
                    y = event.getY();
                    mcircleX = x;
                    mcircleY = y;
                    postInvalidate();
                    return true;
                }
            }
        }
        if (!secureR) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    return true;
                }
                case MotionEvent.ACTION_MOVE: {
                    x = event.getX();
                    y = event.getY();
                    mcircleX = x;
                    mcircleY = y;
                    postInvalidate();
                    return true;
                }
            }
        }
        if (!secureG) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    return true;
                }
                case MotionEvent.ACTION_MOVE: {
                    x = event.getX();
                    y = event.getY();
                    mcircleX = x;
                    mcircleY = y;
                    postInvalidate();
                    return true;
                }
            }
        }
        if (!secureY) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    return true;
                }
                case MotionEvent.ACTION_MOVE: {
                    x = event.getX();
                    y = event.getY();
                    mcircleX = x;
                    mcircleY = y;
                    mPaintCircleDefault = mPaintCircleY;
                    postInvalidate();
                    return true;
                }
            }
        }
        return value;
    }
}
