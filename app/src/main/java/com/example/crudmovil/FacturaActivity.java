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

import com.example.crudmovil.model.Factura;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacturaActivity extends AppCompatActivity {


    private EditText etId_factura;
    private EditText etId_productoFac;
    private EditText etFechaFac;
    private EditText etValorFac;

    private ListView listvFactura;

    /*Lista*/
    private List<Factura> listFactura = new ArrayList<Factura>();
    ArrayAdapter<Factura> arrayAdapterFactura;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Factura facturaSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        etId_factura = findViewById(R.id.etId_factura);
        etId_productoFac = findViewById(R.id.etId_ProductoFac);
        etFechaFac = findViewById(R.id.etFechaFac);
        etValorFac = findViewById(R.id.etValorFac);


        listvFactura = findViewById(R.id.listvFactura);

        inicializarFirebase();
        listarDatos();
        /*Metodo para seleccionar y traer su contenido a los EditText*/
        listvFactura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                facturaSeleccionado = (Factura) parent.getItemAtPosition(position);
                etId_factura.setText(facturaSeleccionado.getId_factura());
                etId_productoFac.setText(facturaSeleccionado.getId_productoFac());
                etFechaFac.setText(facturaSeleccionado.getFechaFac());
                etValorFac.setText(facturaSeleccionado.getValorFac());
            }
        });
    }

    private void listarDatos() {
        databaseReference.child("Factura").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listFactura.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    Factura factura = objSnaptshot.getValue(Factura.class);
                    listFactura.add(factura);

                    arrayAdapterFactura = new ArrayAdapter<Factura>(FacturaActivity.this, android.R.layout.simple_list_item_1, listFactura);
                    listvFactura.setAdapter(arrayAdapterFactura);
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

    private String idFactura;
    private String idProducto;
    private String fecha;
    private String valor;

    public void crearFac (View view) {

        idFactura = etId_factura.getText().toString();
        idProducto = etId_productoFac.getText().toString();
        fecha = etFechaFac.getText().toString();
        valor = etValorFac.getText().toString();

        if (idFactura.equals("") || idProducto.equals("") || fecha.equals("") || valor.equals("")) {
            validacionFac(idFactura, idProducto, fecha, valor);
        }else {
            Factura factura = new Factura();
            /*Campos para  Firebase*/
            factura.setId_factura(idFactura);
            factura.setId_productoFac(idProducto);
            factura.setFechaFac(fecha);
            factura.setValorFac(valor);
            /*Posible problema con el id cliente, por su tipo*/
            /*Crea la tabla Pedido y desglosa información por id*/
            databaseReference.child("Factura").child(factura.getId_factura()).setValue(factura);

            Toast.makeText(this, "Registro agregado", Toast.LENGTH_LONG).show();
            limpiarCampo();
        }
    }

    public void actualizarFac (View view) {

        Factura factura = new Factura();
        factura.setId_factura(etId_factura.getText().toString().trim());
        factura.setId_productoFac(etId_productoFac.getText().toString().trim());
        factura.setFechaFac(etFechaFac.getText().toString().trim());
        factura.setValorFac(etValorFac.getText().toString().trim());

        /*Accedemos a nuestra DatabaseReference*/
        /*Posible error por id*/
        databaseReference.child("Factura").child(factura.getId_factura()).setValue(factura);
        Toast.makeText(this, "Registro actualizado", Toast.LENGTH_LONG).show();
        limpiarCampo();

    }

    public void eliminarFac (View view) {
        Factura factura = new Factura();
        factura.setId_factura(facturaSeleccionado.getId_factura());

        databaseReference.child("Factura").child(factura.getId_factura()).removeValue();
        Toast.makeText(this, "Eliminado", Toast.LENGTH_LONG).show();
        limpiarCampo();
    }

    /*Validación de campos*/
    public void validacionFac(String idFactura, String idProducto, String fecha, String valor) {

        if (idFactura.equals("")){
            etId_factura.setError("Campo requerido");
        }
        if (idProducto.equals("")){
            etId_productoFac.setError("Campo requerido");
        }
        if (fecha.equals("")){
            etFechaFac.setError("Campo requerido");
        }
        if (valor.equals("")){
            etValorFac.setError("Campo requerido");
        }
    }

    private void limpiarCampo() {

        etId_factura.setText("");
        etId_productoFac.setText("");
        etFechaFac.setText("");
        etValorFac.setText("");
    }

    public void atrasFac(View view) {

        startActivity(new Intent(FacturaActivity.this, MenuActivity.class));
        finish();
    }
}