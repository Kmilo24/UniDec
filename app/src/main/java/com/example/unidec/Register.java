package com.example.unidec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private TextInputEditText nam, last, uss, ema, pass, conPas, inst;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private String userID;
    private LottieAnimationView pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inst=findViewById(R.id.instEd);
        nam=findViewById(R.id.firstNaEd);
        last=findViewById(R.id.lastNaEd);
        uss=findViewById(R.id.usserEd);
        ema=findViewById(R.id.emailEd);
        pass=findViewById(R.id.passwordEd);
        conPas=findViewById(R.id.conPassdEd);
        pro=findViewById(R.id.progressBR);
    }
    public void sendBt(View view){

        final String name=nam.getText().toString();
        final String lastName=last.getText().toString();
        final String usua=uss.getText().toString();
        final String ninst=inst.getText().toString();
        final String email=ema.getText().toString().trim();
        String password=pass.getText().toString().trim();
        String confPass=conPas.getText().toString().trim();

        fStore=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();

        if(TextUtils.isEmpty(name)){
            nam.setError("required field");
        }else if(TextUtils.isEmpty(lastName)){
            last.setError("required field");
        }else if(TextUtils.isEmpty(usua)){
            uss.setError("required field");
        }else if (TextUtils.isEmpty(email)){
            ema.setError("required field");
        }else if(TextUtils.isEmpty(password)){
            pass.setError("required field");
        }else if(TextUtils.isEmpty(confPass)){
            conPas.setError("required field");
        }else if(password.length()<6) {
            pass.setError("very short password");
        }else if(TextUtils.isEmpty(ninst)){
            inst.setError("required field");
        }else if(password.equals(confPass)) {
            pro.setVisibility(View.VISIBLE);
            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Register.this, "registered user", Toast.LENGTH_SHORT).show();
                        userID=fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference=fStore.collection("usuarios").document(userID);
                        Map<String,Object> user=new HashMap<>();
                        user.put("uNombre",name);
                        user.put("uApellido",lastName);
                        user.put("uUsser",usua);
                        user.put("uEmail",email);
                        user.put("uInstitution",ninst);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG","onSuccess: user Profile is created  for: "+userID);
                            }
                        });
                        pro.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }else{
                        pro.setVisibility(View.INVISIBLE);
                        Toast.makeText(Register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(this, "password does not match", Toast.LENGTH_SHORT).show();
        }
    }
}