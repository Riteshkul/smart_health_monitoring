package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class user_sleep extends AppCompatActivity {
TextView sleep_duration,wake_time,user_sleep_time;
String fire_age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sleep);
        sleep_duration=findViewById(R.id.duration);
        wake_time=findViewById(R.id.wake_up_time);
        user_sleep_time=findViewById(R.id.sleep_time);
        String user_phone_No=getIntent().getStringExtra("mobile_no").toString();
        DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(user_phone_No).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        fire_age=String.valueOf(dataSnapshot.child("age").getValue());
                        int age=Integer.parseInt(fire_age);
                        if ((age >= 16) && (age <= 18)) {
                            sleep_duration.setText(" 8 hours");
                            user_sleep_time.setText(" 10:00 pm");
                            wake_time.setText(" 6:00 am");
                        } else if ((age >= 18) && (age <= 30)) {
                            sleep_duration.setText(" 7.5 hours");
                            user_sleep_time.setText(" 10:00 pm");
                            wake_time.setText(" 5:30 am");
                        } else if ((age >= 30) && (age <= 50)) {
                            sleep_duration.setText(" 7 hours");
                            user_sleep_time.setText(" 10:00 pm");
                            wake_time.setText(" 5:00 am");
                        } else if ((age >= 50) && (age <= 65)) {
                            sleep_duration.setText(" 7 hours");
                            user_sleep_time.setText(" 10:00 pm");
                            wake_time.setText(" 5:00 am");
                        } else if (age > 65) {
                            sleep_duration.setText(" 7.5 hours");
                            user_sleep_time.setText(" 10:00 pm");
                            wake_time.setText(" 5:30 am");
                        }
                    }
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