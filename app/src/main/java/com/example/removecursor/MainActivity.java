package com.example.removecursor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private TextView clickCounter;
    private Gioco gameView;
    int counter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickCounter = findViewById(R.id.contClick);
        FrameLayout container = (FrameLayout) findViewById(R.id.rettangoloGioco);

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("TENTATIVI");
        }else{
            counter=0;
        }

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Point size = new Point();
        display.getSize(size);
        int screen_w = size.x;
        int screen_h = size.y;
        int dp_w = (int)(screen_w / metrics.density);
        int dp_h = (int)(screen_h / metrics.density);

        Log.d("DEBUG", "Display size");
        Log.d("DEBUG", "   Physical w=" + screen_w + ", h=" + screen_h);
        Log.d("DEBUG", "   Density ind. w=" + dp_w + ", h=" + dp_h);

        gameView = new Gioco(getApplicationContext(), screen_w, screen_h);
        container.addView(gameView);

        gameView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {

                    float touchX = event.getX();
                    float touchY = event.getY();
                    float R = 200;

                    counter++;
                    clickCounter.setText("mosse: " + counter);

                    Iterator<Gioco.Cerchio> iterator = gameView.cerchi.iterator();
                    boolean cerchiRimossi = false;

                    while (iterator.hasNext()) {
                        Gioco.Cerchio cerchio = iterator.next();
                        float notePositionX = cerchio.getH();
                        float notePositionY = cerchio.getV();

                        if (Math.abs(touchX - notePositionX) < R && Math.abs(touchY - notePositionY) < R) {
                            iterator.remove();
                            cerchiRimossi = true;
                        }
                    }

                    if (cerchiRimossi) {
                        gameView.invalidate();
                    }

                } else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                    if (gameView.cerchi.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "hai vinto con " + counter + " mosse", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                }
                return true;
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("TENTATIVI", counter);
    }
}
