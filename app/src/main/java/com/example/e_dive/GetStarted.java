package com.example.e_dive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GetStarted extends AppCompatActivity {

    Button btn_signin, btn_signup;
    Animation ttb,btt;
    TextView title;
    LinearLayout layoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        //load animation
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        btt = AnimationUtils.loadAnimation(this, R.anim.btt );

        btn_signin = findViewById(R.id.btn_signin);
        btn_signup = findViewById(R.id.btn_signup);
        title = findViewById(R.id.title);
        layoutbtn = findViewById(R.id.layoutbtn);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosign =  new Intent(GetStarted.this, SignIn_Actv.class);
                startActivity(gotosign);
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosign =  new Intent(GetStarted.this, RegisterOne_actv.class);
                startActivity(gotosign);
                finish();
            }
        });

        //run animation

        title.startAnimation(ttb);
        layoutbtn.startAnimation(btt);


    }
}