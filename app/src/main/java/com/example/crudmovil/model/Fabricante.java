package com.example.crudmovil.model;

public class Fabricante {

    private String id_fabricante;
    private String id_productoFr;
    private String nombreFr;
    private String direccionFr;
    private String telefono;

    public Fabricante() {

    }

    public String getId_fabricante() {
        return id_fabricante;
    }

    public void setId_fabricante(String id_fabricante) {
        this.id_fabricante = id_fabricante;
    }

    public String getId_productoFr() {
        return id_productoFr;
    }

    public void setId_productoFr(String id_productoFr) {
        this.id_productoFr = id_productoFr;
    }

    public String getNombreFr() {
        return nombreFr;
    }

    public void setNombreFr(String nombreFr) {
        this.nombreFr = nombreFr;
    }

    public String getDireccionFr() {
        return direccionFr;
    }

    public void setDireccionFr(String direccionFr) {
        this.direccionFr = direccionFr;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return nombreFr;
    }
}
