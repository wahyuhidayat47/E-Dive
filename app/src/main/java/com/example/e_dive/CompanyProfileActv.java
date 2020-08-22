package com.example.e_dive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CompanyProfileActv extends AppCompatActivity {

    Button btn_getstarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile_actv);

        btn_getstarted = findViewById(R.id.btn_getstarted);

        btn_getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosign = new Intent(CompanyProfileActv.this, GetStarted.class);
                startActivity(gotosign);

            }
        });
    }
}