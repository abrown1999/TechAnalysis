package boats.boat.speed.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import boats.boat.speed.R;

public class CustomView extends View {

    private Paint mPaintCircle;
    private OvalShape mOvalShape;
    private int mCircleeColor;
    private float mcircleX, mcircleY;
    private float mcircleRadius = 50f;

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
        canvas.drawCircle(mcircleX, mcircleY, mcircleRadius, mPaintCircle);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);
        System.out.println("idk whats going on");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                return true;
            }
            case MotionEvent.ACTION_MOVE    : {
                System.out.println("should be following");
                float x = event.getX();
                float y = event.getY();

                double dx = Math.pow(x - mcircleX, 2);
                double dy = Math.pow(x - mcircleY, 2);
                if (dx + dy < Math.pow(mcircleRadius, 2)) {
                    mcircleX = x;
                    mcircleY = y;
                    postInvalidate();
                    return true;
                }
                return value;

            }
        }
        return value;
    }
}
