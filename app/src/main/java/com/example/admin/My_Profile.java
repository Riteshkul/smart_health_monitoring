package com.example.admin;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class My_Profile extends AppCompatActivity {

    Button u;
    String fire_name,fire_age,fire_email,fire_address,fire_phoneno,fire_height,fire_weight;
    String get_user_mobile_no;
    EditText name,age,email,address,ph,wight,height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
         get_user_mobile_no=getIntent().getStringExtra("final_user_no").toString();
        DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(get_user_mobile_no).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {

                        DataSnapshot dataSnapshot=task.getResult();
                        fire_name= String.valueOf(dataSnapshot.child("name").getValue());
                        fire_age= String.valueOf(dataSnapshot.child("age").getValue());
                        fire_email= String.valueOf(dataSnapshot.child("email").getValue());
                        fire_address= String.valueOf(dataSnapshot.child("add").getValue());
                        fire_phoneno= String.valueOf(dataSnapshot.child("phoneno").getValue());
                        fire_weight= String.valueOf(dataSnapshot.child("weight").getValue());
                        fire_height= String.valueOf(dataSnapshot.child("height").getValue());

                        name=(EditText) findViewById(R.id.profile_name);
                        age=(EditText)findViewById(R.id.profile_age);
                        email=(EditText)findViewById(R.id.Email);
                        //ph=(EditText)findViewById(R.id.Phone);
                        wight=(EditText)findViewById(R.id.profile_weight);
                        height=(EditText)findViewById(R.id.profile_height);
                        address=(EditText)findViewById(R.id.user_address);

                        name.setText(fire_name);
                        age.setText(fire_age);
                        email.setText(fire_email);
                        address.setText(fire_address);
                        wight.setText(fire_weight);
                        height.setText(fire_height);
                       // ph.setText(fire_phoneno);
                    }
                    else
                    {
                        Toast.makeText(My_Profile.this,"Problem",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        u=(Button)findViewById(R.id.profile_update);
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference;
                String updated_name=name.getText().toString();
                String updated_address=address.getText().toString();
                String updated_email=email.getText().toString();
                String updated_weight=wight.getText().toString();
                String updated_height=height.getText().toString();
                String updated_age=age.getText().toString();
                String updated_phno=fire_phoneno.toString();
                HashMap updated_user=new HashMap();
                updated_user.put("name",updated_name);
                updated_user.put("email",updated_email);
                updated_user.put("weight",updated_weight);
                updated_user.put("height",updated_height);
                updated_user.put("age",updated_age);
                updated_user.put("add",updated_address);
                updated_user.put("phno",updated_phno);
                reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.child(get_user_mobile_no).setValue(updated_user).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(My_Profile.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                            Intent k = new Intent(My_Profile.this, user_home.class);
                            startActivity(k);
                        }
                        else
                        {
                            Toast.makeText(My_Profile.this,"error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home_nav:
                        startActivity(new Intent(getApplicationContext(),user_home.class));
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