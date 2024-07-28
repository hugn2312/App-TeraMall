package com.example.teramall.Detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.teramall.R;
import com.example.teramall.model.Event;
import com.example.teramall.model.Voucher;

public class DetailEvent extends AppCompatActivity {
    TextView tv_name, tv_date, tv_des;
    ImageView img_event;
    Button btback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        tv_name = findViewById(R.id.tv_nameEvent);
        tv_date = findViewById(R.id.tv_date);
        tv_des = findViewById(R.id.tv_description);
        img_event = findViewById(R.id.img_Event);
        btback = findViewById(R.id.btnback_event);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle == null)
        {
            return;
        }
        Event event = (Event) bundle.get("object_event");
        tv_name.setText(event.getNameEvent());
        tv_des.setText(event.getDescription());
        tv_date.setText(event.getDate());
        Glide.with(img_event.getContext())
                .load(event.getImgEvent())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .centerCrop()
                .into(img_event);
    }
}