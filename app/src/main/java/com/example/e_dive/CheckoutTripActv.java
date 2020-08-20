package com.example.e_dive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class CheckoutTripActv extends AppCompatActivity {

    Button btn_minus, btn_plus, btn_pay;
    TextView qty_quota_tiket, total_amount, qty_tiket, name_trip, name_location,desc_trip, date_tiket_trip,time_tiket_trip;
    LinearLayout btn_back;
    ImageView ic_seru;
    Integer valueqtytiket = 1;
    Integer valuequotatiket = 0;
    Integer valuehargatiket = 0;
    Integer valuetotalharga = 0;
    Integer sisaquotatiket = 0;

    DatabaseReference reference,reference2, reference3;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String url_photo = "";
    String desc = "";

    //generate nomor secara random
    Integer nomor_transaksi = new Random().nextInt();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_trip_actv);

        getUsernameLocal();

        btn_minus = findViewById(R.id.btn_minus);
        btn_plus = findViewById(R.id.btn_plus);
        btn_pay = findViewById(R.id.btn_pay);
        qty_quota_tiket = findViewById(R.id.qty_quota_tiket);
        total_amount = findViewById(R.id.total_amount);
        qty_tiket = findViewById(R.id.qty_tiket);
        btn_back = findViewById(R.id.btn_back);
        ic_seru = findViewById(R.id.ic_seru);
        name_trip = findViewById(R.id.name_trip);
        name_location = findViewById(R.id.name_location);
        desc_trip = findViewById(R.id.desc_trip);
        time_tiket_trip = findViewById(R.id.time_tiket_trip);
        date_tiket_trip = findViewById(R.id.date_tiket_trip);

        qty_tiket.setText(valueqtytiket.toString());

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_trip_baru = bundle.getString("jenis_trip");


        // format currency indonesia
        Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


        // mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Trip").child(jenis_trip_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name_trip.setText(snapshot.child("nama_trip").getValue().toString());
                name_location.setText(snapshot.child("location").getValue().toString());
                qty_quota_tiket.setText(snapshot.child("quota").getValue().toString()+ " person");
                date_tiket_trip.setText(snapshot.child("date_trip").getValue().toString());
                time_tiket_trip.setText(snapshot.child("time_trip").getValue().toString());
                url_photo = snapshot.child("url_thumbnail").getValue().toString();
                desc = snapshot.child("ketentuan").getValue().toString();

                valuehargatiket = Integer.valueOf(snapshot.child("harga_pax").getValue().toString());
                valuequotatiket = Integer.valueOf(snapshot.child("quota").getValue().toString());
                valuetotalharga = valuehargatiket * valueqtytiket;
                total_amount.setText(formatRupiah.format((double)valuetotalharga));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // default button minus hide
        btn_minus.animate().alpha(0).setDuration(300).start();
        btn_minus.setEnabled(false);
        ic_seru.setVisibility(View.GONE);

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueqtytiket+=1;
                qty_tiket.setText(valueqtytiket.toString());
                if(valueqtytiket>1){
                    btn_minus.animate().alpha(1).setDuration(300).start();
                    btn_minus.setEnabled(true);
                }
                valuetotalharga = valuehargatiket * valueqtytiket;
                total_amount.setText(formatRupiah.format((double)valuetotalharga));
                if(valueqtytiket > valuequotatiket){
                    btn_pay.animate().translationY(250).alpha(0).setDuration(300).start();
                    btn_pay.setEnabled(false);
                    qty_quota_tiket.setTextColor(Color.parseColor("#E91E63"));
                    ic_seru.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueqtytiket-=1;
                qty_tiket.setText(valueqtytiket.toString());
                if(valueqtytiket < 2){
                    btn_minus.animate().alpha(0).setDuration(300).start();
                    btn_minus.setEnabled(false);
                }
                valuetotalharga = valuehargatiket * valueqtytiket;
                total_amount.setText(formatRupiah.format((double)valuetotalharga));
                if(valueqtytiket <= valuequotatiket){
                    btn_pay.animate().translationY(0).alpha(1).setDuration(300).start();
                    btn_pay.setEnabled(true);
                    qty_quota_tiket.setTextColor(Color.parseColor("#FBB47C"));
                    ic_seru.setVisibility(View.GONE);
                }
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_pay.setEnabled(false);
                btn_pay.setText("Loading ...");

                reference2 = FirebaseDatabase.getInstance().getReference()
                        .child("TiketOrderDetail").child(username_key_new).child(name_trip.getText().toString() + nomor_transaksi);
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference2.getRef().child("id_ticket").setValue(name_trip.getText().toString() + nomor_transaksi);
                        reference2.getRef().child("nama_order").setValue(name_trip.getText().toString());
                        reference2.getRef().child("lokasi").setValue(name_location.getText().toString());
                        reference2.getRef().child("date_trip").setValue(date_tiket_trip.getText().toString());
                        reference2.getRef().child("time_trip").setValue(time_tiket_trip.getText().toString());
                        reference2.getRef().child("jumlah_order").setValue(valueqtytiket.toString());
                        reference2.getRef().child("total_harga").setValue(valuetotalharga.toString());
                        reference2.getRef().child("url_photo").setValue(url_photo.toString());
                        reference2.getRef().child("ketentuan").setValue(desc.toString());


                        Intent gotoCheckout = new Intent(CheckoutTripActv.this, Success_TripActv.class);
                        startActivity(gotoCheckout);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                reference3 = FirebaseDatabase.getInstance().getReference().child("Trip").child(jenis_trip_baru);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        sisaquotatiket = valuequotatiket - valueqtytiket;
                        reference3.getRef().child("quota").setValue(sisaquotatiket);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoCheckout = new Intent(CheckoutTripActv.this, Home_Actv.class);
                startActivity(gotoCheckout);
            }
        });
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}