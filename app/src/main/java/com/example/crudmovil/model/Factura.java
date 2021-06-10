package com.example.crudmovil.model;

public class Factura {

    private String id_factura;
    private String id_productoFac;
    private String fechaFac;
    private String valorFac;

    public Factura() {

    }

    public String getId_factura() {
        return id_factura;
    }

    public void setId_factura(String id_factura) {
        this.id_factura = id_factura;
    }

    public String getId_productoFac() {
        return id_productoFac;
    }

    public void setId_productoFac(String id_productoFac) {
        this.id_productoFac = id_productoFac;
    }

    public String getFechaFac() {
        return fechaFac;
    }

    public void setFechaFac(String fechaFac) {
        this.fechaFac = fechaFac;
    }

    public String getValorFac() {
        return valorFac;
    }

    public void setValorFac(String valorFac) {
        this.valorFac = valorFac;
    }

    @Override
    public String toString() {
        return fechaFac;
    }
}
