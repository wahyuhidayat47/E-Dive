package com.example.e_dive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Home_Actv extends AppCompatActivity {

    LinearLayout name_rajaampat, name_bcd , name_pramuka,name_tanjungbira, name_fins, name_regulator, name_tabung,
            name_weightbelt, name_wetsuit, name_mask, my_ticket;
    TextView full_name,bio;
    ImageView img_user,log_out;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    DatabaseReference reference;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__actv);

        getUsernameLocal();

        name_rajaampat = findViewById(R.id.name_rajaampat);
        name_bcd = findViewById(R.id.name_bcd);
        full_name = findViewById(R.id.full_name);
        bio = findViewById(R.id.bio);
        img_user = findViewById(R.id.img_user);
        name_pramuka = findViewById(R.id.name_pramuka);
        name_tanjungbira = findViewById(R.id.name_tanjungbira);
        name_fins = findViewById(R.id.name_fins);
        name_regulator = findViewById(R.id.name_regulator);
        name_tabung = findViewById(R.id.name_tabung);
        name_weightbelt = findViewById(R.id.name_weightbelt);
        name_wetsuit = findViewById(R.id.name_wetsuit);
        name_mask = findViewById(R.id.name_mask);
        my_ticket = findViewById(R.id.my_ticket);
        log_out = findViewById(R.id.log_out);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                full_name.setText(snapshot.child("full_name").getValue().toString());
                bio.setText(snapshot.child("user_bio").getValue().toString());
                Picasso.with(Home_Actv.this).load(snapshot.child("url_photo_profile")
                        .getValue().toString()).centerCrop().fit().into(img_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        name_rajaampat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Gotorajaampat = new Intent(Home_Actv.this,
                        TicketDetailActv.class);
                Gotorajaampat.putExtra("jenis_trip","Raja Ampat");
                startActivity(Gotorajaampat);
            }
        });
        name_pramuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Gotorajaampat = new Intent(Home_Actv.this,
                        TicketDetailActv.class);
                Gotorajaampat.putExtra("jenis_trip","Pramuka Island");
                startActivity(Gotorajaampat);
            }
        });
        name_tanjungbira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Gotorajaampat = new Intent(Home_Actv.this,
                        TicketDetailActv.class);
                Gotorajaampat.putExtra("jenis_trip","Tanjung Bira");
                startActivity(Gotorajaampat);
            }
        });

        name_bcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goto = new Intent(Home_Actv.this, EquipmentActv.class);
                Goto.putExtra("jenis_equipment","BCD AmScuD");
                startActivity(Goto);
            }
        });
        name_fins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goto = new Intent(Home_Actv.this, EquipmentActv.class);
                Goto.putExtra("jenis_equipment","Fins AmScuD");
                startActivity(Goto);
            }
        });
        name_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goto = new Intent(Home_Actv.this, EquipmentActv.class);
                Goto.putExtra("jenis_equipment","Mask AmScuD");
                startActivity(Goto);
            }
        });
        name_regulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goto = new Intent(Home_Actv.this, EquipmentActv.class);
                Goto.putExtra("jenis_equipment","Regulator AmScuD");
                startActivity(Goto);
            }
        });
        name_tabung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goto = new Intent(Home_Actv.this, EquipmentActv.class);
                Goto.putExtra("jenis_equipment","Tabung");
                startActivity(Goto);
            }
        });
        name_weightbelt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goto = new Intent(Home_Actv.this, EquipmentActv.class);
                Goto.putExtra("jenis_equipment","Weightbelt AmScuD");
                startActivity(Goto);
            }
        });

        name_wetsuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goto = new Intent(Home_Actv.this, EquipmentActv.class);
                Goto.putExtra("jenis_equipment","Wetsuite AmScuD");
                startActivity(Goto);
            }
        });

        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goto = new Intent(Home_Actv.this, MyProfileActv.class);
                startActivity(Goto);
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // menghapus nilai dari  username local
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.apply();
                Intent logout =  new Intent(Home_Actv.this,SignIn_Actv.class);
                startActivity(logout);
                finish();
            }
        });
        my_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goto = new Intent(Home_Actv.this, MyProfileActv.class);
                startActivity(Goto);
            }
        });
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}