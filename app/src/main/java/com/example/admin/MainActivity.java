
package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
ImageSlider imgslider;
Toolbar toolbar;
String final_mobile;
BottomNavigationView bottomNavigationView;
    String mobile_number;
private CardView c1,c2,c3,c4,c5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottom_nav);
        mobile_number=getIntent().getStringExtra("user_mobile_no").toString();
        final_mobile=mobile_number.substring(3,mobile_number.length());
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch(item.getItemId())
                {
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(MainActivity.this,login_activity.class);
                        startActivity(intent);
                        break;

                }
                return false;
            }
        });
        imgslider=findViewById(R.id.image_slider);
        ArrayList<SlideModel>images=new ArrayList<>();
        images.add(new SlideModel(R.drawable.img1, ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.img2,ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.img3,ScaleTypes.FIT));
        images.add(new SlideModel(R.drawable.img4,ScaleTypes.FIT));
        imgslider.setImageList(images);
        c1=(CardView) findViewById(R.id.card1);
        c2=(CardView) findViewById(R.id.card2);
        c3=(CardView) findViewById(R.id.card6);
        c4=(CardView) findViewById(R.id.card4);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.card1:
                intent=new Intent(MainActivity.this,Profile.class);
                intent.putExtra("user_mobile_number",final_mobile.toString());
                startActivity(intent);
                break;
            case R.id.card2:
                intent=new Intent(this,Add_Excercise.class);
                startActivity(intent);
                break;
            case R.id.card6:
                intent=new Intent(this,View_Users.class);
                startActivity(intent);
                break;
            case R.id.card4:
                intent=new Intent(this,tips.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search_view);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setQueryHint("Enter here");
        return super.onCreateOptionsMenu(menu);
    }
}
