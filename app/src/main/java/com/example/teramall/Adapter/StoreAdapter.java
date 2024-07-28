package com.example.teramall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.teramall.R;
import com.example.teramall.model.Store;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> implements Filterable {
    private List<Store> listStore;
    private Context store_context;
    private List<Store> listStoreOld;
    public StoreAdapter.IClickItemStoreList iClickItemStoreList;

//    public StoreAdapter(@NonNull FirebaseRecyclerOptions<Store> options,IClickItemStoreList listener) {
//        super(options);
//        this.listStore = listStoreOld;
//        this.listStoreOld = listStore;
//        this.iClickItemStoreList =listener;
//    }
    public StoreAdapter(List<Store> listStore, IClickItemStoreList listener){
        this.listStore = listStore;
        this.listStoreOld = listStore;
        this.iClickItemStoreList = listener;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    listStore = listStoreOld;
                }else {
                    List<Store> list = new ArrayList<>();
                    for(Store store:listStoreOld){
                        if (store.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(store);
                        }
                    }
                    listStore = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values =listStore;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults Results) {
                listStore = (List<Store>) Results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public StoreAdapter.StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_store,parent,false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapter.StoreViewHolder holder, int position) {
        final Store store = listStore.get(position);
        if(store == null){
            return;
        }

        holder.tvName.setText(store.getName());
        holder.tvAddress.setText(store.getAddress());
        Glide.with(holder.imgView.getContext())
                .load(store.getImagelogo())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imgView);
        holder.layout_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemStoreList.onClickItemStore(store);
            }
        });
    }

//    @Override
//    protected void onBindViewHolder(@NonNull StoreViewHolder holder, int position, @NonNull Store model) {
//        final Store store = listStore.get(position);
//        if(store == null){
//            return;
//        }
//
//        holder.tvName.setText(store.getName());
//        holder.tvAddress.setText(store.getAddress());
//        Glide.with(holder.imgView.getContext())
//                .load(store.getImagelogo())
//                .placeholder(R.mipmap.ic_launcher_round)
//                .error(R.mipmap.ic_launcher_round)
//                .into(holder.imgView);
//        holder.layout_store.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                iClickItemStoreList.onClickItemStore(store);
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        if(listStore != null) {
            return listStore.size();
        }
        return 0;
    }

    public interface IClickItemStoreList {
        void onClickItemStore(Store store);
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgView;
        private TextView tvName;
        private TextView tvAddress;
        private RelativeLayout layout_store;
        public StoreViewHolder(@NonNull View itemView ) {
            super(itemView);
            imgView = itemView.findViewById(R.id.icon_store);
            tvName = itemView.findViewById(R.id.tv_nameShop);
            tvAddress =itemView.findViewById(R.id.tv_address);
            layout_store =itemView.findViewById(R.id.layout_store);
        }
    }
}