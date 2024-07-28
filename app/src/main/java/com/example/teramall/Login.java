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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
public class Login extends AppCompatActivity {

    EditText edTaikhoan,edMatkhau;
    TextView tvDangnhap;
    TextView btRegister;
    Button btBack;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btBack = findViewById(R.id.btnBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
            }
        });
        btRegister = findViewById(R.id.btRegister);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        anhxa();
        taosukien();
    }
    private void anhxa(){
        edTaikhoan = findViewById(R.id.edtaikhoan);
        edMatkhau = findViewById(R.id.edmatkhau);
        tvDangnhap = findViewById(R.id.btDangnhap);
    }
    private void taosukien(){
        tvDangnhap.setOnClickListener(view -> onLickSignIn());
    }
    private void onLickSignIn(){
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        String email = edTaikhoan.getText().toString().trim();
        String password = edMatkhau.getText().toString().trim();
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(Login.this, "Login successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}