package com.example.teramall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.teramall.R;
import com.example.teramall.model.Deal;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class DealAdapter2  extends RecyclerView.Adapter<DealAdapter2.ViewHolder> {
    private Context context;
    private List<Deal> lstDeal;
    public IClickItemDealList iClickItemDealList;
    public DealAdapter2(List<Deal> lstDeal,IClickItemDealList listener) {
        this.lstDeal = lstDeal;
        this.iClickItemDealList = listener;
    }


    @NonNull
    @Override
    public DealAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ground,parent,false);
        return new DealAdapter2.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DealAdapter2.ViewHolder holder, int position) {
        Deal deal = lstDeal.get(position);
        holder.short_descripton.setText(deal.getShortDescription());
        holder.location.setText(deal.getLocation());
        holder.price.setText(deal.getPrice());
        Glide.with(holder.img_deal.getContext())
                .load(deal.getSurl())
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(holder.img_deal);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemDealList.onClickItemDeal(deal);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (lstDeal != null){
            return lstDeal.size();
        }
        return 0;
    }

    public interface IClickItemDealList {
        void onClickItemDeal(Deal deal);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView layout;
        TextView short_descripton;
        TextView location;
        TextView price;
        ImageView img_deal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_ground);
            short_descripton = itemView.findViewById(R.id.short_desciption);
            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            img_deal = itemView.findViewById(R.id.img_deal);
        }
    }
}
