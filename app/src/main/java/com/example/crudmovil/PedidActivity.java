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

import com.example.crudmovil.model.Pedido;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PedidActivity extends AppCompatActivity {

    private EditText etId_pedido;
    private EditText etId_cliente;
    private EditText etFecha;
    private EditText etDescripcion;

    private ListView listvPedido;

    /*Lista*/
    private List<Pedido> listPedido = new ArrayList<Pedido>();
    ArrayAdapter<Pedido> arrayAdapterPedido;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Pedido pedidoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedid);

        etId_pedido = findViewById(R.id.etId_pedido);
        etId_cliente = findViewById(R.id.etId_cliente);
        etFecha = findViewById(R.id.etFecha);
        etDescripcion = findViewById(R.id.etDescripcion);

        listvPedido = findViewById(R.id.listvPedido);

        inicializarFirebase();
        listarDatos();
        /*Metodo para seleccionar y traer su contenido a los EditText*/
        listvPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pedidoSeleccionado = (Pedido) parent.getItemAtPosition(position);
                etId_pedido.setText(pedidoSeleccionado.getId_pedido());
                etId_cliente.setText(pedidoSeleccionado.getId_cliente());
                etFecha.setText(pedidoSeleccionado.getFecha());
                etDescripcion.setText(pedidoSeleccionado.getDescripcion());
            }
        });
    }

    private void listarDatos() {
        databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPedido.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    Pedido pedido = objSnaptshot.getValue(Pedido.class);
                    listPedido.add( pedido);

                    arrayAdapterPedido = new ArrayAdapter<Pedido>(PedidActivity.this, android.R.layout.simple_list_item_1, listPedido);
                    listvPedido.setAdapter(arrayAdapterPedido);
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

    private String idPedido;
    private String idCliente;
    private String fecha;
    private String descripcion;

    public void crearP (View view) {

        idPedido = etId_pedido.getText().toString();
        idCliente = etId_cliente.getText().toString();
        fecha = etFecha.getText().toString();
        descripcion = etDescripcion.getText().toString();

        if (idPedido.equals("") || idCliente.equals("") || fecha.equals("") || descripcion.equals("")) {
            validacionP(idPedido, idCliente, fecha, descripcion);
        }else {
            Pedido pedido = new Pedido();
            /*Campos para  Firebase*/
            pedido.setId_pedido(idPedido);
            pedido.setId_cliente(idCliente);
            pedido.setFecha(fecha);
            pedido.setDescripcion(descripcion);
            /*Posible problema con el id cliente, por su tipo*/
            /*Crea la tabla Pedido y desglosa información por id*/
            databaseReference.child("Pedido").child(pedido.getId_pedido()).setValue(pedido);

            Toast.makeText(this, "Registro agregado", Toast.LENGTH_LONG).show();
            limpiarCampo();
        }
    }

    public void actualizarP (View view) {
        Pedido pedido = new Pedido();
        pedido.setId_pedido(etId_pedido.getText().toString().trim());
        pedido.setId_cliente(etId_cliente.getText().toString().trim());
        pedido.setFecha(etFecha.getText().toString().trim());
        pedido.setDescripcion(etDescripcion.getText().toString().trim());

        /*Accedemos a nuestra DatabaseReference*/
        /*Posible error por id*/
        databaseReference.child("Pedido").child(pedido.getId_pedido()).setValue(pedido);
        Toast.makeText(this, "Registro actualizado", Toast.LENGTH_LONG).show();
        limpiarCampo();
    }

    public void eliminarP (View view) {
        Pedido pedido = new Pedido();
        pedido.setId_pedido(pedidoSeleccionado.getId_pedido());

        databaseReference.child("Pedido").child(pedido.getId_pedido()).removeValue();
        Toast.makeText(this, "Eliminado", Toast.LENGTH_LONG).show();
        limpiarCampo();
    }

    /*Validación de campos*/
    public void validacionP(String idPedido, String idCliente, String fecha, String descripcion) {

        if (idPedido.equals("")){
            etId_pedido.setError("Campo requerido");
        }
        if (idCliente.equals("")){
            etId_cliente.setError("Campo requerido");
        }
        if (fecha.equals("")){
            etFecha.setError("Campo requerido");
        }
        if (descripcion.equals("")){
            etDescripcion.setError("Campo requerido");
        }
    }

    private void limpiarCampo() {

        etId_pedido.setText("");
        etId_cliente.setText("");
        etFecha.setText("");
        etDescripcion.setText("");
    }

    public void atrasP(View view) {

        startActivity(new Intent(PedidActivity.this, MenuActivity.class));
        finish();
    }
}