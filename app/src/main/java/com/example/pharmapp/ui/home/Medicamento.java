package com.example.pharmapp.ui.home;

public class Medicamento {
    private String nombre;
    private Integer comprimido;
    private String descripcion;
    private int imagen;

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

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
