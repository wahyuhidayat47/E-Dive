package com.example.e_dive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOne_actv extends AppCompatActivity {

    EditText username, mail, password;
    Button btn_next;
    LinearLayout btn_back;
    DatabaseReference reference,reference2;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one_actv);

        btn_next = findViewById(R.id.btn_next);
        btn_back = findViewById(R.id.btn_back);
        username = findViewById(R.id.username);
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backto = new Intent(RegisterOne_actv.this, SignIn_Actv.class);
                startActivity(backto);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_next.setEnabled(false);
                btn_next.setText("Loading ...");

                final String xusername = username.getText().toString();
                final String xmail = mail.getText().toString();
                final String xpass = password.getText().toString();
                final String PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


                if (xusername.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username required", Toast.LENGTH_SHORT).show();
                    btn_next.setEnabled(true);
                    btn_next.setText("NEXT");
                } else if (xmail.isEmpty() || !xmail.matches(PATTERN)) {
                    Toast.makeText(getApplicationContext(), "Fill Mail", Toast.LENGTH_SHORT).show();
                    btn_next.setEnabled(true);
                    btn_next.setText("NEXT");
                } else if (xpass.isEmpty() || password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password required and minimum 6 character", Toast.LENGTH_SHORT).show();
                    btn_next.setEnabled(true);
                    btn_next.setText("NEXT");
                } else {
                    reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(xusername);
                    reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(getApplicationContext(), "Username Already Exsist", Toast.LENGTH_SHORT).show();
                                btn_next.setEnabled(true);
                                btn_next.setText("NEXT");
                            } else if (xusername.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Fill Username", Toast.LENGTH_SHORT).show();
                                btn_next.setEnabled(true);
                                btn_next.setText("NEXT");
                            } else {


                                //menyimpan data ke local storage
                                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(username_key, username.getText().toString());
                                editor.apply();

                                // simpan ke database
                                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        snapshot.getRef().child("username").setValue(username.getText().toString());
                                        snapshot.getRef().child("password").setValue(password.getText().toString());
                                        snapshot.getRef().child("mail").setValue(mail.getText().toString());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                // berpindah activity
                                Intent nextto = new Intent(RegisterOne_actv.this, RegisterTwo_Actv.class);
                                startActivity(nextto);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });

    }




}