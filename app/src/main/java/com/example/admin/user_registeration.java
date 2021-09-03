package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class user_registeration extends AppCompatActivity {
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    FirebaseAuth mauth;
    String gender;
    EditText user_name,user_email,user_add,user_age,user_phno,user_weight,user_height;
    CheckBox male_ch,female_ch;
    Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        user_name=findViewById(R.id.name);
        user_email=findViewById(R.id.Email);
        user_add=findViewById(R.id.Address);
        male_ch=findViewById(R.id.male);
        female_ch=findViewById(R.id.female);
        user_age=findViewById(R.id.age);
        user_height=findViewById(R.id.height);
        user_weight=findViewById(R.id.Weight);
        user_phno=findViewById(R.id.Phone);

        reg=(Button)findViewById(R.id.register);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String em = user_email.getText().toString();
                final String nme = user_name.getText().toString();
                final String Age = user_age.getText().toString();
                final String ad1 = user_add.getText().toString();
                final String no = user_phno.getText().toString();
                final String height = user_height.getText().toString();
                final String weight = user_weight.getText().toString();
                String regexStr = "[0-9]$";
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(no.length()!=10 && no.matches(regexStr)==false  ) {
                    user_phno.setError("please enter valid 10 digit Mobile no");
                }
                else if(em.matches(emailPattern)==false)
                {
                    user_email.setError("please enter valid email");
                }
                else if (TextUtils.isEmpty(em)) {
                    user_email.setError("please enter email");
                } else if (TextUtils.isEmpty(nme)) {
                    user_name.setError("please enter your name");
                } else if (TextUtils.isEmpty(Age)) {
                    user_age.setError("please enter your age");
                } else if (TextUtils.isEmpty(ad1)) {
                    user_add.setError("please enter your address");
                } else if (TextUtils.isEmpty(no)) {
                    user_phno.setError("please enter your Mobile no");
                } else if (TextUtils.isEmpty(height)) {
                    user_height.setError("please enter your Mobile no");
                } else if (TextUtils.isEmpty(weight)) {
                    user_weight.setError("please enter your Mobile no");
                }

                else
                    {
                        if(male_ch.isChecked())
                        {
                            gender="male";
                        }
                        else if(female_ch.isChecked())
                        {
                            gender="female";
                        }

                        rootnode=FirebaseDatabase.getInstance();
                        reference=rootnode.getReference("Users");
                        user_helper addUserHelper=new user_helper(em,nme,ad1,no,Age,height,weight,gender);
                        reference.child(no).setValue(addUserHelper).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                           Toast.makeText(user_registeration.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(user_registeration.this,user_login.class);
                                startActivity(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                           Toast.makeText(user_registeration.this,"Registeration Failed",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            }
        });
    }

    private class user_helper {
        String email, name, add, phoneno,age,height,weight,gender;

        public user_helper() {
        }

        public user_helper(String email, String name, String add, String phoneno, String age, String height, String weight, String gender) {
            this.email = email;
            this.name = name;
            this.add = add;
            this.phoneno = phoneno;
            this.age = age;
            this.height = height;
            this.weight = weight;
            this.gender = gender;
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

        public String getPhoneno() {
            return phoneno;
        }

        public void setPhoneno(String phoneno) {
            this.phoneno = phoneno;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}