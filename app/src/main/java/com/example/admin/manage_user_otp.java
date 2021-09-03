package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class manage_user_otp extends AppCompatActivity {
    EditText otp;
    Button verify_otp;
    String otp_id;
    String no;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user_otp);
        no = getIntent().getStringExtra("mobile_no").toString();
        otp = findViewById(R.id.verify_otp);
        verify_otp = findViewById(R.id.verify_singin);
        initializeotp();
        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp.getText().toString().isEmpty())
                {
                    Toast.makeText(manage_user_otp.this,"Empty Field",Toast.LENGTH_SHORT).show();
                }
                else if(otp.getText().toString().length()!=6)
                {
                    Toast.makeText(manage_user_otp.this,"Invalid OTP",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(otp_id,otp.getText().toString());
                    signinwithPhoneAuthentication(credential);
                }
            }
        });



    }

    private void initializeotp()
    {
        auth = FirebaseAuth.getInstance();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(no)
                .setTimeout(120L, TimeUnit.SECONDS)
                .setActivity(manage_user_otp.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        otp_id=s;
                        super.onCodeSent(s, forceResendingToken);
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                        signinwithPhoneAuthentication(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                        Toast.makeText(manage_user_otp.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void  signinwithPhoneAuthentication(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent i=new Intent(manage_user_otp.this,user_home.class);
                    i.putExtra("user_mobile_no",no);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(manage_user_otp.this,"SignIn code Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
