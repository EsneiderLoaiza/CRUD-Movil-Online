package com.example.crudmovil.model;

public class Producto {

    private String id_producto;
    private String id_pedido;
    private String id_fabricante;
    private String nombre;
    private String valor;

    public Producto() {

    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getId_fabricante() {
        return id_fabricante;
    }

    public void setId_fabricante(String id_fabricante) {
        this.id_fabricante = id_fabricante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
