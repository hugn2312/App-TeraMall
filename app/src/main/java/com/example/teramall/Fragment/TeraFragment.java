package com.example.teramall.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teramall.Adapter.EventAdapter;
import com.example.teramall.Adapter.StoreAdapter;
import com.example.teramall.Adapter.VoucherAdapter;
import com.example.teramall.Detail.DetailEvent;
import com.example.teramall.Detail.DetailStore;
import com.example.teramall.Detail.DetailVoucher;
import com.example.teramall.R;
import com.example.teramall.model.Event;
import com.example.teramall.model.Store;
import com.example.teramall.model.Voucher;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeraFragment extends Fragment {
    private RecyclerView rcvVoucher;
    private RecyclerView rcvEvent;
    private VoucherAdapter voucherAdapter;
    private EventAdapter eventAdapter;

    private List<Voucher> lstVoucher;
    private List<Event> lstEvent;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeraFragment() {
        // Required empty public constructor
    }
    public void onResume() {
        super.onResume();
        Log.e("TeraMall", "Reload Frag3");
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeraFragment newInstance(String param1, String param2) {
        TeraFragment fragment = new TeraFragment();
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
        return inflater.inflate(R.layout.fragment_tera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Voucher");
        rcvVoucher =view.findViewById(R.id.re_khuyenMai);
        rcvVoucher.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false));
        lstVoucher = new ArrayList<>() ;
        voucherAdapter = new VoucherAdapter(lstVoucher, new VoucherAdapter.IClickItemVoucherList() {
            @Override
            public void onClickItemVoucher(Voucher voucher) {
                onClickGotoDetail(voucher);
            }
        });
        rcvVoucher.setAdapter(voucherAdapter);
        getListStoreFromDatabase();
        // event
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Event");
        rcvEvent =view.findViewById(R.id.re_suKien);
        rcvEvent.setLayoutManager(new GridLayoutManager(getContext(),2));
        lstEvent = new ArrayList<>() ;
        eventAdapter = new EventAdapter(lstEvent, new EventAdapter.IClickItemEventList() {
            @Override
            public void onClickItemEvent(Event event) {
                onClickGotoDetailEvent(event);
            }
        });
        rcvEvent.setAdapter(eventAdapter);
        getListEventFromDatabase();

        super.onViewCreated(view, savedInstanceState);
    }

    private void getListEventFromDatabase() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Event");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Event event = snapshot.getValue(Event.class);
                if (event != null){
                    lstEvent.add(event);
                    eventAdapter.notifyDataSetChanged();
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

    private void onClickGotoDetail(Voucher voucher) {
        Intent intent = new Intent(getContext(), DetailVoucher.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_voucher", voucher);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void onClickGotoDetailEvent(Event event) {
        Intent intent = new Intent(getContext(), DetailEvent.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_event", event);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getListStoreFromDatabase() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Voucher");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Voucher voucher = snapshot.getValue(Voucher.class);
                if (voucher != null){
                    lstVoucher.add(voucher);
                    voucherAdapter.notifyDataSetChanged();
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
}