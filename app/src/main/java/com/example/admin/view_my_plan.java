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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class view_my_plan extends AppCompatActivity {
String plan_name="";
String fire_breakfast;
    String fire_lunch;
    String fire_dinner,get_user_mobile_no,get_target,get_body_type,get_bmi,get_bmr,get_bfp;
    Button excercise;
TextView status_bmi,food_breakfast,food_lunch,food_dinner,label_status,label_breakfast,label_lunch,label_dinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_plan);
        excercise=findViewById(R.id.my_excercise);

        Bundle bundle =getIntent().getExtras();
        if(bundle!=null) {
            get_user_mobile_no=bundle.getString("mobile_no");
            get_bmi=bundle.getString("bmi");
            get_bmr=bundle.getString("bmr");
            get_bfp=bundle.getString("bfp");
            get_target = bundle.getString("target");
            get_body_type = bundle.getString("body_type");

            String url = "https://diet-api-android.herokuapp.com/?target="+get_target.toString()+"&bmi="+get_bmi.toString()
                    +"&bmr="+get_bmr.toString()+"&body_type="+get_body_type.toString()+"&bfp="+get_bfp.toString();
            RequestQueue queue = Volley.newRequestQueue(view_my_plan.this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String plan_number=response.getString("prediction");
                                Toast.makeText(view_my_plan.this,plan_number.toString(),Toast.LENGTH_SHORT).show();

                                food_breakfast=findViewById(R.id.breakfast_items);

                                label_breakfast=findViewById(R.id.breakfast);
                                label_lunch=findViewById(R.id.lunch);
                                label_dinner=findViewById(R.id.dinner);

                                food_lunch=findViewById(R.id.lunch_items);
                                food_dinner=findViewById(R.id.dinner_items);
                                if(plan_number.toString().equals("[0]") )
                                {
                                    plan_name+="balance_diet";
                                }
                                else if(plan_number.toString().equals("[1]"))
                                {
                                    plan_name+="balance_diet";
                                }
                                else if(plan_number.toString().equals("[2]"))
                                {
                                    plan_name+="high_carbo_diet";
                                }
                                 else if(plan_number.toString().equals("[3]"))
                                {
                                    plan_name+="ketogenic_diet";
                                }
                                 else if(plan_number.toString().equals("[4]"))
                                {
                                    plan_name+="low_carbo_diet";
                                }
                                 else if(plan_number.toString().equals("[5]"))
                                {
                                    plan_name+="zone_diet";
                                }
                                else
                                {
                                    Toast.makeText(view_my_plan.this,"Error in retrieving ",Toast.LENGTH_SHORT).show();
                                }
                                DatabaseReference reference1;
                                reference1= FirebaseDatabase.getInstance().getReference(plan_name.toString());
                                reference1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().exists()) {

                                                DataSnapshot dataSnapshot = task.getResult();
                                                fire_breakfast = String.valueOf(dataSnapshot.child("breakfast").getValue());
                                                fire_lunch = String.valueOf(dataSnapshot.child("lunch").getValue());
                                                fire_dinner = String.valueOf(dataSnapshot.child("dinner").getValue());
                                                food_breakfast.setText(fire_breakfast);
                                                food_lunch.setText(fire_lunch);
                                                food_dinner.setText(fire_dinner);
                                            }
                                        }
                                    }
                                });


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Toast.makeText(view_my_plan.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    });

// Access the RequestQueue through your singleton class.
            queue.add(jsonObjectRequest);
            excercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(view_my_plan.this,My_Excercise.class);
                    i.putExtra("target",get_target);
                    startActivity(i);
                }
            });
        }
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