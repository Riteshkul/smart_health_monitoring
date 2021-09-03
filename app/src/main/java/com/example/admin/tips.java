package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class tips extends AppCompatActivity implements View.OnClickListener{
    BottomNavigationView bottomNavigationView;
    Button tip_btn;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    static  int i=1;
    EditText t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        t1=findViewById(R.id.tipname);
        t2=findViewById(R.id.tiptext);
        tip_btn=findViewById(R.id.tip_btn);
        tip_btn.setOnClickListener(this);
        bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch(item.getItemId())
                {
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(tips.this,login_activity.class);
                        startActivity(intent);
                        break;
                    case R.id.home:
                        i=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                        break;

                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("Tips");
        final String tip = t1.getText().toString();
        final String text = t2.getText().toString();
        if (TextUtils.isEmpty(tip)) {
            t1.setError("please enter name of tip");
        }
        if (TextUtils.isEmpty(text)) {
            t2.setError("please enter text of tip");
        } else {


            DatabaseReference fbDb = null;
            if (fbDb == null) {
                fbDb = FirebaseDatabase.getInstance().getReference();
            }


            fbDb.child("Tips/" + tip)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            // get total available quest
                                                            int size = (int) dataSnapshot.getChildrenCount();
                                                            String s = t1.getText().toString();
                                                            int x = size + 1;
                                                            reference.child(tip).child("Tip" + x).setValue(text);
                                                            t1.setText("");
                                                            t2.setText("");
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }

                                                    }
                    );



            Toast.makeText(this, "Tip Inserted Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}

