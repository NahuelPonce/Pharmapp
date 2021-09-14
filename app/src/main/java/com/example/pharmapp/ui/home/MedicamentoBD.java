package com.example.pharmapp.ui.home;

import android.icu.number.IntegerWidth;

import java.sql.Blob;

public class MedicamentoBD {



    public MedicamentoBD(int medicamentoID, String nombre, Integer comprimido, Integer stock, Double precio, String imagen) {
        this.medicamentoID = medicamentoID;
        this.nombre = nombre;
        this.comprimido = comprimido;
        this.stock = stock;
        this.precio = precio;
        this.imagen = imagen;

    }
    private int medicamentoID;
    private String nombre;
    private Integer comprimido;
    private Integer stock;
    private Double precio;
    private String imagen;


    public int getMedicamentoID() {
        return medicamentoID;
    }

    public void setMedicamentoID(int medicamentoID) {
        this.medicamentoID = medicamentoID;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }




}
