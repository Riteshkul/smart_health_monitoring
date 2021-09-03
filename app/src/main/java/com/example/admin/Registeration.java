package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Registeration extends AppCompatActivity implements View.OnClickListener{
Button b1,b2;
ImageView iv;
    int SELECT_PICTURE=200;
    Uri selectedimageuri;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    FirebaseAuth mauth;
    EditText email,name,age,add,phno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        email=(EditText) findViewById(R.id.Email);
        name=(EditText)findViewById(R.id.name);
        age=(EditText)findViewById(R.id.age);
        add=(EditText)findViewById(R.id.Address);
        phno=(EditText)findViewById(R.id.Phone);
        iv=findViewById(R.id.admin_image);
        b1=findViewById(R.id.register);
        b1.setOnClickListener(this);
        b2=findViewById(R.id.browse);
        b2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.browse:
                imagechooser();
                break;
            case R.id.register:
                add_data();
                break;

        }
    }
    private void imagechooser() {
        Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i,SELECT_PICTURE);
    }
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if(resultCode==RESULT_OK)
        {
            if(requestCode==SELECT_PICTURE)
            {
                selectedimageuri=data.getData();
                if(null!=selectedimageuri)
                    iv.setImageURI(selectedimageuri);
            }
        }
        else
        {
            Toast.makeText(this,"Problem",Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void add_data() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        final String em=email.getText().toString();
        final String nme=name.getText().toString();
        final String Age=age.getText().toString();
        final String ad1=add.getText().toString();
        final String no=phno.getText().toString();
        String regexStr = "[0-9]$";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(no.length()!=10 && no.matches(regexStr)==false  ) {
            phno.setError("please enter your Mobile no");
        }
        else if(em.matches(emailPattern)==false)
        {
            email.setError("please enter valid email");
        }
        if (TextUtils.isEmpty(em)){
            email.setError("please enter email");
        }
        else if (TextUtils.isEmpty(nme)) {
            name.setError("please enter your name");
        }
        else if (TextUtils.isEmpty(Age)) {
            age.setError("please enter your age");
        }
        else if (TextUtils.isEmpty(ad1)) {
            add.setError("please enter your address");
        }
        else if (TextUtils.isEmpty(no)) {
            phno.setError("please enter your Mobile no");
        }
        else if (iv.getDrawable()==null) {
            Toast.makeText(this,"Please select the image",Toast.LENGTH_SHORT).show();
        }
        else
        {
            rootnode=FirebaseDatabase.getInstance();
            reference=rootnode.getReference("Admin_users");
            add_user_helper addUserHelper=new add_user_helper(em,nme,ad1,Age,no);
            reference.child(phno.getText().toString()).setValue(addUserHelper);
            if (selectedimageuri != null) {
                ProgressDialog progressDialog
                        = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                // Defining the child of storageReference
                StorageReference ref
                        = storageReference
                        .child(
                                "Admin_users/"
                                        + name.getText().toString());

                // adding listeners on upload
                // or failure of image
                ref.putFile(selectedimageuri)
                        .addOnSuccessListener(
                                new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                    @Override
                                    public void onSuccess(
                                            UploadTask.TaskSnapshot taskSnapshot)
                                    {
                                        progressDialog.dismiss();

                                    }
                                })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {

                                // Error, Image not uploaded
                                progressDialog.dismiss();
                                Toast
                                        .makeText(getApplicationContext(),
                                                "Failed " + e.getMessage(),
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .addOnProgressListener(
                                new OnProgressListener<UploadTask.TaskSnapshot>() {

                                    // Progress Listener for loading
                                    // percentage on the dialog box
                                    @Override
                                    public void onProgress(
                                            UploadTask.TaskSnapshot taskSnapshot)
                                    {
                                        double progress
                                                = (100.0
                                                * taskSnapshot.getBytesTransferred()
                                                / taskSnapshot.getTotalByteCount());
                                        progressDialog.setMessage(
                                                "Uploaded "
                                                        + (int)progress + "%");

                                    }
                                });
            }
Intent i=new Intent(Registeration.this,login_activity.class);
            startActivity(i);
        }
    }

    private class add_user_helper {
        String email,name,add,age,phoneno;

        public add_user_helper(String email, String name, String add, String age, String phoneno) {
            this.email = email;
            this.name = name;
            this.add = add;
            this.age = age;
            this.phoneno = phoneno;
        }

        public add_user_helper() {
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getPhoneno() {
            return phoneno;
        }

        public void setPhoneno(String phoneno) {
            this.phoneno = phoneno;
        }
    }
}