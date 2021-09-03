package com.example.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class My_Excercise extends AppCompatActivity {

    ListView listView;
    DatabaseReference reference;
    String final_target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_excercise);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
       listView=findViewById(R.id.ex_list);
       listView.setAdapter(arrayAdapter);
        int ex_target=Integer.parseInt(getIntent().getStringExtra("target").toString());
        if(ex_target==1)
        {
            final_target="loose weight";
        }
        else if(ex_target==2)
        {
            final_target="maintain weight";
        }
        else
        {
            final_target="gain muscle";
        }
        reference= FirebaseDatabase.getInstance().getReference("Excercises");
        reference.child(final_target.toString()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int size = (int) dataSnapshot.getChildrenCount();
                for(int i=1;i<=size;i++)
                {
                    arrayAdapter.add(dataSnapshot.child("Excercise"+i).getValue().toString());
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