package com.example.teramall.Detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.teramall.R;
import com.example.teramall.model.Store;
import com.example.teramall.model.Voucher;

public class DetailVoucher extends AppCompatActivity {
    Button btback;
    TextView tv_name,tv_des;
    ImageView imVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_voucher);
        btback = findViewById(R.id.btBack_voucher);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imVoucher = findViewById(R.id.imgVoucher);
        tv_name = findViewById(R.id.nameVoucher);
        tv_des = findViewById(R.id.desVoucher);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null)
        {
            return;
        }
        Voucher voucher = (Voucher) bundle.get("object_voucher");
        tv_name.setText(voucher.getNameVoucher());
        tv_des.setText(voucher.getDescription());
        Glide.with(imVoucher.getContext())
                .load(voucher.getImgVoucher())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .centerCrop()
                .into(imVoucher);
    }
}