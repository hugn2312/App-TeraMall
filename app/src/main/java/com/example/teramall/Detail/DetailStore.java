package com.example.teramall.Detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.teramall.R;
import com.example.teramall.model.Deal;
import com.example.teramall.model.Store;
import com.google.firebase.auth.FirebaseAuth;

public class DetailStore extends AppCompatActivity {
    Button btback;
    TextView tvName, tvLocation, tvAddress, tvTime, tvWebsite, tvPhone;
    ImageView imLogo, imShop;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_store);
        btback = findViewById(R.id.btBack_detail);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //anh xa
        tvName = findViewById(R.id.nameShop);
        tvLocation = findViewById(R.id.location_floor);
        tvAddress = findViewById(R.id.address);
        tvTime = findViewById(R.id.time);
        tvWebsite = findViewById(R.id.website);
        tvPhone = findViewById(R.id.phoneNumber);
        imLogo = findViewById(R.id.img_logoShop);
        imShop = findViewById(R.id.img_shop);
        firebaseAuth = FirebaseAuth.getInstance();
        Bundle bundle = getIntent().getExtras();
        if(bundle == null)
        {
            return;
        }
        Store store = (Store) bundle.get("object_store");
        tvName.setText(store.getName());
        tvLocation.setText(store.getLocation());
        tvAddress.setText(store.getAddress());
        tvTime.setText(store.getTime());
        tvWebsite.setText(store.getWebsite());
        tvPhone.setText(store.getPhoneNumber());
        Glide.with(imLogo.getContext())
                .load(store.getImagelogo())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(imLogo);
        Glide.with(imShop.getContext())
                .load(store.getImageShop())
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(imShop);



    }
}