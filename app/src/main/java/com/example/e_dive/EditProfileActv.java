package com.example.e_dive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfileActv extends AppCompatActivity {

    EditText xfull_name, xbio, xpassword, xmail, xphone;
    Button btn_save, btn_plus;
    LinearLayout btn_back;
    ImageView img_user;

    DatabaseReference reference;
    StorageReference storage, storageReference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    Uri photo_location;
    Integer photo_max = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_actv);

        getUsernameLocal();

        img_user = findViewById(R.id.img_user);
        xfull_name = findViewById(R.id.xfull_name);
        xbio = findViewById(R.id.xbio);
        xphone = findViewById(R.id.xphone);
        xmail = findViewById(R.id.xmail);
        xpassword = findViewById(R.id.xpassword);
        btn_save = findViewById(R.id.btn_save);
        btn_back = findViewById(R.id.btn_back);
        btn_plus = findViewById(R.id.btn_plus);


        reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(username_key_new);
        storage = FirebaseStorage.getInstance().getReference().child("PhotoUsers").child(username_key_new);
        storageReference = FirebaseStorage.getInstance().getReference().child("PhotoUsers").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xfull_name.setText(dataSnapshot.child("full_name").getValue().toString());
                xbio.setText(dataSnapshot.child("user_bio").getValue().toString());
                xphone.setText(dataSnapshot.child("phone_number").getValue().toString());
                xpassword.setText(dataSnapshot.child("password").getValue().toString());
                xmail.setText(dataSnapshot.child("mail").getValue().toString());
                Picasso.with(EditProfileActv.this)
                        .load(dataSnapshot.child("url_photo_profile")
                                .getValue().toString()).centerCrop().fit().into(img_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //berpidah activity
                Intent gotoprof = new Intent(EditProfileActv.this,MyProfileActv.class);
                startActivity(gotoprof);

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_save.setEnabled(false);
                btn_save.setText("Loading ...");

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("full_name").setValue(xfull_name.getText().toString());
                        dataSnapshot.getRef().child("user_bio").setValue(xbio.getText().toString());
                        dataSnapshot.getRef().child("mail").setValue(xmail.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(xpassword.getText().toString());
                        dataSnapshot.getRef().child("phone_number").setValue(xphone.getText().toString());
                        Intent gotohome = new Intent(EditProfileActv.this,MyProfileActv.class);
                        startActivity(gotohome);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                // validasi untuk file (apakah ada ?)
                if (photo_location != null) {
                    final StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "." +
                            getFileExtension(photo_location));

                    storageReference1.putFile(photo_location).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String downloadUrl = uri.toString();
                                        reference.getRef().child("url_photo_profile").setValue(downloadUrl);
                                        //berpidah activity
                                        Intent gotohome = new Intent(EditProfileActv.this,MyProfileActv.class);
                                        startActivity(gotohome);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"Failed Update to Database",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }

            }
        });
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });


    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void  findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(img_user);
        }
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}