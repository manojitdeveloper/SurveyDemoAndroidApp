package com.surveydemoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.surveydemoapp.Global.Constants;
import com.surveydemoapp.HomeSection.HomeFragmentActivity;

public class Splash extends Activity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(Splash.this, Constants.SPLASH_DISPLAY_LENGTH);
    }


    @Override
    public void run() {

        startActivity(new Intent(Splash.this, HomeFragmentActivity.class));
        finish();
    }
}
