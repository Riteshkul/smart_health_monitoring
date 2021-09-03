package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class login_activity extends AppCompatActivity implements View.OnClickListener {
Button b1,b2;
EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b1=findViewById(R.id.get_otp);
        b2=findViewById(R.id.create_account);
        phone=findViewById(R.id.mobile_no);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.get_otp:
                get_otp_for_user();
                break;
            case R.id.create_account:
                Intent i=new Intent(login_activity.this,Registeration.class);
                startActivity(i);
                break;
        }
    }

    private void get_otp_for_user() {
        DatabaseReference reference;

        if (TextUtils.isEmpty(phone.getText().toString())){
            phone.setError("please enter Name");
        }
        reference= FirebaseDatabase.getInstance().getReference("Admin_users");
        reference.child(phone.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        if(task.getResult().exists())
                        {
                            Intent i=new Intent(login_activity.this,manage_otp.class);
                            i.putExtra("mobile_no","+91"+phone.getText().toString());
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(login_activity.this,"Please Enter Registered mobile no",Toast.LENGTH_LONG).show();
                        }
                    }

            }
        });

    }
}