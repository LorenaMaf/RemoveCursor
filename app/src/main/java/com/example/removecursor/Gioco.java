package com.example.removecursor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.Random;

public class Gioco extends View {
    ArrayList<Cerchio> cerchi = new ArrayList<>();
    private boolean cerchiGenerati = false;

    public Gioco(Context c, int screen_w, int screen_h) {
        super(c);
        int daTogliereW = (screen_w*10)/100;
        int daTogliereH = (screen_h*10)/100;
        setMinimumWidth(screen_w-daTogliereW);
        setMinimumHeight(screen_h-daTogliereH);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Log.d("DEBUG","onMeasure w="+width+"  h="+height);

        if (widthMode == MeasureSpec.EXACTLY) {
            Log.d("DEBUG","        width mode EXACTLY");
        } else if (widthMode == MeasureSpec.AT_MOST) {
            Log.d("DEBUG","        width mode AT MOST");
        } else {
            Log.d("DEBUG","        width mode ... A PIACERE!!");
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            Log.d("DEBUG","        width mode EXACTLY");
        } else if (heightMode == MeasureSpec.AT_MOST) {
            Log.d("DEBUG","        width mode AT MOST");
        } else {
            Log.d("DEBUG","        width mode ... A PIACERE!!");
        }

        setMeasuredDimension(getSuggestedMinimumWidth(),getSuggestedMinimumHeight());
    }

    @Override
    protected void onLayout(boolean b, int x1, int y1, int x2, int y2) {
        Log.d("DEBUG","onLayout coordinate x1="+x1+" y1="+y1+"  x2="+x2+"  y2="+y2);
        setMeasuredDimension(getSuggestedMinimumWidth(),getSuggestedMinimumHeight());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint cerchioPaint = new Paint();
        cerchioPaint.setColor(Color.BLUE);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!cerchiGenerati) {
                    Random random = new Random();
                    for (int i = 0; i < 10; i++) {
                        float x = random.nextFloat() * getWidth();
                        float y = random.nextFloat() * getHeight();
                        float raggio = 20 + random.nextFloat() * 80;
                        cerchi.add(new Cerchio(x, y, raggio));
                    }

                    invalidate();
                    cerchiGenerati = true;
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
        for (Cerchio cerchio : cerchi) {
            canvas.drawCircle(cerchio.x, cerchio.y, cerchio.raggio, cerchioPaint);
        }
    }



    public class Cerchio {
        float x, y, raggio;

        public Cerchio(float x, float y, float raggio) {
            this.x = x;
            this.y = y;
            this.raggio = raggio;
        }

        public float getH() {
            return x;
        }

        public float getV() {
            return y;
        }
    }
}
