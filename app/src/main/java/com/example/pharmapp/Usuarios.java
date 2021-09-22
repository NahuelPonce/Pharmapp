package com.example.pharmapp;

public class Usuarios {
    public Usuarios(String usuario, String contraseña, String nombre, String apellido, Integer dni, String foto, String calle, Integer altura, String dpto, String obrasocial, Integer numafiliado){

        this.usuario = usuario;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.foto = foto;
        this.calle = calle;
        this.altura = altura;
        this.dpto = dpto;
        this.obrasocial = obrasocial;
        this.numafiliado = numafiliado;
    }

    private String contraseña;
    private String usuario;
    private String nombre;
    private String apellido;
    private Integer dni;
    private String foto;
    private String calle;
    private Integer altura;
    private String dpto;
    private String obrasocial;
    private Integer numafiliado;

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public String getDpto() {
        return dpto;
    }

    public void setDpto(String dpto) {
        this.dpto = dpto;
    }

    public String getObrasocial() {
        return obrasocial;
    }

    public void setObrasocial(String obrasocial) {
        this.obrasocial = obrasocial;
    }

    public Integer getNumafiliado() {
        return numafiliado;
    }

    public void setNumafiliado(Integer numafiliado) {
        this.numafiliado = numafiliado;
    }


}
