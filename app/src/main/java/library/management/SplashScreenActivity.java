package library.management;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.time.Instant;

import library.management.Controller.StoreData;

public class SplashScreenActivity extends AppCompatActivity {



    private static int SPASH_TIME_OUT = 5000;

    private StoreData controller;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mContentView = findViewById(R.id.fullscreen_content);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);

        controller = new StoreData(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent;
                if(controller.getCurrentUser() != null)
                    homeIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                else
                    homeIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPASH_TIME_OUT);
    }

}
