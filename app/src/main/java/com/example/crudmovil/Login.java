package com.example.crudmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnIngresar = findViewById(R.id.btnIngresar);
    }

    public void ingresar(View view) {
        Intent i = new Intent(Login.this, MainActivity.class);
        startActivity(i);
    }
}