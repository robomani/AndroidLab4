package erebe.lab4;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Paint;

class chronos extends View implements Constants, IUpdatable {


    private float cx, cy, size, angle, updateAngle, radius, scale;
    private Paint paint = new Paint();
    private TypedArray a;
    public int sides;


    public chronos(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public chronos(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint.setColor(Color.BLUE);
        a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.chronos, defStyle, 0);
        try {
            int color = a.getColor(R.styleable.chronos_color, Color.BLUE);
            paint.setColor(color);
            size = a.getDimensionPixelSize(R.styleable.chronos_Size,50);
            sides = a.getInteger(R.styleable.chronos_Sides,3);
            angle = a.getFloat(R.styleable.chronos_Angle,0);

        } finally {
            a.recycle();
        }
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);


    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.translate(cx,cy);
        if (sides == 7)
        {
            canvas.scale(scale,scale);
        }
        DrawPolygon(canvas,sides,size);

    }

    @Override
    public void Update()
    {
        updateAngle = updateAngle + 0.3f;
        if (updateAngle > 360)
        {
            updateAngle = 0;
        }

        invalidate();
    }



    protected void DrawPolygon(Canvas canvas,int i_Sides, float i_Size)
    {
        if (i_Sides > 7)
        {
            i_Sides = 7;
        }
        if (i_Sides < 3)
        {
            i_Sides = 3;
        }

        float internal = 360.0f / i_Sides;
        float external =  180f - internal;

        if (internal < 60)
        {
            i_Size /= 2;
            /*
            if (internal < 30)
            {
                size /= 2;
            }
            */
        }
        canvas.rotate(angle + updateAngle);
        for (int i = 0; i < i_Sides; i++)
        {

            canvas.save();
            canvas.translate(-i_Size,0);
            canvas.rotate(external / 2);
            canvas.drawLine(i_Size,0,0,0,paint);
            canvas.rotate(-external);
            canvas.drawLine(i_Size,0,0,0,paint);

            if (i_Sides > 3)
            {
                DrawPolygon(canvas,i_Sides-1, i_Size/2);

            }
            canvas.restore();

            canvas.rotate(internal);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        cx = w/2;
        cy = h/2;
        radius = Math.min(cx,cy);
        scale = radius / 100.0f;
    }


}
