package com.example.crudmovil;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    private Button btnIngresar;
    private EditText etEmail;
    private EditText etPassword;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        firebaseAuth = FirebaseAuth.getInstance();

        btnIngresar = findViewById(R.id.btnIngresar);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
    }


    public void ingresar(View view) {

        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        if (email.equals("") || password.equals("")) {
            validacion(email, password);
        }
        else {
            /*Va a la base de datos a validar lo ingresado*/
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    /*Si se pudo iniiar sesión, iremos a la siguiente ventana*/
                    if (task.isSuccessful()) {
                        Intent i = new Intent(Login.this, MenuActivity.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(Login.this, "Datos erroneos", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    public void validacion(String usuario, String password) {


        if (usuario.equals("")){
            etEmail.setError("Campo requerido");
        }
        if (password.equals("")){
            etPassword.setError("Campo requerido");
        }

    }
}