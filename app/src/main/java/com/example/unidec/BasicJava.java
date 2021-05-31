package com.example.unidec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class BasicJava extends AppCompatActivity {
    private TextView texto1,texto2;
    private LottieAnimationView pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_java);
        texto1=findViewById(R.id.contenido1);
        texto2=findViewById(R.id.contenido2);
        pro=findViewById(R.id.progressB);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();
        db.setFirestoreSettings(settings);
        pro.setVisibility(View.VISIBLE);
        DocumentReference docRef = db.collection("cursos").document("java");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        pro.setVisibility(View.INVISIBLE);
                        String da =document.get("uBasic1").toString();
                        texto1.setText(da);
                        da =document.get("uBasic2").toString();
                        texto2.setText(da);
                    }
                }
            }
        });
    }
    public void back(View view){
        startActivity(new Intent(getApplicationContext(), Calcula.class));
    }
    public void wel(View view){
        startActivity(new Intent(getApplicationContext(), WelJava.class));
    }
}