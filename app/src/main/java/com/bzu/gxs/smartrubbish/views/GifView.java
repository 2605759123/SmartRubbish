package com.bzu.gxs.smartrubbish.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bzu.gxs.smartrubbish.R;

import java.io.InputStream;


/**
 * 创建者     xks
 * 创建时间   2016/5/24 17:32
 * 描述	      ${TODO}
 */
public class GifView extends View {

    private Movie mMovie;
    private long movieStart;
    private int width;
    private int height;
    private boolean isStart = false;
    private int gifid=R.raw.wanshang;
    InputStream is;

    public void setGifid(int gifid) {
        this.gifid = gifid;
    }

    public GifView(Context context) {
        super(context);
        initializeView();
    }

    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public GifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }
    public void initializeView() {
        //初始化gif图片资源
        Log.i("init888","1");
        is = getContext().getResources().openRawResource(gifid);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mMovie = Movie.decodeStream(is);
    }

    protected void onDraw(Canvas canvas) {


//        is = getContext().getResources().openRawResource(gifid);
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        mMovie = Movie.decodeStream(is);
//        Log.i("kkkkk","111");




        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);
        long now = android.os.SystemClock.uptimeMillis();
        if (movieStart == 0) {
            movieStart = (int) now;
        }
        if (mMovie != null) {
            int relTime = (int) ((now - movieStart) % mMovie.duration());
            mMovie.setTime(relTime);
            mMovie.draw(canvas, width / 2 - mMovie.width() / 2, 0);
            if (isStart) {
                this.invalidate();
            }
//            this.invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            //MeasureSpec.EXACTLY表示该view设置的确切的数值
            width = widthSize;
            height = heightSize;
        }
    }

    public void startAnimation() {
        isStart = true;
        postInvalidate();
    }

    public void stopAnimation() {
        isStart = false;
        postInvalidate();
    }

}
