package com.app.hutbay.utility;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import com.app.hutbay.R;

import java.io.InputStream;

public class GifView extends View {

    private Movie mMovie;
    private long movieStart;
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

    private void initializeView() {

        //R.drawable.loader - our animated GIF

        InputStream is = getContext().getResources().openRawResource(R.drawable.loading);

        mMovie = Movie.decodeStream(is);

    }


    @Override

    protected void onDraw(Canvas canvas) {

        //canvas.drawColor(Color.TRANSPARENT);
        canvas.drawColor(Color.BLACK);

        super.onDraw(canvas);

        long now = android.os.SystemClock.uptimeMillis();

        if (movieStart == 0) {

            movieStart = now;

        }

        if (mMovie != null) {
            int relTime = (int) ((now - movieStart) % mMovie.duration());
            //double scalefactorx = (double) this.getWidth() / (double) mMovie.width();
            //double scalefactory = (double) this.getHeight() / (double) mMovie.height(); canvas.scale((float) scalefactorx, (float) scalefactory); mMovie.draw(canvas, (float) scalefactorx, (float) scalefactory);
            mMovie.setTime(relTime);
            mMovie.draw(canvas, (getWidth() - mMovie.width())/2, (getHeight() - mMovie.height())/2);
            //mMovie.draw(canvas, (float)scalefactorx, (float)scalefactory);
            this.invalidate();

        }

    }

    public void setImageResource(int id){}


}
