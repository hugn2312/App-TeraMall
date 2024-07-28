package com.example.teramall.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.teramall.Adapter.StoreAdapter;
import com.example.teramall.Detail.DetailStore;
import com.example.teramall.FilterDataShop;
import com.example.teramall.R;
import com.example.teramall.model.Store;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {
    Button btnFilter;
    private RecyclerView rcvStore;
    private StoreAdapter storeAdaper;

    private List<Store> lstStore;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private MenuItem menuItem;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopFragment() {
        // Required empty public constructor
    }
    public void onResume() {
        super.onResume();
        Log.e("TeraMall", "Reload Frag2");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
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
        View view = inflater.inflate(R.layout.fragment_shop,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnFilter = view.findViewById(R.id.bt_FilterShop);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FilterDataShop.class));
            }
        });
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Store");
        Toolbar toolbar = view.findViewById(R.id.toolbar_search);
        AppCompatActivity  activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        rcvStore =view.findViewById(R.id.rcv_view);
        rcvStore.setLayoutManager(new LinearLayoutManager(getContext()));
        lstStore = new ArrayList<>() ;
        storeAdaper = new StoreAdapter(lstStore, new StoreAdapter.IClickItemStoreList() {
            @Override
            public void onClickItemStore(Store store) {
                onClickGotoDetail(store);
            }
        });

        rcvStore.setAdapter(storeAdaper);
        getListStoreFromDatabase();
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.store_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                storeAdaper.getFilter().filter(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                storeAdaper.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getListStoreFromDatabase() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Store");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Store store = snapshot.getValue(Store.class);
                if (store != null){
                    lstStore.add(store);
                    storeAdaper.notifyDataSetChanged();
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
    private void onClickGotoDetail(Store store) {
        Intent intent = new Intent(getContext(), DetailStore.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_store", store);
        intent.putExtras(bundle);
        startActivity(intent);
    }
//    private void mySearch(String newText) {
//        FirebaseRecyclerOptions<Store> options =
//                new FirebaseRecyclerOptions.Builder<Store>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Store").orderByChild("name").startAt(newText).endAt(newText+"uf8ff"),Store.class )
//                        .build();
//        storeAdaper = new StoreAdapter(options, new StoreAdapter.IClickItemStoreList() {
//            @Override
//            public void onClickItemStore(Store store) {
//                onClickGotoDetail(store);
//            }
//        });
//        storeAdaper.startListening();
//        rcvStore.setAdapter(storeAdaper);
//    }
}