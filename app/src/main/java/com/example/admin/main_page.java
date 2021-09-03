package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class main_page extends AppCompatActivity {
RadioGroup radioGroup;
Button start_btn;
String task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        start_btn=findViewById(R.id.start);
        radioGroup=findViewById(R.id.radioGroup2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.admin:
                       task="admin";
                        break;
                    case R.id.user:
                        task="user";
                        break;
                }
            }
        });
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(task.equals("user"))
                    {
                        Intent intent1=new Intent(main_page.this,user_login.class);
                        startActivity(intent1);
                    }
                    else
                    {
                        Intent intent=new Intent(main_page.this,login_activity.class);
                        startActivity(intent);
                    }
            }
        });
    }
}