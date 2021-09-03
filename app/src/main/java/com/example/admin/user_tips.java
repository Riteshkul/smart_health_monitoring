package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class user_tips extends AppCompatActivity {
ListView breakfast_tip,lunch_tip,dinner_tip;
    DatabaseReference reference;
    String text="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tips);
        breakfast_tip=findViewById(R.id.brekfast_view);
        lunch_tip=findViewById(R.id.lunch_view);
        dinner_tip=findViewById(R.id.dinner_view);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        ArrayAdapter<String> arrayAdapter2=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        breakfast_tip.setAdapter(arrayAdapter);
        lunch_tip.setAdapter(arrayAdapter1);
        dinner_tip.setAdapter(arrayAdapter2);
        reference= FirebaseDatabase.getInstance().getReference("Tips");
        reference.child("breakfast").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int size = (int) dataSnapshot.getChildrenCount();
                for(int i=1;i<=size;i++)
                {
                    arrayAdapter.add(dataSnapshot.child("Tip"+i).getValue().toString());
                }

            }
        });
        reference.child("lunch").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int size = (int) dataSnapshot.getChildrenCount();
                for(int i=1;i<=size;i++)
                {
                    arrayAdapter1.add(dataSnapshot.child("Tip"+i).getValue().toString());
                }

            }
        });
        reference.child("dinner").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int size = (int) dataSnapshot.getChildrenCount();
                for(int i=1;i<=size;i++)
                {
                    arrayAdapter2.add(dataSnapshot.child("Tip"+i).getValue().toString());
                }

            }
        });
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home_nav:
                        startActivity(new Intent(getApplicationContext(),user_home.class));
                        break;
                    case R.id.profile_nav:
                        startActivity(new Intent(getApplicationContext(),My_Profile.class));
                        break;
                    case R.id.logout_nav:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),user_login.class));
                        break;



                }
                return false;
            }
        });
    }
}