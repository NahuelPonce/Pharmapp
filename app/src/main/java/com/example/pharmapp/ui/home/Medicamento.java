package com.example.pharmapp.ui.home;

import java.io.Serializable;

public class Medicamento implements Serializable {
    private String nombre;
    private Integer comprimido;
    private String descripcion;
    private int imagen;
    private Integer precio;

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

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
