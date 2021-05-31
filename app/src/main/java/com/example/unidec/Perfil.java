package com.example.unidec;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Perfil extends AppCompatActivity {
    private static final int GALLERY_INTENT=1;
    private ImageButton photo;
    private TextView uss,las,ins,nick,ema;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        uss=findViewById(R.id.name2 );
        las=findViewById(R.id.last2 );
        ins=findViewById(R.id.inst2);
        nick=findViewById(R.id.uss2);
        ema=findViewById(R.id.email2);
        photo=findViewById(R.id.phoPe);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        id=fAuth.getCurrentUser().getUid();
        final DocumentReference documentReference=fStore.collection("usuarios").document(id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                uss.setText(documentSnapshot.getString("uNombre"));
                las.setText(documentSnapshot.getString("uApellido"));
                ins.setText(documentSnapshot.getString("uInstitution"));
                nick.setText(documentSnapshot.getString("uUsser"));
                ema.setText(documentSnapshot.getString("uEmail"));
            }
        });
    }
    public void cerrar(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}