package com.example.teramall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.teramall.Adapter.DealAdapter2;
import com.example.teramall.Adapter.FavouriteAdapter;
import com.example.teramall.model.Deal;
import com.example.teramall.model.Favourite;
import com.example.teramall.model.Store;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavouritePage extends AppCompatActivity {
    ArrayList<Favourite> lstFav = new ArrayList<>();
    FavouriteAdapter favouriteAdapter;

    FirebaseDatabase database;
    FirebaseAuth auth;
    DatabaseReference reference;
    TextView favCount;
    RecyclerView rvfav;
    Button btback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_page);
        rvfav = findViewById(R.id.rv_fav);
        rvfav.setLayoutManager(new LinearLayoutManager(FavouritePage.this));
        btback = findViewById(R.id.btnback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        favouriteAdapter = new FavouriteAdapter(this,lstFav);
        rvfav.setAdapter(favouriteAdapter);
        loadFavDeal();
    }

    private void loadFavDeal() {
        database =FirebaseDatabase.getInstance();
        reference = database.getReference("Favourites");
        reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Favourite favourite = snapshot.getValue(Favourite.class);
                        if (favourite != null){
                            lstFav.add(favourite);
                            favouriteAdapter.notifyDataSetChanged();
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