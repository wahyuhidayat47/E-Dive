package com.example.e_dive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Success_RegisterActv extends AppCompatActivity {

    Button btn_explore;
    Animation btt, ttb;
    ImageView ic_logo_success;
    TextView title, sub_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success__register_actv);

        ic_logo_success = findViewById(R.id.ic_logo_success);
        title = findViewById(R.id.title);
        sub_title = findViewById(R.id.sub_title);
        btn_explore = findViewById(R.id.btn_explore);


        //load animation
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt );

        //run animation

        ic_logo_success.startAnimation(ttb);
        sub_title.startAnimation(btt);
        title.startAnimation(btt);
        btn_explore.startAnimation(btt);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotoHome = new Intent(Success_RegisterActv.this, Home_Actv.class);
                startActivity(GotoHome);
            }
        });
    }
}