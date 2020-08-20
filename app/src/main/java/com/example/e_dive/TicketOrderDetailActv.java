package com.example.e_dive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class TicketOrderDetailActv extends AppCompatActivity {

    TextView name_order, total_harga, desc_trip, qty_tiket;
    ImageView photo_item;
    Button btn_refund;
    LinearLayout btn_back;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    DatabaseReference reference;

    Integer valuetotalharga = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_order_detail_actv);

        getUsernameLocal();

        name_order = findViewById(R.id.name_order);
        total_harga = findViewById(R.id.total_harga);
        desc_trip = findViewById(R.id.desc_trip);
        qty_tiket = findViewById(R.id.qty_ticket);
        photo_item = findViewById(R.id.photo_item);
        btn_back = findViewById(R.id.btn_back);
        btn_refund = findViewById(R.id.btn_refund);

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String name_order_baru = bundle.getString("name_order");

        // format currency indonesia
        Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        reference = FirebaseDatabase.getInstance().getReference().child("TiketOrderDetail").child(username_key_new).child(name_order_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name_order.setText(snapshot.child("nama_order").getValue().toString());
                desc_trip.setText(snapshot.child("ketentuan").getValue().toString());
                //qty_tiket.setText(snapshot.child("jumlah_order").getValue()+ " Tikets");
                valuetotalharga = Integer.valueOf(snapshot.child("total_harga").getValue().toString());
                total_harga.setText(formatRupiah.format((double)valuetotalharga));
                Picasso.with(TicketOrderDetailActv.this).load(snapshot.child("url_photo")
                        .getValue().toString()).centerCrop().fit().into(photo_item);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goto = new Intent(TicketOrderDetailActv.this, MyProfileActv.class);
                startActivity(Goto);
            }
        });


        btn_refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goto = new Intent(TicketOrderDetailActv.this, InfoRefundActv.class);
                startActivity(Goto);
            }
        });


    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}