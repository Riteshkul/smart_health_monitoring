package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Step_Counter extends AppCompatActivity {

    TextView s;
    private double magnitudePrevious=0;
    private Integer stepCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        s=(TextView)findViewById(R.id.step);


        SensorManager sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener stepDetector=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event!=null)
                {
                    float x_acceleration=event.values[0];
                    float y_acceleration=event.values[1];
                    float z_accelertaion=event.values[2];

                    double magnitude=Math.sqrt(x_acceleration*x_acceleration+y_acceleration*y_acceleration+z_accelertaion*z_accelertaion);
                    double magnitudeDelta=magnitude-magnitudePrevious;
                    magnitudePrevious=magnitude;

                    if(magnitudeDelta >6)
                    {
                        stepCount++;
                    }
                    s.setText(stepCount.toString());
                }
            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(stepDetector,sensor,SensorManager.SENSOR_DELAY_NORMAL);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home_nav:
                        startActivity(new Intent(getApplicationContext(),user_home.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.profile_nav:
                        startActivity(new Intent(getApplicationContext(),My_Profile.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.logout_nav:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),user_login.class));
                        overridePendingTransition(0,0);
                        break;

                }
                return false;
            }
        });
    }

    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreference=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreference.edit();
        editor.clear();
        editor.putInt("stepCount",stepCount);
        editor.apply();
    }

    protected void onStop()
    {
        super.onStop();

        SharedPreferences sharedPreference=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreference.edit();
        editor.clear();
        editor.putInt("stepCount",stepCount);
        editor.apply();
    }

    protected void onResume()
    {
        super.onResume();

        SharedPreferences sharedPreference=getPreferences(MODE_PRIVATE);
        stepCount=sharedPreference.getInt("stepCount",0);
    }
}