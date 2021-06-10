package com.example.crudmovil.model;

public class Producto {

    private String id_producto;
    private String id_pedidoPro;
    private String id_fabricantePro;
    private String nombrePro;
    private String valorPro;

    public Producto() {

    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getId_pedidoPro() {
        return id_pedidoPro;
    }

    public void setId_pedidoPro(String id_pedidoPro) {
        this.id_pedidoPro = id_pedidoPro;
    }

    public String getId_fabricantePro() {
        return id_fabricantePro;
    }

    public void setId_fabricantePro(String id_fabricantePro) {
        this.id_fabricantePro = id_fabricantePro;
    }

    public String getNombrePro() {
        return nombrePro;
    }

    public void setNombrePro(String nombrePro) {
        this.nombrePro = nombrePro;
    }

    public String getValorPro() {
        return valorPro;
    }

    public void setValorPro(String valorPro) {
        this.valorPro = valorPro;
    }

    @Override
    public String toString() {
        return nombrePro;
    }
}
