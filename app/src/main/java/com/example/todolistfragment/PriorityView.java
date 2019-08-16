package com.example.todolistfragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

public class PriorityView extends View {
    private Paint priorityPaint;
    private Path path;

    private Point point1_draw;
    private Point point2_draw;
    private Point point3_draw;

    private int priority;
    private int height;
    private int widht;
    public static final int PRIORITY_HIGH=3;
    public static final int PRIORITY_MEDIUM=2;
    public static final int PRIORITY_LOW=1;
    public static final int PRIORITY_DEFAULT=4;


    public PriorityView(Context context) {
        super(context);
        init(null);

    }

    public PriorityView(Context context, AttributeSet attrs) {

        super(context, attrs);
        init(attrs);
    }

   private void init(AttributeSet attrs){
        priorityPaint=new Paint();
        path=new Path();
        point1_draw = new Point();
        point2_draw = new Point();
        point3_draw = new Point();
        priorityPaint.setStyle(Paint.Style.FILL_AND_STROKE);

       if(attrs==null){
           return;
       }

        TypedArray typedArray=getContext().obtainStyledAttributes(attrs,R.styleable.PriorityView);
       priority=typedArray.getInteger(R.styleable.PriorityView_priority,PRIORITY_DEFAULT);
       typedArray.recycle();

       if(priority==PRIORITY_HIGH){
           priorityPaint.setColor(Color.RED);
       }else if(priority==PRIORITY_MEDIUM){
           priorityPaint.setColor(Color.GREEN);
       }else if(priority==PRIORITY_LOW){
           priorityPaint.setColor(Color.YELLOW);
       }else {
           priorityPaint.setColor(Color.BLUE);
           priorityPaint.setAntiAlias(true);
       }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    height=getMeasuredHeight();
    widht=getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(priority==PRIORITY_HIGH){
            canvas.drawCircle(height/2,widht/2,widht/2.5f,priorityPaint);
        }else if(priority==PRIORITY_MEDIUM){
            canvas.drawRect(widht/10,height/10,widht-widht/10,height-height/10,priorityPaint);
        }else if(priority==PRIORITY_LOW){
            canvas.drawRoundRect(widht/10,height/10,widht-widht/10,height-height/10,height/5,widht/5,priorityPaint);
        }else {
            point1_draw.x=widht/2;
            point1_draw.y=height/10;
            point2_draw.x =widht-widht/10;
            point2_draw.y =height-height/10;
            point3_draw.x =widht/10;
            point3_draw.y =height-height/10;
            path.moveTo(point1_draw.x,point1_draw.y);
            path.lineTo(point2_draw.x,point2_draw.y);
            path.lineTo(point3_draw.x,point3_draw.y);
            path.lineTo(point1_draw.x,point1_draw.y);
            path.close();
            canvas.drawPath(path,priorityPaint);
            path.reset();
        }
    }
    public void setPriority(int flag){
        if(flag==PRIORITY_HIGH){
        priority=flag;
        priorityPaint.setColor(Color.RED);
        invalidate();
        }else if(flag==PRIORITY_MEDIUM){
            priority=flag;
            priorityPaint.setColor(Color.GREEN);
            invalidate();
        }else if(flag==PRIORITY_LOW){
            priority=flag;
            priorityPaint.setColor(Color.YELLOW);
            invalidate();
        }else {
            priority=flag;
            priorityPaint.setColor(Color.BLUE);
            priorityPaint.setAntiAlias(true);
            invalidate();
        }
    }
}
