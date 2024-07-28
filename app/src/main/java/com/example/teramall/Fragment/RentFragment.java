package com.example.teramall.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.teramall.Adapter.DealAdapter2;
import com.example.teramall.Detail.DetailActivity;
import com.example.teramall.FilterData;
import com.example.teramall.R;
import com.example.teramall.model.Deal;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RentFragment extends Fragment {
    private RecyclerView recyclerView;
    private DealAdapter2 dealAdapter;
    private List<Deal> lstDeal;
    private SearchView searchView;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Button bt_Filter;
    private LinearLayout lay_Filter, lay_Sort;
    boolean sortHidden = true;
    boolean filterHidden = true;
    private String selectedFilter = "all";
    private String currentSearchText = "";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RentFragment() {
        // Required empty public constructor
    }
    public void onResume() {
        super.onResume();
        Log.e("TeraMall", "Reload Frag4");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UnknowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RentFragment newInstance(String param1, String param2) {
        RentFragment fragment = new RentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rent,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        lstDeal = new ArrayList<>();

        recyclerView.setAdapter(dealAdapter);
        recyclerView.setItemAnimator(null);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Ground");
        dealAdapter = new DealAdapter2(lstDeal, new DealAdapter2.IClickItemDealList() {
            @Override
            public void onClickItemDeal(Deal deal) {
                onClickGotoDetail(deal);
            }
        });
        recyclerView.setAdapter(dealAdapter);
        getListDealFromDatabase();
        //
        bt_Filter = view.findViewById(R.id.bt_Filter);
        bt_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),FilterData.class));
            }
        });
    }
    private void getListDealFromDatabase(){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Ground");
        Query query = reference.orderByChild("price").startAt("20");
        Query query1 = reference.orderByChild("price").endAt("50");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Deal deal = snapshot.getValue(Deal.class);
                if (deal != null){
                    lstDeal.add(deal);
                    dealAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void onClickGotoDetail(Deal deal){
        Intent intent = new Intent(getContext(), DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_ground", deal);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void filterList(String status){
        selectedFilter = status;
        ArrayList<Deal> filteredDeal = new ArrayList<Deal>();

        for(Deal deal: lstDeal)
        {
            if(deal.getLocation().toLowerCase().contains(status))
            {
                if(currentSearchText == "")
                {
                    filteredDeal.add(deal);
                }
                else
                {
                    if(deal.getLocation().toLowerCase().contains(currentSearchText.toLowerCase()))
                    {
                        filteredDeal.add(deal);
                    }
                }
            }
        }
        dealAdapter = new DealAdapter2(lstDeal, new DealAdapter2.IClickItemDealList() {
            @Override
            public void onClickItemDeal(Deal deal) {
                onClickGotoDetail(deal);
            }
        });
        recyclerView.setAdapter(dealAdapter);
    }
    public void allFilterTapped(View view){
        selectedFilter = "all";
        dealAdapter = new DealAdapter2(lstDeal, new DealAdapter2.IClickItemDealList() {
            @Override
            public void onClickItemDeal(Deal deal) {
                onClickGotoDetail(deal);
            }
        });
        recyclerView.setAdapter(dealAdapter);
    }
    public void tang1FilterTapped(View view) {
        filterList("Tầng 1, TeraMall");
    }
    public void tang2FilterTapped(View view) {
        filterList("Tầng 2, TeraMall");
    }
    public void tang3FilterTapped(View view) {
        filterList("Tầng 3, TeraMall");
    }
}