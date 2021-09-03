package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class user_home extends AppCompatActivity {
    CardView b,user_diet,e,sleep,step,water,tips;
    ImageSlider imgslider;
    String final_mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        String user_no=getIntent().getStringExtra("user_mobile_no").toString();
        final_mobile=user_no.substring(3,user_no.length());
        b = (CardView) findViewById(R.id.bmi);
        user_diet = (CardView) findViewById(R.id.diet);
        sleep = (CardView) findViewById(R.id.mysleep);
        step = (CardView) findViewById(R.id.stepcounter);
        tips = (CardView) findViewById(R.id.mytips);
        //e = (CardView) findViewById(R.id.ex);
        imgslider=findViewById(R.id.image_slider);
        ArrayList<SlideModel> images=new ArrayList<>();
        images.add(new SlideModel(R.drawable.img1, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.img2,ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.img3,ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.img4,ScaleTypes.FIT));
        imgslider.setImageList(images);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bmi=new Intent(user_home.this,Bmi.class);
                startActivity(bmi);
            }
        });

        user_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diet=new Intent(user_home.this,My_Diet.class);
                diet.putExtra("mobile_no",final_mobile);
                startActivity(diet);
            }
        });
        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s=new Intent(user_home.this, user_sleep.class);
                s.putExtra("mobile_no",final_mobile);
                startActivity(s);
            }
        });
        step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent st=new Intent(user_home.this,Step_Counter.class);
                startActivity(st);
            }
        });
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t=new Intent(user_home.this,user_tips.class);
                startActivity(t);
            }
        });




        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {


                    case R.id.profile_nav:
                        Intent i=new Intent(user_home.this,My_Profile.class);
                        i.putExtra("final_user_no",final_mobile);
                        startActivity(i);
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
