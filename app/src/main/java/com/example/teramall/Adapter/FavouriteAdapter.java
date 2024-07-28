package com.example.teramall.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.teramall.Detail.DetailActivity;
import com.example.teramall.Detail.DetailStore;
import com.example.teramall.FavouritePage;
import com.example.teramall.MyAplication;
import com.example.teramall.R;
import com.example.teramall.model.Deal;
import com.example.teramall.model.Favourite;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    private Context context;
    private ArrayList<Favourite> lstFav;
    boolean isInMyFav = false;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "FAVOURITE_DEAL_TAG";
    String nameDeal, location, price, surl;

    public FavouriteAdapter(Context context, ArrayList<Favourite> lstFav) {
        this.context = context;
        this.lstFav = lstFav;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_fav,parent,false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        final Favourite favourite = lstFav.get(position);
        if(favourite == null){
            return;
        }
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            holder.btUnFav.setVisibility(View.INVISIBLE);
        }
        holder.tvName.setText(favourite.getShortDescription());
        holder.tvLocation.setText(favourite.getLocation());
        holder.tvPrice.setText(favourite.getPrice());
        Glide.with(holder.imDeal.getContext())
                .load(favourite.getSurl())
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imDeal);
        nameDeal = holder.tvName.getText().toString();
        location = holder.tvLocation.getText().toString();
        price = holder.tvPrice.getText().toString();
        surl = favourite.getSurl();
        if (firebaseAuth.getCurrentUser() != null)
        {
            checkIsFav(nameDeal,holder);
        }
        holder.btUnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firebaseAuth.getCurrentUser() == null){
                    Toast.makeText(view.getContext(), "You're not logged in", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (isInMyFav){
                        MyAplication.RemoveFromFavList(view.getContext(),nameDeal);
                    }else {
                        MyAplication.addToFavList(view.getContext(),nameDeal,price,location,surl);
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if(lstFav != null) {
            return lstFav.size();
        }
        return 0;
    }

    class FavouriteViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvLocation, tvPrice;
        Button btUnFav;
        ImageView imDeal;
        CardView lay_fav;
        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvLocation = itemView.findViewById(R.id.tvlocation);
            tvPrice = itemView.findViewById(R.id.tvprice);
            btUnFav = itemView.findViewById(R.id.btn_favdeal);
            imDeal = itemView.findViewById(R.id.imgdeal);
            lay_fav = itemView.findViewById(R.id.lay_fav);
        }
    }
    private void checkIsFav(String nameClub,FavouriteViewHolder holder){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Favourites");
        reference.child(nameClub)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        isInMyFav = snapshot.exists();
                        if (isInMyFav){
                            holder.btUnFav.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.baseline_favorite_24, 0, 0);
                            holder.btUnFav.setText("Remove Favourite");
                            holder.btUnFav.setTextColor(context.getResources().getColor(R.color.white));
                            holder.btUnFav.setTextSize(20);
                        }
                        else{
                            holder.btUnFav.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.baseline_favorite_border_24, 0, 0);
                            holder.btUnFav.setText("Add Favourite");
                            holder.btUnFav.setTextColor(context.getResources().getColor(R.color.white));
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }
}
