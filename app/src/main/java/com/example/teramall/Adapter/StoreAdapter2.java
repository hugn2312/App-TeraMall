package com.example.teramall.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.teramall.Detail.DetailActivity;
import com.example.teramall.Detail.DetailStore;
import com.example.teramall.R;
import com.example.teramall.model.Deal;
import com.example.teramall.model.Store;

import java.util.List;

public class StoreAdapter2 extends ArrayAdapter<Store> {
    RelativeLayout layout;
    TextView name;
    TextView location;
    TextView address;
    ImageView img_store;
    Context mcontext;
    public StoreAdapter2(@NonNull Context context, int resource, List<Store>lstStore) {
        super(context, resource,lstStore);
        this.mcontext = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Store store = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_store, parent, false);
        }
        layout = convertView.findViewById(R.id.layout_store);
        name =convertView.findViewById(R.id.tv_nameShop);
        location =convertView.findViewById(R.id.tv_location);
        address =convertView.findViewById(R.id.tv_address);
        img_store = convertView.findViewById(R.id.icon_store);
        name.setText(store.getName());
        location.setText(store.getLocation());
        address.setText(store.getAddress());
        Glide.with(img_store.getContext())
                .load(store.getImagelogo())
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(img_store);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGotoDetail(store);
            }
        });
        return convertView;
    }

    private void onClickGotoDetail(Store store) {
        Intent intent = new Intent(getContext(), DetailStore.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_store", store);
        intent.putExtras(bundle);
        mcontext.startActivity(intent);
    }
}
