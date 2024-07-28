package com.example.teramall.Adapter;

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
import com.example.teramall.model.Event;
import com.example.teramall.model.Voucher;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    List <Event> lstEvent;
    public EventAdapter.IClickItemEventList iClickItemEventList;
    public EventAdapter (List <Event> lstEvent,IClickItemEventList listener){
        this.lstEvent =lstEvent;
        this.iClickItemEventList =listener;
    }
    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_sukien,parent,false);
        return new EventAdapter.EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position) {
        final Event event = lstEvent.get(position);
        if(event == null){
            return;
        }
        holder.tv_nameSukien.setText(event.getNameEvent());
        Glide.with(holder.imgEvent.getContext())
                .load(event.getImgEvent())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imgEvent);
        holder.lay_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemEventList.onClickItemEvent(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(lstEvent != null) {
            return lstEvent.size();
        }
        return 0;
    }

    public interface IClickItemEventList {
        void onClickItemEvent(Event event);
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nameSukien;
        ImageView imgEvent;
        private CardView lay_event;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nameSukien = itemView.findViewById(R.id.tv_nameSukien);
            imgEvent = itemView.findViewById(R.id.img_sukien);
            lay_event = itemView.findViewById(R.id.lay_event);
        }
    }
}
