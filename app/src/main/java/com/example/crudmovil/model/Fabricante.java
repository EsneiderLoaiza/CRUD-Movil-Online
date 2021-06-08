package com.example.crudmovil.model;

public class Fabricante {

    private String id_fabricante;
    private String id_producto;
    private String nombre;
    private String direccion;
    private String telefono;

    public Fabricante() {

    }

    public String getId_fabricante() {
        return id_fabricante;
    }

    public void setId_fabricante(String id_fabricante) {
        this.id_fabricante = id_fabricante;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    @Override
    public String toString() {
        return nombre;
    }
}
