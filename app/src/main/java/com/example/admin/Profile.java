
package com.example.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class Profile extends AppCompatActivity implements View.OnClickListener {

  BottomNavigationView bottomNavigationView;
Button upload;

EditText name,age,email,address,phone_no;
ImageView iv;
    Uri selectedimageuri;
int SELECT_PICTURE=200;
String get_user_mobile_no,fire_name,fire_email,fire_address,fire_phoneno,fire_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        get_user_mobile_no=getIntent().getStringExtra("user_mobile_number");
        iv=findViewById(R.id.user_image);
        DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference("Admin_users");
        reference.child(get_user_mobile_no).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {

                        DataSnapshot dataSnapshot=task.getResult();
                        fire_name= String.valueOf(dataSnapshot.child("name").getValue());
                        fire_age= String.valueOf(dataSnapshot.child("age").getValue());
                        fire_email= String.valueOf(dataSnapshot.child("email").getValue());
                        fire_address= String.valueOf(dataSnapshot.child("add").getValue());
                        fire_phoneno= String.valueOf(dataSnapshot.child("phoneno").getValue());
                        StorageReference storageReference= FirebaseStorage.getInstance().getReference("Admin_users/"+fire_name);
                        try {
                            File localFile=File.createTempFile("img",".jpeg");
                            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    iv.setImageBitmap(bitmap);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        name=(EditText) findViewById(R.id.update_user_name);
                        age=(EditText)findViewById(R.id.age);
                        email=(EditText)findViewById(R.id.Email);
                        address=(EditText)findViewById(R.id.PostalAddress);
                        name.setText(fire_name);
                        age.setText(fire_age);
                        email.setText(fire_email);
                        address.setText(fire_address);
                    }
                    else
                    {
                        Toast.makeText(Profile.this,"Problem",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
         upload=findViewById(R.id.update);
         upload.setOnClickListener(this);
        bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
                Intent i;
                switch(item.getItemId())
                {

                    case R.id.home:
                        i=new Intent(Profile.this,MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(Profile.this,login_activity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.update:
                update_data();
                break;

        }

    }

    private void update_data() {
        DatabaseReference reference;
        String updated_name=name.getText().toString();
        String updated_address=address.getText().toString();
        String updated_email=email.getText().toString();
        String updated_age=age.getText().toString();
        String updated_phno=fire_phoneno.toString();
        HashMap updated_user=new HashMap();
        updated_user.put("name",updated_name);
        updated_user.put("email",updated_email);
        updated_user.put("age",updated_age);
        updated_user.put("add",updated_address);
        updated_user.put("phno",updated_phno);
        reference = FirebaseDatabase.getInstance().getReference("Admin_users");
        reference.child(get_user_mobile_no).setValue(updated_user).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Profile.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                    Intent k = new Intent(Profile.this, MainActivity.class);
                    startActivity(k);
                }
                else
                {
                    Toast.makeText(Profile.this,"error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
