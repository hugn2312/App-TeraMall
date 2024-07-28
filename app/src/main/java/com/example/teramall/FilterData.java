package com.example.teramall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.teramall.Adapter.DealAdapter;
import com.example.teramall.Adapter.DealAdapter2;
import com.example.teramall.model.Deal;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class FilterData extends AppCompatActivity {
    public static ArrayList<Deal> dealList = new ArrayList<Deal>();

    private ListView recyclerView;
    private Button sortButton;
    private Button filterButton;
    private TextView tvSort, tvFilter;
    private LinearLayout filterView1;
    private LinearLayout filterView2;
    private LinearLayout sortView;

    private String selectedFilter = "all";
    private String currentSearchText = "";
    boolean sortHidden = true;
    boolean filterHidden = true;
    private SearchView searchView;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private DealAdapter dealAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_data);
        recyclerView = findViewById(R.id.shapesListView);
        initSearchWidgets();
        initWidgets();
        hideFilter();
        hideSort();
        //
        dealAdapter = new DealAdapter(this,0,dealList);
        recyclerView.setAdapter(dealAdapter);
        getListDealFromDatabase();

    }

    private void initWidgets() {
        tvSort = findViewById(R.id.tvSort);
        tvFilter = findViewById(R.id.tvFilter);
        sortButton = (Button) findViewById(R.id.sortButton);
        filterButton = (Button) findViewById(R.id.filterButton);
        filterView1 = (LinearLayout) findViewById(R.id.layout_filter);
        filterView2 = (LinearLayout) findViewById(R.id.layout_filter2);
        sortView = (LinearLayout) findViewById(R.id.layout_sort);
    }
    private void initSearchWidgets() {
        searchView = (SearchView) findViewById(R.id.shapeListSearchView);
        searchView.setVisibility(View.INVISIBLE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                currentSearchText = s;
                ArrayList<Deal> filteredDeal = new ArrayList<Deal>();
                for (Deal deal: dealList)
                {
                    if (deal.getLocation().toLowerCase().contains(s.toLowerCase())){
                        if (selectedFilter.equals("all"))
                        {
                            filteredDeal.add(deal);
                        }
                        else {
                            if (deal.getLocation().toLowerCase().contains(selectedFilter))
                            {
                                filteredDeal.add(deal);
                            }
                        }
                    }
                }
                dealAdapter = new DealAdapter(getApplicationContext(),0,filteredDeal);
                recyclerView.setAdapter(dealAdapter);
                return false;
            }
        });
    }

    private void getListDealFromDatabase(){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Ground");
        Query query = reference.orderByChild("location").equalTo("Tầng 1, TeraMall");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Deal deal = snapshot.getValue(Deal.class);
                if (deal != null){
                    dealList.add(deal);
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

    private void filterList(String status){
        selectedFilter = status;
        ArrayList<Deal> filteredDeal = new ArrayList<Deal>();

        for(Deal deal: dealList)
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
        dealAdapter = new DealAdapter(this,0,filteredDeal);
        recyclerView.setAdapter(dealAdapter);
    }
    public void allFilterTapped(View view){
        selectedFilter = "all";
        dealAdapter = new DealAdapter(this,0,dealList);
        recyclerView.setAdapter(dealAdapter);
    }
    public void tang1FilterTapped(View view) {
        filterList("1");
    }
    public void tang2FilterTapped(View view) {
        filterList("2");
    }
    public void tang3FilterTapped(View view) {
        filterList("3");
    }
    public void tangtrietFilterTapped(View view) {
        filterList("trệt");
    }
    public void tang4FilterTapped(View view) {
        filterList("4");
    }
    public void showSortTapped(View view) {
        if(sortHidden == true)
        {
            sortHidden = false;
            showSort();
        }
        else
        {
            sortHidden = true;
            hideSort();
        }
    }

    public void showFilterTapped(View view) {
        if(filterHidden == true)
        {
            filterHidden = false;
            showFilter();
        }
        else
        {
            filterHidden = true;
            hideFilter();
        }
    }
    private void hideSort() {
        sortView.setVisibility(View.GONE);
        tvSort.setText("SORT");
    }
    private void showSort() {
        sortView.setVisibility(View.VISIBLE);
        tvSort.setText("HIDE");
    }

    private void hideFilter() {
        filterView1.setVisibility(View.GONE);
        filterView2.setVisibility(View.GONE);
        tvFilter.setText("FILTER");

    }
    private void showFilter() {
        filterView1.setVisibility(View.VISIBLE);
        filterView2.setVisibility(View.VISIBLE);
        tvFilter.setText("HIDE");
    }

    public void giatangClick(View view) {
        Collections.sort(dealList,Deal.priceAscending);
        dealAdapter = new DealAdapter(this,0,dealList);
        recyclerView.setAdapter(dealAdapter);
        checkForFilter();
    }

    public void giagiamClick(View view) {
        Collections.sort(dealList,Deal.priceAscending);
        Collections.reverse(dealList);
        dealAdapter = new DealAdapter(this,0,dealList);
        recyclerView.setAdapter(dealAdapter);
        checkForFilter();
    }
    private void checkForFilter()
    {
        if(selectedFilter.equals("all"))
        {
            if(currentSearchText.equals(""))
            {
                setAdapter(dealList);
            }
            else
            {
                ArrayList<Deal> filteredShapes = new ArrayList<Deal>();
                for(Deal shape: dealList)
                {
                    if(shape.getLocation().toLowerCase().contains(currentSearchText))
                    {
                        filteredShapes.add(shape);
                    }
                }
                setAdapter(filteredShapes);
            }
        }
        else
        {
            filterList(selectedFilter);
        }
    }

    private void setAdapter(ArrayList<Deal> shapeList)
    {
        dealAdapter = new DealAdapter(this,0,dealList);
        recyclerView.setAdapter(dealAdapter);
    }


}