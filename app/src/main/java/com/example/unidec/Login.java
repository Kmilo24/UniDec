package com.example.unidec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private TextInputEditText edUss,edPass;


    private LottieAnimationView pro;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }
    public void reBt(View view){
        startActivity(new Intent(getApplicationContext(), Register.class));
    }
    public void goBt(View view){
        pro=findViewById(R.id.progressB);
        mAuth = FirebaseAuth.getInstance();
        edUss=findViewById(R.id.usrEd);
        edPass=findViewById(R.id.passEd);

        String email=edUss.getText().toString().trim();
        String password=edPass.getText().toString().trim();
        pro.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(email)){
            pro.setVisibility(View.INVISIBLE);
            edUss.setError("required field");
        }else if(TextUtils.isEmpty(password)){
            pro.setVisibility(View.INVISIBLE);
            edPass.setError("required field");
        }else{
           pro.setVisibility(View.VISIBLE);
           mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Welcome", Toast.LENGTH_SHORT).show();
                        pro.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(getApplicationContext(), Principal.class));
                    } else {
                        pro.setVisibility(View.INVISIBLE);
                        Toast.makeText(Login.this, "Sorry, check your details", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}