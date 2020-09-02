package com.example.e_dive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyProfileActv extends AppCompatActivity {

    Button btn_edit_profile, btn_signout;
    ImageView img_user, xphototicket, backhome;
    TextView user_name, user_bio, xname_order, xjumlah_order, xtotal_harga, xid_tiket;
    RecyclerView my_ticketplace;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    DatabaseReference reference, reference2;
    ArrayList<My_ticket> list;
    Tiket_Adapter tiket_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_actv);

        getUsernameLocal();

        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        btn_signout = findViewById(R.id.btn_signout);
        img_user = findViewById(R.id.img_user);
        xphototicket = findViewById(R.id.xphototicket);
        user_name = findViewById(R.id.user_name);
        user_bio = findViewById(R.id.user_bio);
        xname_order = findViewById(R.id.xname_order);
        xjumlah_order = findViewById(R.id.xjumlah_order);
        xtotal_harga = findViewById(R.id.xtotal_harga);
        my_ticketplace = findViewById(R.id.my_ticketplace);
        backhome = findViewById(R.id.backhome);
        xid_tiket = findViewById(R.id.xid_tiket);

        my_ticketplace.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<My_ticket>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_name.setText(snapshot.child("full_name").getValue().toString());
                user_bio.setText(snapshot.child("user_bio").getValue().toString());
                Picasso.with(MyProfileActv.this).load(snapshot.child("url_photo_profile")
                        .getValue().toString()).centerCrop().fit().into(img_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goto = new Intent(MyProfileActv.this, EditProfileActv.class);
                startActivity(Goto);
            }
        });
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goto = new Intent(MyProfileActv.this, Home_Actv.class);
                startActivity(Goto);
            }
        });
        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // menghapus nilai dari  username local
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.apply();
                Intent logout =  new Intent(MyProfileActv.this,SignIn_Actv.class);
                startActivity(logout);
                finish();
            }
        });

        reference2 = FirebaseDatabase.getInstance().getReference().child("TiketOrderDetail").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    My_ticket p = dataSnapshot1.getValue(My_ticket.class);
                    list.add(p);
                }
                tiket_adapter = new Tiket_Adapter(MyProfileActv.this,list);
                my_ticketplace.setAdapter(tiket_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}