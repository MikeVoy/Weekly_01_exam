package com.example.weekly_01_exam.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.weekly_01_exam.R;

public class MyView extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = MyView.class.getSimpleName();
    private Paint mPaint;
    private Bitmap bitmap;
    private Path mPath;
    private Canvas mCanvas;
    private int mH;
    private int mW;
    private float mx,my;
    private Bitmap picture;
    private boolean isInited = false;
    private float TOUCH_TOLERANCE = 2f;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mW = w;
        mH = h;

    }
  /*
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(isInited){

            mCanvas.drawPath(mPath, mPaint);//把线画到mCanvas上,mCanva会把线画到mBitmap
            canvas.drawBitmap(bitmap, 0, 0, null);// 把mBitmap画到textview上 canvas是父textvie传过来的。
        }
       // mCanvas.drawPath(mPath,mPaint);
     //   canvas.drawBitmap(picture,0,0,null);
      //  initScratchCard();
    }

    private void initScratchCard() {
        //初始化画笔   此画笔用来绘制  涂层
        mPaint = new Paint();
        mPaint.setAlpha(250);//透明度
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));//测量模式 ?
        mPaint.setAntiAlias(true); //抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//画笔类型
        mPaint.setDither(true);  //防抖动
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20f);

        mPath = new Path();
        //创建一个幕布
        bitmap = Bitmap.createBitmap(mW,mH, Bitmap.Config.ARGB_8888);

        mCanvas = new Canvas(bitmap);//自定义bitmap生成的画布
        Paint paint = new Paint();  //此画笔用于绘制 幕布上的字体
        paint.setColor(Color.parseColor("#ff0717"));
        paint.setTextSize(50);
        mCanvas.drawColor(Color.GRAY);  //直接给画布上色

        //获取图片资源,
         picture = BitmapFactory.decodeResource(getResources(), R.drawable.gakki03);
        //使用画布将图片绘制上去
        mCanvas.drawBitmap(picture,0,0,mPaint);

        mCanvas.drawText("刮刮看咯.....",mW/2,mH/2,paint);

        isInited = true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isInited) {
            return true;
        }

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(event.getX(), event.getY());
                mx = event.getX();
                my = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(event.getX() - mx);
                float dy = Math.abs(event.getY() - my);
                //x,y移动的距离大于画线容差
                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                    // 二次贝塞尔，实现平滑曲线；mX, mY为操作点，(x + mX) / 2, (y + mY) / 2为终点
                    mPath.quadTo(mx, my, (event.getX() + mx) / 2, (event.getY() + my) / 2);
                    // 第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
                    mx = event.getX();
                    my = event.getY();
                    Log.d(TAG, mx + "|" + my);
                    invalidate();
                    break;
                }
        }
        return true;
    }
}
