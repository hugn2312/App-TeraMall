package com.example.teramall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.teramall.Adapter.DealAdapter;
import com.example.teramall.Adapter.StoreAdapter;
import com.example.teramall.Adapter.StoreAdapter2;
import com.example.teramall.model.Deal;
import com.example.teramall.model.Store;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class FilterDataShop extends AppCompatActivity {
    public static ArrayList<Store> storeList = new ArrayList<Store>();
    private ListView listView;
    private Button btnVitri;
    private Button btnDanhmuc;
    private TextView tvVitri, tvDanhmuc;
    private LinearLayout filterView1;
    private LinearLayout filterView2,filterView3,filterView4;
    private ArrayList<String> selectedFilter = new ArrayList<String>();
    private String currentSearchText = "";
    boolean ViTriHidden = true;
    boolean DanhMucHidden = true;
    private SearchView searchView;
    private SearchView searchView2;
    Button allbtn, tang1btn, tang2btn, tang3btn, tang4btn,tangtretbtn;
    Button thoitrang, thethao, amthuc, giaitri, cuahang, lamdep;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private StoreAdapter2 storeAdapter;
    private int white, lightGray, darkOrange,red;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_data_shop);
        listView = findViewById(R.id.shopListView);
        initSearchWidgets();
        initWidgets();
        hideDanhmuc();
        hideVtri();
        initColor();
        unSelectedAllFilterButton();
        unSelectedAllFilterButton2();
        lookselected(allbtn);
        selectedFilter.add("all");
        // data
        storeAdapter = new StoreAdapter2(this,0,storeList);
        listView.setAdapter(storeAdapter);
        getListStoreFromDatabase();
    }

    private void initColor() {
        white = ContextCompat.getColor(getApplicationContext(), R.color.white);
        lightGray = ContextCompat.getColor(getApplicationContext(), R.color.light_grey);
        darkOrange = ContextCompat.getColor(getApplicationContext(), R.color.dark_orange);
        red = ContextCompat.getColor(getApplicationContext(),R.color.red);
    }
    private void lookselected (Button button){
        button.setTextColor(white);
        button.setBackgroundColor(darkOrange);
    }
    private void lookunselected (Button button){
        button.setTextColor(red);
        button.setBackgroundColor(lightGray);
    }
    private void unSelectedAllFilterButton(){
        lookunselected(allbtn);
        lookunselected(tang1btn);
        lookunselected(tang2btn);
        lookunselected(tang3btn);
        lookunselected(tang4btn);
        lookunselected(tangtretbtn);
    }
    private void unSelectedAllFilterButton2(){
        lookunselected(giaitri);
        lookunselected(thethao);
        lookunselected(cuahang);
        lookunselected(amthuc);
        lookunselected(lamdep);
        lookunselected(thoitrang);
    }
    private void initWidgets() {
        tvVitri = findViewById(R.id.tvVitri);
        tvDanhmuc = findViewById(R.id.tvDanhmuc);
        btnVitri = (Button) findViewById(R.id.btn_vitri);
        btnDanhmuc = (Button) findViewById(R.id.btn_danhmuc);
        filterView1 = (LinearLayout) findViewById(R.id.filter_shop);
        filterView2 = (LinearLayout) findViewById(R.id.filter_shop2);
        filterView3 = (LinearLayout) findViewById(R.id.filter_shop3);
        filterView4 = (LinearLayout) findViewById(R.id.filter_shop4);
        allbtn = (Button) findViewById(R.id.allFilter);
        tang1btn = (Button) findViewById(R.id.tang1Filter);
        tang2btn = (Button) findViewById(R.id.tang2Filter);
        tang3btn = (Button) findViewById(R.id.tang3Filter);
        tang4btn= (Button) findViewById(R.id.tang4Filter);
        tangtretbtn =(Button) findViewById(R.id.tangtretFilter);
        thoitrang = (Button) findViewById(R.id.thoitrangFilter);
        thethao = (Button) findViewById(R.id.thethaoFilter);
        amthuc = (Button) findViewById(R.id.amthucFilter);
        giaitri =(Button) findViewById(R.id.giaitriFilter);
        cuahang = (Button) findViewById(R.id.cuahangFilter);
        lamdep = (Button) findViewById(R.id.lamdepFilter);
    }

    private void initSearchWidgets() {
        searchView = (SearchView) findViewById(R.id.shopListSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                currentSearchText = s;
                ArrayList<Store> filteredStore = new ArrayList<Store>();
                for (Store store: storeList)
                {
                    if (store.getLocation().toLowerCase().contains(s.toLowerCase()) || store.getDanhMuc().toLowerCase().contains(s.toLowerCase())){
                        if (selectedFilter.contains("all"))
                        {
                            filteredStore.add(store);
                        }
                        else {
                            for (String filter: selectedFilter){
                                if (store.getLocation().toLowerCase().contains(filter) || store.getDanhMuc().toLowerCase().contains(filter))
                                {
                                    filteredStore.add(store);
                                }
                            }
                        }
                    }
                }
                storeAdapter = new StoreAdapter2(getApplicationContext(),0,filteredStore);
                listView.setAdapter(storeAdapter);
                return false;
            }
        });
    }
    private void getListStoreFromDatabase(){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Store");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Store store = snapshot.getValue(Store.class);
                if (store != null){
                    storeList.add(store);
                    storeAdapter.notifyDataSetChanged();
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
        if (status != null && !selectedFilter.contains(status))
        selectedFilter.add(status);
        ArrayList<Store> filteredStore = new ArrayList<Store>();

        for(Store store: storeList)
        {
            for (String filter: selectedFilter) {
                if (store.getLocation().toLowerCase().contains(filter) || store.getDanhMuc().toLowerCase().contains(filter)) {
                    if (currentSearchText == "") {
                        filteredStore.add(store);
                    } else {
                        if (store.getLocation().toLowerCase().contains(currentSearchText.toLowerCase()) || store.getDanhMuc().toLowerCase().contains(currentSearchText.toLowerCase())) {
                            filteredStore.add(store);
                        }
                    }
                }
            }
        }
        storeAdapter = new StoreAdapter2(this,0,filteredStore);
        listView.setAdapter(storeAdapter);
    }
    public void allFilterTapped(View view){
        selectedFilter.clear();
        selectedFilter.add("all");
        storeAdapter = new StoreAdapter2(this,0,storeList);
        listView.setAdapter(storeAdapter);
        unSelectedAllFilterButton();
        unSelectedAllFilterButton2();
        lookselected(allbtn);
    }
    public void tang1FilterTapped(View view) {
        filterList("1");

        lookselected(tang1btn);
        lookunselected(allbtn);
        selectedFilter.remove("all");
    }
    public void tang2FilterTapped(View view) {
        filterList("2");
        lookunselected(allbtn);
        lookselected(tang2btn);
        selectedFilter.remove("all");
    }
    public void tang3FilterTapped(View view) {
        filterList("3");
        lookunselected(allbtn);
        lookselected(tang3btn);
        selectedFilter.remove("all");
    }
    public void tangtrietFilterTapped(View view) {
        filterList("trệt");
        lookunselected(allbtn);
        lookselected(tangtretbtn);
        selectedFilter.remove("all");
    }
    public void tang4FilterTapped(View view) {
        filterList("4");
        lookunselected(allbtn);
        lookselected(tang4btn);
        selectedFilter.remove("all");
    }
    public void showVitriTapped(View view) {
        if(ViTriHidden == true)
        {
            ViTriHidden = false;
            showVitri();
        }
        else
        {
            ViTriHidden = true;
            hideVtri();
        }
    }

    private void hideVtri() {
        filterView1.setVisibility(View.GONE);
        filterView2.setVisibility(View.GONE);
        tvVitri.setText("VỊ TRÍ");
    }

    private void showVitri() {
        filterView1.setVisibility(View.VISIBLE);
        filterView2.setVisibility(View.VISIBLE);
        tvVitri.setText("ẨN");
    }


    public void showDanhmucTapped(View view) {
        if(DanhMucHidden == true)
        {
            DanhMucHidden = false;
            showDanhmuc();
        }
        else
        {
            DanhMucHidden = true;
            hideDanhmuc();
        }
    }

    private void hideDanhmuc() {
        filterView3.setVisibility(View.GONE);
        filterView4.setVisibility(View.GONE);
        tvDanhmuc.setText("DANH MỤC");
    }

    private void showDanhmuc() {
        filterView3.setVisibility(View.VISIBLE);
        filterView4.setVisibility(View.VISIBLE);
        tvDanhmuc.setText("ẨN");
    }
    public void ThoiTrangTapped(View view) {
        filterList("thời trang");
        lookunselected(allbtn);
        selectedFilter.remove("all");
        lookselected(thoitrang);
    }
    public void TheThaoFilterTapped(View view) {
        filterList("thể thao");
        lookunselected(allbtn);
        selectedFilter.remove("all");
        lookselected(thethao);
    }
    public void AmThucFilterTapped(View view) {
        filterList("ẩm thực");
        lookunselected(allbtn);
        selectedFilter.remove("all");
        lookselected(amthuc);
    }
    public void GiaiTriFilterTapped(View view) {
        filterList("giải trí");
        lookunselected(allbtn);
        selectedFilter.remove("all");
        lookselected(giaitri);
    }
    public void CuaHangFilterTapped(View view) {
        filterList("cửa hàng");
        lookunselected(allbtn);
        selectedFilter.remove("all");
        lookselected(cuahang);
    }
    public void LamDepFilterTapped(View view) {
        filterList("làm đẹp");
        lookunselected(allbtn);
        selectedFilter.remove("all");
        lookselected(lamdep);
    }
}