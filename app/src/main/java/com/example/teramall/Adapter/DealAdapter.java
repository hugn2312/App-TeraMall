package com.example.teramall.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.teramall.Detail.DetailActivity;
import com.example.teramall.R;
import com.example.teramall.model.Deal;

import java.util.List;

public class DealAdapter extends ArrayAdapter<Deal> {
    CardView layout;
    TextView short_descripton;
    TextView location;
    TextView price;
    ImageView img_deal;
    Context mcontext;
    public DealAdapter(@NonNull Context context, int resource, List<Deal>lstDeal) {
        super(context, resource, lstDeal);
        this.mcontext = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Deal deal = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ground, parent, false);
        }
        layout = convertView.findViewById(R.id.layout_ground);
        short_descripton = convertView.findViewById(R.id.short_desciption);
        location = convertView.findViewById(R.id.location);
        price = convertView.findViewById(R.id.price);
        img_deal = convertView.findViewById(R.id.img_deal);
        short_descripton.setText(deal.getShortDescription());
        location.setText(deal.getLocation());
        price.setText(deal.getPrice());
        Glide.with(img_deal.getContext())
                .load(deal.getSurl())
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(img_deal);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGotoDetail(deal);
            }
        });
        return convertView;
    }

    private void onClickGotoDetail(Deal deal) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_ground", deal);
        intent.putExtras(bundle);
        mcontext.startActivity(intent);
    }
}
