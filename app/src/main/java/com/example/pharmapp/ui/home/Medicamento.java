package com.example.pharmapp.ui.home;


import java.util.concurrent.atomic.AtomicInteger;

public class Medicamento {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int medicamentoID = count.incrementAndGet();
    private String nombre;
    private Integer comprimido;
    private String descripcion;
    private int imagen;
    private Double precio;
    private int cantidad;
    private double total;

    public int getMedicamentoID() {
        return medicamentoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getComprimido() {
        return comprimido;
    }

    public void setComprimido(Integer comprimido) {
        this.comprimido = comprimido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
