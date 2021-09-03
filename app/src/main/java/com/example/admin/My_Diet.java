package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class My_Diet extends AppCompatActivity {

    Button mydiet;
    TextView target,bodyType;
    String fire_age,fire_weight,fire_height,fire_gender;
    String usertarget,body_type;

    double res,BFP;
    float BMI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diet);
        String user_phone_No=getIntent().getStringExtra("mobile_no").toString();
        //String user_phone_No="9527921344";
        mydiet=(Button)findViewById(R.id.btnviewMyDiet);
        target=(TextView)findViewById(R.id.textTarget);
        bodyType=(TextView)findViewById(R.id.textBodyType);


        mydiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tar=target.getText().toString();
                String btype=bodyType.getText().toString();
                DatabaseReference reference;
                reference= FirebaseDatabase.getInstance().getReference("Users");
                reference.child(user_phone_No).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {

                                DataSnapshot dataSnapshot = task.getResult();
                                fire_age=String.valueOf(dataSnapshot.child("age").getValue());
                                fire_weight=String.valueOf(dataSnapshot.child("weight").getValue());
                                fire_height=String.valueOf(dataSnapshot.child("height").getValue());
                                fire_gender=String.valueOf(dataSnapshot.child("gender").getValue());
                                float height=(float)Float.valueOf(fire_height);
                                float weight=(float) Float.valueOf(fire_weight);
                                float age=(float) Float.valueOf(fire_age);
                                float h=height/100;//height will converted into meter
                                 BMI=weight/(h*h);
                                if(fire_gender.equalsIgnoreCase("male"))
                                {
                                    res=(double) 66+(13.7 * weight) + (5 * height) - (6.8 * age);
                                }
                                else if(fire_gender.equalsIgnoreCase("female"))
                                {
                                    res=(double)655 + (9.6 *weight) + (1.8 * height) - (4.7 * age);

                                }
                                if(btype.toString().equalsIgnoreCase("boy"))
                                {
                                     BFP = (double)1.51 * BMI - 0.70 * age - 2.2;
                                }
                                else if(btype.toString().equalsIgnoreCase("girl"))
                                {
                                     BFP=(double) 1.51 * BMI - 0.70 * age + 1.4;
                                }
                                else if(btype.toString().equalsIgnoreCase("adult male"))
                                {
                                     BFP=(double) 1.20 * BMI + 0.23 * age - 16.2;
                                }
                                else if(btype.toString().equalsIgnoreCase("adult female"))
                                {
                                     BFP=(double) 1.20 * BMI + 0.23 * age - 5.4;
                                }

                                String user_bmi=(String) String.valueOf(BMI);
                                String user_bmr=(String) String.valueOf(res);
                                String user_bfp=(String) String.valueOf(BFP);


                                if(tar.equalsIgnoreCase("Loose Weight"))
                                {
                                    usertarget="1";
                                }
                                else if(tar.equalsIgnoreCase("Maintain Weight"))
                                {
                                    usertarget="2";
                                }
                                else
                                {
                                    usertarget="0";
                                }

                                if(btype.equalsIgnoreCase("Boy"))
                                {
                                    body_type="2";
                                }
                                else if(btype.equalsIgnoreCase("girl"))
                                {
                                    body_type="3";
                                }
                                else if(btype.equalsIgnoreCase("adult male"))
                                {
                                    body_type="1";
                                }
                                else
                                {
                                    body_type="0";
                                }
                                Bundle bundle=new Bundle();
                                bundle.putString("mobile_no",user_phone_No);
                                bundle.putString("bmi",user_bmi);
                                bundle.putString("bmr",user_bmr);
                                bundle.putString("bfp",user_bfp);
                                bundle.putString("target",usertarget);
                                bundle.putString("body_type",body_type);
                                Intent intent=new Intent(My_Diet.this,view_my_plan.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(My_Diet.this,"error",Toast.LENGTH_SHORT).show();
                    }
                });


            }


        });

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
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
                        startActivity(new Intent(getApplicationContext(),user_login.class));
                        break;

                }
                return false;
            }
        });
    }
}