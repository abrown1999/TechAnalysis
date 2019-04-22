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

    private Paint mPaintCircle;
    private float mcircleX, mcircleY;
    private float mcircleRadius = 50f;
    public boolean secure = false;
    public boolean create = false;
    private float xHold, yHold;
    private float x, y;

    //Circle mCircle;


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

    public void setSecure(Boolean boo) {
        secure = boo;
        xHold = x;
        yHold = y;
        System.out.println(secure + "SECUREEEDDDDD");
    }

    public void setCreate(Boolean boo) {
        create = boo;
        System.out.println(create);
    }

    private void init(@Nullable AttributeSet set) {
        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setColor(Color.BLUE);
        if (set == null)
            return;
        TypedArray ta = getContext().obtainStyledAttributes(set, new int[]{2130772129, 2130772130});
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mcircleX == 0f || mcircleY == 0f) {
            mcircleX = getWidth() / 2;
            mcircleY = getHeight() / 2;
        }
        if (create) {
            canvas.drawCircle(mcircleX, mcircleY, mcircleRadius, mPaintCircle);
            System.out.println("drawn");
        }
        if (!secure && xHold != 0 && yHold != 0) {
            System.out.println("drawing circle holded");
            canvas.drawCircle(xHold, yHold, mcircleRadius, mPaintCircle);
        }
        /*System.out.println("mCircle is: " + mCircle);
        if (mCircle != null) {
            canvas.drawCircle(mCircle.cx, mCircle.cy, mCircle.radius, mPaintCircle);
            System.out.println("circle drawn!!");
        }*/
    }

    public void drawCirclePlease() {
        //mCircle = new Circle();
        System.out.println("created a dot");
        secure = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);
        if (!secure) {
            System.out.println("move dot");
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
        return value;
    }
/*
    class Circle {
        float cx;
        float cy;
        float radius;

        Circle(float setcx, float setcy, float setradius) {
            cx = setcx;
            cy = setcy;
            radius = setradius;
        }
        Circle() {
        }

        public void onClick(View v) {
            System.out.println("something is happening here");
            if (mCircle == null) {
                mCircle = new Circle(cx, cy, radius);
            }
            mCircle.cx = 50;
            mCircle.cy = 50;
            mCircle.radius = 50;
            // This will call onDraw() method under the hood.
            invalidate();
        }
    }
    */
}
