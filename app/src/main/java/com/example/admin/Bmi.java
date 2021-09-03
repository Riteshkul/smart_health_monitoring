package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Bmi extends AppCompatActivity {

    EditText w,h;
    Button bmi;
    TextView tbmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        w=(EditText)findViewById(R.id.Weight);
        h=(EditText)findViewById(R.id.height);
        bmi=(Button)findViewById(R.id.bmi);
        tbmi=(TextView)findViewById(R.id.textbmi);


        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weight=w.getText().toString();
                String height=h.getText().toString();

                float wh=(float) Float.valueOf(weight);
                float ht=(float) Float.valueOf(height)/100; //height will converted into meter

                float re=wh/(ht*ht);

                if(re<18.5)
                {
                    tbmi.setText(re+" Underweight");
                }
                else if(re<=24.9 && re>=18.5)
                {
                    tbmi.setText(re+"  Normal");
                }
                else if(re>=25.0 && re<=29.9)
                {
                    tbmi.setText(re+"  Overweight");
                }
                else if(re>=30.0 && re<=34.9)
                {
                    tbmi.setText(re+" Obese");
                }
                else if(re>=35)
                {
                    tbmi.setText(re+" Extremly Obese");
                }
                else
                {
                    tbmi.setText("Enter Details...");
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