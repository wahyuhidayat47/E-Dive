package com.example.e_dive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn_Actv extends AppCompatActivity {

    TextView newaccount;
    EditText user_name, password_user;
    Button btn_signin;
    ProgressBar progress_bar;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in__actv);

        newaccount = findViewById(R.id.newaccount);
        user_name = findViewById(R.id.user_name);
        password_user = findViewById(R.id.password_user);
        btn_signin = findViewById(R.id.btn_signin);
        progress_bar = findViewById(R.id.progress_bar);




        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_signin.setEnabled(false);
                btn_signin.setText("Loading ...");

                final String username = user_name.getText().toString();
                final String password = password_user.getText().toString();

                if (username.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Username required",Toast.LENGTH_SHORT).show();
                    btn_signin.setEnabled(true);
                    btn_signin.setText("SIGN IN");
                }else {

                    if (password.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Password required",Toast.LENGTH_SHORT).show();
                        btn_signin.setEnabled(true);
                        btn_signin.setText("SIGN IN");
                    }else
                        {
                        //mengabil data dari firebase
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){

                                    String passwordFirebase = snapshot.child("password").getValue().toString();

                                    if (password.equals(passwordFirebase)){
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, username);
                                        editor.apply();

                                        //berpindah activity
                                        Intent gotohome =  new Intent(SignIn_Actv.this, Home_Actv.class);
                                        startActivity(gotohome);
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Wrong Password!",Toast.LENGTH_SHORT).show();
                                        btn_signin.setEnabled(true);
                                        btn_signin.setText("SIGN IN");
                                    }

                                }else {
                                    Toast.makeText(getApplicationContext(),"User not found",Toast.LENGTH_SHORT).show();
                                    btn_signin.setEnabled(true);
                                    btn_signin.setText("SIGN IN");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(),"Check your Connection!",Toast.LENGTH_SHORT).show();
                                btn_signin.setEnabled(true);
                                btn_signin.setText("SIGN IN");
                            }
                        });
                    }
                }

            }
        });


        newaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosignup =  new Intent(SignIn_Actv.this, RegisterOne_actv.class);
                startActivity(gotosignup);
            }
        });

    }

}