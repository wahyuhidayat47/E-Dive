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

public class Success_TripActv extends AppCompatActivity {

    Button btn_view_tiket, btn_home;
    Animation btt, ttb;
    ImageView ic_logo_success;
    TextView title, sub_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_equipment_actv);

        btn_home = findViewById(R.id.btn_home);
        btn_view_tiket = findViewById(R.id.btn_view_tiket);
        ic_logo_success = findViewById(R.id.ic_logo_success);
        title = findViewById(R.id.title);
        sub_title = findViewById(R.id.sub_title);

        //load animation
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt );

        //run animation

        ic_logo_success.startAnimation(ttb);
        sub_title.startAnimation(btt);
        title.startAnimation(btt);
        btn_home.startAnimation(btt);
        btn_view_tiket.startAnimation(btt);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoHome = new Intent(Success_TripActv.this, Home_Actv.class);
                startActivity(gotoHome);
            }
        });

        btn_view_tiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototiket = new Intent(Success_TripActv.this, MyProfileActv.class);
                startActivity(gototiket);
            }
        });

    }
}