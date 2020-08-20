package com.example.e_dive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
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
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class EquipmentActv extends AppCompatActivity {

    Button btn_rent, btn_plus, btn_minus;
    TextView qty_ticket,total_amount,qty_equipment, name_equipment, harga;
    ImageView image_equipment, image_equipment1;
    LinearLayout btn_back;
    ImageView ic_seru;
    Integer valuejumlahtiket = 1;
    Integer availability = 0;
    Integer valuetotalharga = 0;
    Integer valuehargatiket = 0;
    Integer sisaquota = 0;

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
        setContentView(R.layout.activity_equipment_actv);

        getUsernameLocal();

        btn_rent = findViewById(R.id.btn_rent);
        btn_back = findViewById(R.id.btn_back);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        qty_ticket = findViewById(R.id.qty_ticket);
        total_amount = findViewById(R.id.total_amount);
        qty_equipment = findViewById(R.id.qty_equipment);
        ic_seru = findViewById(R.id.ic_seru);
        name_equipment = findViewById(R.id.name_equipment);
        harga = findViewById(R.id.harga);
        image_equipment = findViewById(R.id.image_equipment);
        image_equipment1 = findViewById(R.id.image_equipment1);


        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_equipment_baru = bundle.getString("jenis_equipment");



        qty_ticket.setText(valuejumlahtiket.toString());

        Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        reference = FirebaseDatabase.getInstance().getReference().child("Equipment").child(jenis_equipment_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name_equipment.setText(snapshot.child("nama_equipment").getValue().toString());
                valuehargatiket = Integer.valueOf(snapshot.child("harga_unit").getValue().toString());
                harga.setText(formatRupiah.format((double)valuehargatiket));
                qty_equipment.setText(snapshot.child("stock").getValue().toString());
                Picasso.with(EquipmentActv.this).load(snapshot.child("url_photo")
                        .getValue().toString()).centerCrop().fit().into(image_equipment);
                Picasso.with(EquipmentActv.this).load(snapshot.child("url_photo")
                        .getValue().toString()).centerCrop().fit().into(image_equipment1);
                url_photo = snapshot.child("url_photo").getValue().toString();
                desc = snapshot.child("keterangan").getValue().toString();

                availability = Integer.valueOf(snapshot.child("stock").getValue().toString());
                valuetotalharga = valuehargatiket * valuejumlahtiket;
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
                valuejumlahtiket+=1;
                qty_ticket.setText(valuejumlahtiket.toString());
                if(valuejumlahtiket>1){
                    btn_minus.animate().alpha(1).setDuration(300).start();
                    btn_minus.setEnabled(true);
                }
                valuetotalharga = valuehargatiket * valuejumlahtiket;
                total_amount.setText(formatRupiah.format((double)valuetotalharga));
                if(valuejumlahtiket > availability){
                    btn_rent.animate().translationY(250).alpha(0).setDuration(300).start();
                    btn_rent.setEnabled(false);
                    qty_equipment.setTextColor(Color.parseColor("#E91E63"));
                    ic_seru.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlahtiket-=1;
                qty_ticket.setText(valuejumlahtiket.toString());
                if(valuejumlahtiket < 2){
                    btn_minus.animate().alpha(0).setDuration(300).start();
                    btn_minus.setEnabled(false);
                }
                valuetotalharga = valuehargatiket * valuejumlahtiket;
                total_amount.setText(formatRupiah.format((double)valuetotalharga));
                if(valuejumlahtiket <= availability){
                    btn_rent.animate().translationY(0).alpha(1).setDuration(300).start();
                    btn_rent.setEnabled(true);
                    qty_equipment.setTextColor(Color.parseColor("#FBB47C"));
                    ic_seru.setVisibility(View.GONE);
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosignup = new Intent(EquipmentActv.this, Home_Actv.class);
                startActivity(gotosignup);
            }
            });
        btn_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference2 = FirebaseDatabase.getInstance().getReference()
                        .child("TiketOrderDetail").child(username_key_new).child(name_equipment.getText().toString() + nomor_transaksi);
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference2.getRef().child("id_ticket").setValue(name_equipment.getText().toString() + nomor_transaksi);
                        reference2.getRef().child("nama_order").setValue(name_equipment.getText().toString());
                        reference2.getRef().child("jumlah_order").setValue(qty_ticket.getText().toString());
                        reference2.getRef().child("total_harga").setValue(valuetotalharga.toString());
                        reference2.getRef().child("url_photo").setValue(url_photo);
                        reference2.getRef().child("ketentuan").setValue(desc);

                        Intent gotosignup = new Intent(EquipmentActv.this, SuccessEquipmentActv.class);
                        startActivity(gotosignup);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                reference3 = FirebaseDatabase.getInstance().getReference().child("Equipment").child(jenis_equipment_baru);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        sisaquota = availability - valuejumlahtiket;
                        reference3.getRef().child("stock").setValue(sisaquota);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
}
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}