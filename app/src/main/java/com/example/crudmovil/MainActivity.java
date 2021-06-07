package com.example.crudmovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crudmovil.model.Cliente;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etId;
    EditText etNombre;
    EditText etDireccion;
    EditText etTelefono;
    ListView listvClientes;

    /*lista*/
    private List<Cliente> listCliente = new ArrayList<Cliente>();
    ArrayAdapter<Cliente> arrayAdapterCliente;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Cliente clienteSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etId);
        etNombre = findViewById(R.id.etNombre);
        etDireccion = findViewById(R.id.etDireccion);
        etTelefono = findViewById(R.id.etTelefono);

        listvClientes = findViewById(R.id.listvClientes);

        inicializarFirebase();
        listarDatos();
        /*Metodo para seleccionar y traer su contenido a los EditText*/
        listvClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clienteSeleccionado = (Cliente) parent.getItemAtPosition(position);
                etId.setText(clienteSeleccionado.getId_cliente());
                etNombre.setText(clienteSeleccionado.getNombre());
                etDireccion.setText(clienteSeleccionado.getDireccion());
                etTelefono.setText(clienteSeleccionado.getTelefono());
            }
        });
    }

    private void listarDatos() {
        databaseReference.child("Cliente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCliente.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    Cliente cliente = objSnaptshot.getValue(Cliente.class);
                    listCliente.add( cliente);

                    arrayAdapterCliente = new ArrayAdapter<Cliente>(MainActivity.this, android.R.layout.simple_list_item_1, listCliente);
                    listvClientes.setAdapter(arrayAdapterCliente);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inicializarFirebase() {

        FirebaseApp.initializeApp(this); //Pasamos contexto this
        firebaseDatabase = FirebaseDatabase.getInstance(); // Pasamos instancia
        databaseReference = firebaseDatabase.getReference(); //Pasamos referencia
    }

    String idCliente;
    String nombre;
    String direccion;
    String telefono;

    public void crear (View view) {

        idCliente = etId.getText().toString();
        nombre = etNombre.getText().toString();
        direccion = etDireccion.getText().toString();
        telefono = etTelefono.getText().toString();

        if (nombre.equals("") || direccion.equals("") || telefono.equals("")) {
            validacion(nombre, direccion, telefono);
        }else {
            Cliente cliente = new Cliente();
            cliente.setId_cliente(idCliente);
            cliente.setNombre(nombre);
            cliente.setDireccion(direccion);
            cliente.setTelefono(telefono);
            /*Posible problema con el id cliente, por su tipo*/
            databaseReference.child("Cliente").child(cliente.getId_cliente()).setValue(cliente);

            Toast.makeText(this, "Registro agregado", Toast.LENGTH_LONG).show();
            limpiarCampo();
        }
    }


    public void actualizar (View view) {
        Cliente cliente = new Cliente();
        cliente.setId_cliente(etId.getText().toString().trim());
        cliente.setNombre(etNombre.getText().toString().trim());
        cliente.setDireccion(etDireccion.getText().toString().trim());
        cliente.setTelefono(etTelefono.getText().toString().trim());

        /*Accedemos a nuestra DatabaseReference*/
        /*Posible error por id*/
        databaseReference.child("Cliente").child(cliente.getId_cliente()).setValue(cliente);
        Toast.makeText(this, "Registro actualizado", Toast.LENGTH_LONG).show();
        limpiarCampo();
    }

    public void eliminar (View view) {
        Cliente cliente = new Cliente();
        cliente.setId_cliente(clienteSeleccionado.getId_cliente());

        databaseReference.child("Cliente").child(cliente.getId_cliente()).removeValue();
        Toast.makeText(this, "Eliminado", Toast.LENGTH_LONG).show();
        limpiarCampo();
    }

    /*Validación de campos*/
    public void validacion(String nombre, String direccion, String telefono) {


            if (nombre.equals("")){
                etNombre.setError("Campo requerido");
            }
            if (direccion.equals("")){
                etDireccion.setError("Campo requerido");
            }
            if (telefono.equals("")){
                etTelefono.setError("Campo requerido");
            }
    }

    public void limpiarCampo() {

        etId.setText("");
        etNombre.setText("");
        etDireccion.setText("");
        etTelefono.setText("");
    }

    public void atras(View view) {

        startActivity(new Intent(MainActivity.this, MenuActivity.class));
        finish();
    }
}