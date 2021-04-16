package com.jigangseon.psg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import net.daum.mf.map.api.MapView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        ProgressBar loading = (ProgressBar)findViewById(R.id.loading_bar);
        int time = 1000;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MapActivity.class);
                startActivity(intent);
                finish();
            }
        },time);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}