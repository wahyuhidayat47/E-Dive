package com.example.e_dive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation app_spalsh, btt;
    ImageView app_logo;
    TextView app_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load animation
        app_spalsh = AnimationUtils.loadAnimation(this, R.anim.app_spalsh);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //load element

        app_logo = findViewById(R.id.app_logo);
        app_title = findViewById(R.id.app_title);

        //run animation

        app_logo.startAnimation(app_spalsh);
        app_title.startAnimation(btt);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // pindah activity
                Intent gotogetstarted = new Intent(MainActivity.this, GetStarted.class);
                startActivity(gotogetstarted);
                finish();
            }
        }, 2000);
    }
}