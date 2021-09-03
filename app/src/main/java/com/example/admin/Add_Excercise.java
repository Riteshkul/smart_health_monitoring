package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Add_Excercise extends AppCompatActivity implements View.OnClickListener{
    BottomNavigationView bottomNavigationView;
    Button btn_upload,submit;

    String selected="";
    RadioGroup radioGp;
    RadioButton radioButton;
    FirebaseDatabase rootnode;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference reference;
    EditText name,type_ex;
    ImageView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__excercise);

        name=findViewById(R.id.excercise_name);
        type_ex=findViewById(R.id.type);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(this);

        bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch(item.getItemId())
                {
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(Add_Excercise.this,login_activity.class);
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
        switch(v.getId())
        {

            case R.id.submit:
                insert();
                break;
        }

    }

    private void insert() {
        final String nm=name.getText().toString();
        final String ty=type_ex.getText().toString();
        if (TextUtils.isEmpty(nm)){
            name.setError("please enter Name");
        }
        else
        {

            rootnode=FirebaseDatabase.getInstance();
            reference=rootnode.getReference("Excercises");


            DatabaseReference fbDb = null;
            if (fbDb == null) {
                fbDb = FirebaseDatabase.getInstance().getReference();
            }
            fbDb.child("Excercises/"+ty)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            // get total available quest
                                                            int size = (int) dataSnapshot.getChildrenCount();
                                                            int x=size+1;

                                                            reference.child(ty).child("Excercise"+x).setValue(nm);
                                                            Toast.makeText(Add_Excercise.this,"Excercise added successfully",Toast.LENGTH_SHORT).show();
                                                            name.setText("");
                                                            type_ex.setText("");
                                                        }
                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }

                                                    }
                    );


        }
    }

}
