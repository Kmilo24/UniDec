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

public class Principal extends AppCompatActivity {
    private TextView uss,in;
    private ImageButton photo;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private StorageReference mStorage;
    private String id;
    private static final int GALLERY_INTENT=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        uss=findViewById(R.id.name );

        in=findViewById(R.id.inst);
        photo=findViewById(R.id.phoPe);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        id=fAuth.getCurrentUser().getUid();
        mStorage= FirebaseStorage.getInstance().getReference();
        final DocumentReference documentReference=fStore.collection("usuarios").document(id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                uss.setText(documentSnapshot.getString("uUsser"));
                in.setText(documentSnapshot.getString("uInstitution"));
            }
        });
    }
    public void per(View view){
        startActivity(new Intent(getApplicationContext(), Perfil.class));
    }
    public void sendPho(View view){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_INTENT);
        String namePo=uss.getText().toString();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT&&resultCode==RESULT_OK){
            Uri uri=data.getData();

            StorageReference filePath=mStorage.child(uss.getText().toString()).child(uss.getText().toString());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Principal.this, "Perfect", Toast.LENGTH_SHORT).show();
                    Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                    String url = uri.toString();
                    Picasso
                            .with(Principal.this)
                            .load(uri)
                            .resize(200, 200)
                            .placeholder(R.drawable.ic_baseline_person_24)
                            .into(photo);
                }
            });
        }

    }
    public void cerrar(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
    public void calcu(View view){
        startActivity(new Intent(getApplicationContext(), Calcula.class));
    }

}