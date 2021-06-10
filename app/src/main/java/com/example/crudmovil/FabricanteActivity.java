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

import com.example.crudmovil.model.Fabricante;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FabricanteActivity extends AppCompatActivity {

    private EditText etId_fabricante;
    private EditText etId_productoFr;
    private EditText etNombreFr;
    private EditText etDireccionFr;


    private ListView listvFabricante;

    /*Lista*/
    private List<Fabricante> listFabricante = new ArrayList<Fabricante>();
    ArrayAdapter<Fabricante> arrayAdapterFabricante;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Fabricante fabricanteSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabricante);

        etId_fabricante = findViewById(R.id.etId_fabricante);
        etId_productoFr = findViewById(R.id.etId_productoFr);
        etNombreFr = findViewById(R.id.etNombreFr);
        etDireccionFr = findViewById(R.id.etDireccionFr);

        listvFabricante = findViewById(R.id.listvFabricante);

        inicializarFirebase();
        listarDatos();
        /*Metodo para seleccionar y traer su contenido a los EditText*/
        listvFabricante.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fabricanteSeleccionado = (Fabricante) parent.getItemAtPosition(position);
                etId_fabricante.setText(fabricanteSeleccionado.getId_fabricante());
                etId_productoFr.setText(fabricanteSeleccionado.getId_productoFr());
                etNombreFr.setText(fabricanteSeleccionado.getNombreFr());
                etDireccionFr.setText(fabricanteSeleccionado.getDireccionFr());
            }
        });
    }

    private void listarDatos() {
        databaseReference.child("Fabricante").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listFabricante.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    Fabricante fabricante = objSnaptshot.getValue(Fabricante.class);
                    listFabricante.add(fabricante);

                    arrayAdapterFabricante = new ArrayAdapter<Fabricante>(FabricanteActivity.this, android.R.layout.simple_list_item_1, listFabricante);
                    listvFabricante.setAdapter(arrayAdapterFabricante);
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

    private String idFafricante;
    private String idProducto;
    private String nombre;
    private String direccion;

    public void crearFr (View view) {

        idFafricante = etId_fabricante.getText().toString();
        idProducto = etId_productoFr.getText().toString();
        nombre = etNombreFr.getText().toString();
        direccion = etDireccionFr.getText().toString();

        if (idFafricante.equals("") || idProducto.equals("") || nombre.equals("") || direccion.equals("")) {
            validacionFr(idFafricante, idProducto, nombre, direccion);
        }else {
            Fabricante fabricante = new Fabricante();
            /*Campos para  Firebase*/
            fabricante.setId_fabricante(idFafricante);
            fabricante.setId_productoFr(idProducto);
            fabricante.setNombreFr(nombre);
            fabricante.setDireccionFr(direccion);
            /*Posible problema con el id cliente, por su tipo*/
            /*Crea la tabla Pedido y desglosa información por id*/
            databaseReference.child("Fabricante").child(fabricante.getId_fabricante()).setValue(fabricante);

            Toast.makeText(this, "Registro agregado", Toast.LENGTH_LONG).show();
            limpiarCampo();
        }
    }

    public void actualizarFr (View view) {
        Fabricante fabricante = new Fabricante();
        fabricante.setId_fabricante(etId_fabricante.getText().toString().trim());
        fabricante.setId_productoFr(etId_productoFr.getText().toString().trim());
        fabricante.setNombreFr(etNombreFr.getText().toString().trim());
        fabricante.setDireccionFr(etDireccionFr.getText().toString().trim());

        /*Accedemos a nuestra DatabaseReference*/
        /*Posible error por id*/
        databaseReference.child("Fabricante").child(fabricante.getId_fabricante()).setValue(fabricante);
        Toast.makeText(this, "Registro actualizado", Toast.LENGTH_LONG).show();
        limpiarCampo();
    }

    public void eliminarFr (View view) {
        Fabricante fabricante = new Fabricante();
        fabricante.setId_fabricante(fabricante.getId_fabricante());

        databaseReference.child("Fabricante").child(fabricante.getId_fabricante()).removeValue();
        Toast.makeText(this, "Eliminado", Toast.LENGTH_LONG).show();
        limpiarCampo();
    }

    /*Validación de campos*/
    public void validacionFr(String idFafricante, String idProducto, String nombre, String direccion) {

        if (idFafricante.equals("")){
            etId_fabricante.setError("Campo requerido");
        }
        if (idProducto.equals("")){
            etId_productoFr.setError("Campo requerido");
        }
        if (nombre.equals("")){
            etNombreFr.setError("Campo requerido");
        }
        if (direccion.equals("")){
            etDireccionFr.setError("Campo requerido");
        }
    }

    private void limpiarCampo() {

        etId_fabricante.setText("");
        etId_productoFr.setText("");
        etNombreFr.setText("");
        etDireccionFr.setText("");
    }

    public void atrasFr(View view) {

        startActivity(new Intent(FabricanteActivity.this, MenuActivity.class));
        finish();
    }
}