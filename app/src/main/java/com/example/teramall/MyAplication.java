package com.example.teramall;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MyAplication extends Application {
    public static void addToFavList(Context context, String name, String price, String location, String surl){
        FirebaseAuth auth =FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null){
            Toast.makeText(context, "Bạn chưa đăng nhập, vui lập đăng nhập để yêu thích", Toast.LENGTH_SHORT).show();
        }
        else {
            HashMap<String,Object> hashMap =new HashMap<>();
            hashMap.put("shortDescription", ""+name);
            hashMap.put("location", ""+location);
            hashMap.put("price", ""+price);
            hashMap.put("surl", ""+surl);
            //
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Favourites");
            ref.child(name)
                    .setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Đã thêm vào mục yêu thích", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Không thể thêm vào mục yêu thích"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    public static void RemoveFromFavList(Context context,String name){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null)
        {
            Toast.makeText(context, "Bạn chưa đăng nhập, vui lập đăng nhập để yêu thích", Toast.LENGTH_SHORT).show();
        }
        else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Favourites");
            ref.child(name)
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Không thể xóa"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
