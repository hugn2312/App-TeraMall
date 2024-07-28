package com.example.teramall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText edname;
    private EditText edemail;
    private EditText edsdt;
    private EditText edpassword;
    private Button btdangky;
    private TextView haveAccount;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        haveAccount = findViewById(R.id.have_account);
        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,Login.class));
                finish();
            }
        });
        anhxadulieu();
        taosukien();
    }
    void anhxadulieu(){
        edname = findViewById(R.id.editTextName);
        edemail = findViewById(R.id.editTextEmail);
        edsdt = findViewById(R.id.editTextsdt);
        edpassword = findViewById(R.id.editTextPassword);
        btdangky = findViewById(R.id.buttonRegister);
    }
    void taosukien(){
        btdangky.setOnClickListener(view -> sukienRegister());
    }
    private void sukienRegister() {
        String userName = edname.getText().toString().trim();
        String email = edemail.getText().toString().trim();
        String phone = edsdt.getText().toString().trim();
        String password = edpassword.getText().toString().trim();
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser user = auth.getCurrentUser();
                Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                updateUser();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Failed to create account", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateUser() {
        String uid = auth.getUid();
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("email",edemail.getText().toString());
        userInfo.put("name",edname.getText().toString());
        userInfo.put("phone number",edsdt.getText().toString());
        userInfo.put("password",edpassword.getText().toString());
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("Users");
        df.child(uid)
                .setValue(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RegisterActivity.this, "Account created", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Fail", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}