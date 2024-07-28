package com.example.teramall.Fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teramall.FavouritePage;
import com.example.teramall.Login;
import com.example.teramall.ProfileActivity;
import com.example.teramall.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    Button bt_login,bt_logout, btfav;
    Button btProfile;
    TextView tv_Name;
    FirebaseAuth auth;
    LinearLayout layout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TeraMall", "Reload Frag1");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        // return inflater.inflate(R.layout.fragment_account, container, false);
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        // load ten user
        auth = FirebaseAuth.getInstance();
        tv_Name = view.findViewById(R.id.name_user);
        if(auth.getCurrentUser()==null){
            tv_Name.setText("Đăng nhập để có trải nghiệm tốt hơn");
            tv_Name.setTextSize(22);
        }else {
            Loaduserinfo();
        }
        layout = view.findViewById(R.id.gotoPro);
        if (auth.getCurrentUser() == null){
            layout.setVisibility(View.INVISIBLE);
        }else { layout.setVisibility(View.VISIBLE);}
        // dang nhap
        bt_login = view.findViewById(R.id.btnLogin);
        if (auth.getCurrentUser() == null){
            bt_login.setVisibility(View.VISIBLE);
        }else { bt_login.setVisibility(View.INVISIBLE);}
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Login.class);
                startActivity(i);
            }
        });
        //profile
        btProfile = view.findViewById(R.id.btProfile);
        btProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ProfileActivity.class);
                startActivity(i);
            }
        });
        //danh sach yeu thích
        btfav = view.findViewById(R.id.btnfav);
        if (auth.getCurrentUser() == null){
            btfav.setVisibility(View.INVISIBLE);
        }else { btfav.setVisibility(View.VISIBLE);}
        btfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), FavouritePage.class);
                startActivity(i);
            }
        });

        //dang xuat
        bt_logout = view.findViewById(R.id.btnLogout);
        if (auth.getCurrentUser() == null){
            bt_logout.setVisibility(View.INVISIBLE);
        }else { bt_logout.setVisibility(View.VISIBLE);}
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(view.getContext(), Login.class);
                startActivity(i);
            }
        });
        return view;
    }
    private void Loaduserinfo() {
        Log.d(TAG, "loadUserInfo: Loading user info of user"+ auth.getUid());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = ""+snapshot.child("name").getValue();
                        tv_Name.setText(name);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}