package com.example.e_dive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class TicketDetailActv extends AppCompatActivity {

    Button btn_buy_trip;
    LinearLayout btn_back;
    ImageView image_location;
    TextView name_location, name_trips, acomodation, food_drink, equipment, short_desc, price;

    DatabaseReference reference;

    Integer valueprice =0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail_actv);


        btn_buy_trip = findViewById(R.id.btn_buy_trip);
        btn_back = findViewById(R.id.btn_back);
        image_location = findViewById(R.id.image_location);
        name_trips = findViewById(R.id.name_trips);
        name_location = findViewById(R.id.name_location);
        acomodation = findViewById(R.id.acomodation);
        food_drink = findViewById(R.id.food_drink);
        equipment = findViewById(R.id.equipment);
        short_desc = findViewById(R.id.short_desc);
        price = findViewById(R.id.price);

        Bundle bundle = getIntent().getExtras();
        final String jenis_trip_baru = bundle.getString("jenis_trip");

        Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        reference = FirebaseDatabase.getInstance().getReference().child("Trip").child(jenis_trip_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // mengisi data sesuai dengan yang ada didatabase
                name_trips.setText(snapshot.child("nama_trip").getValue().toString());
                name_location.setText(snapshot.child("location").getValue().toString());
                acomodation.setText(snapshot.child("acomodation").getValue().toString());
                food_drink.setText(snapshot.child("food_drink").getValue().toString());
                equipment.setText(snapshot.child("equipment").getValue().toString());
                short_desc.setText(snapshot.child("short_desc").getValue().toString());

                valueprice = Integer.valueOf(snapshot.child("harga_pax").getValue().toString());
                price.setText(formatRupiah.format((double)valueprice));
                Picasso.with(TicketDetailActv.this).load(snapshot.child("url_thumbnail")
                        .getValue().toString()).centerCrop().fit().into(image_location);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosignup = new Intent(TicketDetailActv.this, Home_Actv.class);
                startActivity(gotosignup);
            }
        });
        btn_buy_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoCheckout = new Intent(TicketDetailActv.this, CheckoutTripActv.class);
                gotoCheckout.putExtra("jenis_trip",jenis_trip_baru);
                startActivity(gotoCheckout);
            }
        });

    }
}