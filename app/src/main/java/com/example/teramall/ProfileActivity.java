package com.example.teramall;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    TextView tvname,tvemail,name1,email1,tvphone,tvpassword;
    Button btback;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvname = findViewById(R.id.nameTv);
        tvemail = findViewById(R.id.emailTv);
        name1 = findViewById(R.id.nametv1);
        email1 = findViewById(R.id.emailtv1);
        tvphone = findViewById(R.id.phonetv);
        tvpassword = findViewById(R.id.passwordtv);
        btback = findViewById(R.id.btnback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null){
            return;
        }else {
            Loaduserinfo();
        }
    }

    private void Loaduserinfo() {
        Log.d(TAG, "loadUserInfo: Loading user info of user"+ auth.getUid());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = ""+snapshot.child("name").getValue();
                        String email = ""+snapshot.child("email").getValue();
                        String phone = ""+snapshot.child("phone number").getValue();
                        String password = ""+snapshot.child("password").getValue();
                        tvname.setText(name);
                        tvemail.setText(email);
                        name1.setText(name);
                        email1.setText(email);
                        tvphone.setText(phone);
                        tvpassword.setText(password);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}