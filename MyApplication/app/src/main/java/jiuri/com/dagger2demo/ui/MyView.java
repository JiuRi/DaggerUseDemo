package jiuri.com.dagger2demo.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import jiuri.com.dagger2demo.R;

/**
 * Created by user103 on 2017/8/25.
 */

public class MyView extends View {

    private int mColor1;
    private Paint mPaint;
    private Bitmap mBitmap;
    private int mWidth;
    private int mHeight;
    private int i;
    private Handler mHandler;

    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        mHandler = new Handler(context.getMainLooper());
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.check);
        mColor1 = Color.BLUE;
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);//设置画笔为之描边 不填充  ，还可以设置为填充
        /**
         *       STROKE                描边
                 FILL                  填充
                 FILL_AND_STROKE       描边加填充
         */
        mPaint.setStrokeWidth(10f);//设置画笔的宽度
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    /*   // canvas.drawColor(mColor1); //画画布的颜色
        //画矩形
        //canvas.drawRoundRect(10,10,100,100,20,50,mPaint);//画可圆角的矩形 6 个参数分别表示 左上角 的 x y,和右下角的x,y，以及x方向的圆角,以及y方向的圆角
        canvas.drawRect(10f,10f,300f,300f,mPaint);//画矩形 后面画的会把前面画的遮盖住
        //画点
        canvas.drawPoint(500,500,mPaint);
        float height=450;
        //划线
        for (int i = 0; i <50 ; i++) {
            height+=50;
            canvas.drawLine(0,height,1000,height,mPaint);
        }*/
/*        canvas.translate(mWidth/2,mHeight/2);
        //画圆
        canvas.drawCircle(0,0,400,mPaint);//绘制圆形有四个参数，前两个是圆心坐标，第三个是半径，最后一个是画笔。
        //画圆弧
        *//**
         * 第一种
         * drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter, @NonNull Paint paint)
         * 开始角度  扫过角度 是否使用中心

         *//*
        mPaint.setColor(Color.BLUE);
        canvas.drawPoint(0,0,mPaint);

       // canvas.drawBitmap(mBitmap,500,500,mPaint);
        i++;
        int height = mBitmap.getHeight();
        int width = mBitmap.getWidth();
        canvas.translate(-width,-height);
            SystemClock.sleep(1000);
            Rect rect=new Rect(0,0,width/13*i,height);//图片绘制的区域
            Rect dst = new Rect(0,0,width/13*i*2,height*2);
            //canvas.drawBitmap(mBitmap,new Matrix(),mPaint);
        canvas.drawBitmap(mBitmap,rect,dst,mPaint);*/
        mPaint.setColor(Color.YELLOW);
        canvas.drawLine(10,mHeight/2,mWidth,mHeight/2,mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawRoundRect(10,mHeight/2-20,80,mHeight/2+20,8,8,mPaint);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(13);
        canvas.drawText("0%",35,mHeight/2,mPaint);

    }
    public void check(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        },500);
    }
}
