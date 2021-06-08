package com.example.crudmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    private Button btnCerrar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firebaseAuth = FirebaseAuth.getInstance();
        btnCerrar = (Button) findViewById(R.id.btnCerrar);
    }

    /*public void cliente(View view) {
        Intent i = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(i);
    }*/

    public void pedido(View view) {
        Intent j = new Intent(MenuActivity.this, PedidoActivity.class);
        startActivity(j);
    }

    public void cerrarSesion(View view) {
        firebaseAuth.signOut();
        startActivity(new Intent(MenuActivity.this, Login.class));
        finish(); /*Finaliza tarea para que no pueda volver hacia atrás cuando cierre sesión*/
    }
}