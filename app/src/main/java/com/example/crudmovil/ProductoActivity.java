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

import com.example.crudmovil.model.Producto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductoActivity extends AppCompatActivity {

    private EditText etId_producto;
    private EditText etId_pedidoPro;
    private EditText etId_fabricantePro;
    private EditText etNombrePro;
    private EditText etValorPro;

    private ListView listvProducto;

    /*Lista*/
    private List<Producto> listProducto = new ArrayList<Producto>();
    ArrayAdapter<Producto> arrayAdapterProducto;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Producto productoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        etId_producto = findViewById(R.id.etId_producto);
        etId_pedidoPro = findViewById(R.id.etId_pedidoPro);
        etId_fabricantePro = findViewById(R.id.etId_fabricantePro);
        etNombrePro = findViewById(R.id.etNombrePro);
        etValorPro = findViewById(R.id.etValorPro);

        listvProducto = findViewById(R.id.listvProducto);

        inicializarFirebase(); //Primero debe estar primero
        listarDatos();
        /*Metodo para seleccionar y traer su contenido a los EditText*/
        listvProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productoSeleccionado = (Producto) parent.getItemAtPosition(position);
                etId_producto.setText(productoSeleccionado.getId_producto());
                etId_pedidoPro.setText(productoSeleccionado.getId_pedidoPro());
                etId_fabricantePro.setText(productoSeleccionado.getId_fabricantePro());
                etNombrePro.setText(productoSeleccionado.getNombrePro());
                etValorPro.setText(productoSeleccionado.getValorPro());
            }
        });
    }

    private void listarDatos() {
        databaseReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listProducto.clear(); //Elimina cache
                //Se obtiene datos del hijo
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    Producto producto = objSnaptshot.getValue(Producto.class);
                    listProducto.add(producto);

                    //Pendiente
                    arrayAdapterProducto = new ArrayAdapter<Producto>(ProductoActivity.this, android.R.layout.simple_list_item_1, listProducto);
                    listvProducto.setAdapter(arrayAdapterProducto);
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

    private String idProducto;
    private String idPedido;
    private String idFabricante;
    private String nombre;
    private String valor;

    public void crearPro (View view) {

        idProducto = etId_producto.getText().toString();
        idPedido = etId_pedidoPro.getText().toString();
        idFabricante = etId_fabricantePro.getText().toString();
        nombre = etNombrePro.getText().toString();
        valor = etValorPro.getText().toString();

        if (idProducto.equals("") || idPedido.equals("") || idFabricante.equals("") || nombre.equals("") || valor.equals("")) {
            validacionPro(idProducto, idPedido, idFabricante, nombre, valor);
        }else {
            Producto producto = new Producto();
            /*Campos para  Firebase*/
            producto.setId_producto(idProducto);
            producto.setId_pedidoPro(idPedido);
            producto.setId_fabricantePro(idFabricante);
            producto.setNombrePro(nombre);
            producto.setValorPro(valor);

            /*Crea la tabla Pedido y desglosa información por id*/
            databaseReference.child("Producto").child(producto.getId_producto()).setValue(producto);

            Toast.makeText(this, "Registro agregado", Toast.LENGTH_LONG).show();
            limpiarCampo();
        }
    }

    public void actualizarPro (View view) {
        Producto producto = new Producto();
        producto.setId_producto(etId_producto.getText().toString().trim());
        producto.setId_pedidoPro(etId_pedidoPro.getText().toString().trim());
        producto.setId_fabricantePro(etId_fabricantePro.getText().toString().trim());
        producto.setNombrePro(etNombrePro.getText().toString().trim());
        producto.setValorPro(etValorPro.getText().toString().trim());

        /*Accedemos a nuestra DatabaseReference*/

        databaseReference.child("Producto").child(producto.getId_producto()).setValue(producto);
        Toast.makeText(this, "Registro actualizado", Toast.LENGTH_LONG).show();
        limpiarCampo();
    }

    public void eliminarPro (View view) {
        Producto producto = new Producto();
        producto.setId_producto(productoSeleccionado.getId_producto());

        databaseReference.child("Producto").child(producto.getId_producto()).removeValue();
        Toast.makeText(this, "Eliminado", Toast.LENGTH_LONG).show();
        limpiarCampo();
    }

    /*Validación de campos*/
    public void validacionPro(String idProducto, String idPedido, String idFabricante, String nombre, String valor) {

        if (idProducto.equals("")){
            etId_producto.setError("Campo requerido");
        }
        if (idPedido.equals("")){
            etId_pedidoPro.setError("Campo requerido");
        }
        if (idFabricante.equals("")){
            etId_fabricantePro.setError("Campo requerido");
        }
        if (nombre.equals("")){
            etNombrePro.setError("Campo requerido");
        }
        if (valor.equals("")){
            etValorPro.setError("Campo requerido");
        }
    }

    private void limpiarCampo() {

        etId_producto.setText("");
        etId_pedidoPro.setText("");
        etId_fabricantePro.setText("");
        etNombrePro.setText("");
        etValorPro.setText("");
    }

    public void atrasPro(View view) {

        startActivity(new Intent(ProductoActivity.this, MenuActivity.class));
        finish();
    }
}