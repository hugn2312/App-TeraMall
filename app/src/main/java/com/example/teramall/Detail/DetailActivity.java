package com.example.teramall.Detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.teramall.MyAplication;
import com.example.teramall.R;
import com.example.teramall.model.Deal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {
    TextView tvArea, tvArea1, tvDes, tvLocate,tvPrice, tvSDes;
    ImageView imDetail;
    Button btBack,btFav;
    private FirebaseAuth firebaseAuth;
    boolean isInMyFav = false;
    String nameDeal, location, price, surl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //anh xa
        tvArea = findViewById(R.id.tv_area);
        tvArea1 = findViewById(R.id.tv_area1);
        tvSDes = findViewById(R.id.s_description);
        tvLocate = findViewById(R.id.tv_location);
        tvPrice = findViewById(R.id.tv_price);
        tvDes = findViewById(R.id.descriptonTV);
        imDetail = findViewById(R.id.im_detail);
        btBack = findViewById(R.id.btBack);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btFav = findViewById(R.id.bt_favdeal);

        firebaseAuth = FirebaseAuth.getInstance();
        Bundle bundle = getIntent().getExtras();
        if(bundle == null)
        {
            return;
        }
        Deal deal = (Deal) bundle.get("object_ground");
        tvArea.setText(deal.getArea());
        tvArea1.setText(deal.getArea1());
        tvSDes.setText(deal.getShortDescription());
        tvLocate.setText(deal.getLocation());
        tvPrice.setText(deal.getPrice());
        tvDes.setText(deal.getDescription());
        Glide.with(imDetail.getContext())
                .load(deal.getSurl())
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(imDetail);
        //
        nameDeal = tvSDes.getText().toString();
        location = tvLocate.getText().toString();
        price = tvPrice.getText().toString();
        surl = deal.getSurl();
        if (firebaseAuth.getCurrentUser() != null)
        {
            checkIsFav(nameDeal);
        }
        btFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firebaseAuth.getCurrentUser() == null){
                    Toast.makeText(DetailActivity.this, "You're not logged in", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (isInMyFav){
                        MyAplication.RemoveFromFavList(DetailActivity.this,nameDeal);
                    }else {
                        MyAplication.addToFavList(DetailActivity.this,nameDeal,price,location,surl);
                    }
                }
            }
        });
    }
    private void checkIsFav(String nameClub){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Favourites");
        reference.child(nameClub)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        isInMyFav = snapshot.exists();
                        if (isInMyFav){
                            btFav.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.baseline_favorite_24, 0, 0);
                            btFav.setText("Remove Favourite");
                            btFav.setTextColor(getApplication().getResources().getColor(R.color.white));
                            btFav.setTextSize(20);
                        }
                        else{
                            btFav.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.baseline_favorite_border_24, 0, 0);
                            btFav.setText("Add Favourite");
                            btFav.setTextColor(getApplication().getResources().getColor(R.color.white));
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }
}