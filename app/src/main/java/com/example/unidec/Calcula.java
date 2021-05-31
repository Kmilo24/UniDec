package com.example.unidec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Calcula extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcula);

    }
    public void back(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), Principal.class));
    }

    public void wel(View view){
        startActivity(new Intent(getApplicationContext(), WelJava.class));
    }
    public void basi(View view){
        startActivity(new Intent(getApplicationContext(), BasicJava.class));
    }
}