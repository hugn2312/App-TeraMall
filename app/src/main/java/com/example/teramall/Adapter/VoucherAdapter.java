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
import com.example.teramall.model.Store;
import com.example.teramall.model.Voucher;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
    List <Voucher> lstVoucher;
    public VoucherAdapter.IClickItemVoucherList iClickItemVoucherList;
    public VoucherAdapter (List <Voucher> lstVoucher, IClickItemVoucherList listener ){
        this.lstVoucher  = lstVoucher;
        this.iClickItemVoucherList = listener;
    }
    @NonNull
    @Override
    public VoucherAdapter.VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_event,parent,false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherAdapter.VoucherViewHolder holder, int position) {
        final Voucher voucher = lstVoucher.get(position);
        if(voucher == null){
            return;
        }
        Glide.with(holder.imgVoucher.getContext())
                .load(voucher.getImgVoucher())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imgVoucher);
        holder.tvnameVoucher.setText(voucher.getNameVoucher());
        holder.lay_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemVoucherList.onClickItemVoucher(voucher);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(lstVoucher != null) {
            return lstVoucher.size();
        }
        return 0;
    }

    public class VoucherViewHolder extends RecyclerView.ViewHolder {
        private CardView lay_voucher;
        private ImageView imgVoucher;
        private TextView tvnameVoucher;
        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            imgVoucher  = itemView.findViewById(R.id.img_khuyenMai);
            tvnameVoucher = itemView.findViewById(R.id.tv_nameVoucher);
            lay_voucher = itemView.findViewById(R.id.lay_voucher);

        }
    }

    public interface IClickItemVoucherList {
        void onClickItemVoucher(Voucher voucher);
    }
}
